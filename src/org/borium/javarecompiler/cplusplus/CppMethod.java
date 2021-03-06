package org.borium.javarecompiler.cplusplus;

import static org.borium.javarecompiler.Statics.*;

import java.io.*;
import java.util.*;

import org.borium.javarecompiler.*;
import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.attribute.AttributeCode.*;
import org.borium.javarecompiler.classfile.instruction.*;
import org.borium.javarecompiler.cplusplus.CppMethod.ExceptionHandlers.*;
import org.borium.javarecompiler.cplusplus.LocalVariables.*;

class CppMethod
{
	static class ExceptionHandlers
	{
		static class ExceptionHandler
		{
			public int startPc;
			public int endPc;
			public int handlerPc;
			private ArrayList<String> exceptionClasses = new ArrayList<>();

			public ExceptionHandler(ExceptionTable entry, CppClass cppClass)
			{
				startPc = entry.startPc;
				endPc = entry.endPc;
				handlerPc = entry.handlerPc;
				String exceptionClass = entry.getExceptionClass().replace('/', '.');
				exceptionClass = javaToCppClass(exceptionClass);
				exceptionClass = cppClass.simplifyType(exceptionClass);
				exceptionClasses.add(exceptionClass);
			}
		}

		private ArrayList<ExceptionHandler> handlers = new ArrayList<>();

		public ExceptionHandlers(ExceptionTable[] exceptionTable, CppClass cppClass)
		{
			for (ExceptionTable entry : exceptionTable)
			{
				ExceptionHandler handler = findHandler(entry);
				if (handler == null)
				{
					handler = new ExceptionHandler(entry, cppClass);
					handlers.add(handler);
				}
				else
				{
					String exceptionClass = entry.getExceptionClass().replace('/', '.');
					exceptionClass = javaToCppClass(exceptionClass);
					exceptionClass = cppClass.simplifyType(exceptionClass);
					handler.exceptionClasses.add(exceptionClass);
				}
			}
		}

		public ArrayList<Integer> getCatchAddresses()
		{
			ArrayList<Integer> addresses = new ArrayList<>();
			for (ExceptionHandler handler : handlers)
			{
				addresses.add(handler.handlerPc);
			}
			return addresses;
		}

		/**
		 * Check if address is the beginning of catch block. This is needed for stack
		 * depth analysis where stack depth goes negative because of ASTORE instruction.
		 * This is only valid at the beginning of catch block where reference to the
		 * exception object is implicitly pushed to stack in ATHROW and ASTORE takes it
		 * from stack and stores into local variable.
		 *
		 * @param address Address of ASTORE instruction.
		 * @return true if address is a beginning of any exception handler in catch
		 *         block.
		 */
		public boolean isCatchBlock(int address)
		{
			for (ExceptionHandler handler : handlers)
			{
				if (handler.handlerPc == address)
				{
					return true;
				}
			}
			return false;
		}

		/**
		 * Find an exception handler where the specified local is used as a catch
		 * parameter.
		 *
		 * @param local Potential catch block parameter.
		 * @return Exception handler or null.
		 */
		public ExceptionHandler isParameter(LocalVariable local)
		{
			for (ExceptionHandler handler : handlers)
			{
				if (local.startPc == handler.handlerPc + 2)
				{
					return handler;
				}
			}
			return null;
		}

		/**
		 * Find an exception handler where the specified local variable is internal to
		 * the catch block.
		 *
		 * @param local Potential catch block variable.
		 * @return Exception handler or null.
		 */
		public ExceptionHandler isTryVariable(LocalVariable local)
		{
			for (ExceptionHandler handler : handlers)
			{
				if (local.startPc >= handler.startPc && local.endPc <= handler.endPc)
				{
					return handler;
				}
			}
			return null;
		}

		/**
		 * Find an exception handler that is at or past the given address. The range
		 * between current address and start PC of the exception handler is outside of
		 * the try block. The assumption is that address is not within catch block.
		 *
		 * @param address Address for which the handler needs to be located.
		 * @return Exception handler or null if there are no exception handlers at or
		 *         past the address. The rest of code executes outside of any exception
		 *         handler try block.
		 */
		ExceptionHandler findHandler(int address)
		{
			for (ExceptionHandler handler : handlers)
			{
				if (address > handler.endPc)
				{
					continue;
				}
				if (address <= handler.endPc)
				{
					return handler;
				}
			}
			return null;
		}

		private ExceptionHandler findHandler(ExceptionTable entry)
		{
			for (ExceptionHandler handler : handlers)
			{
				if (handler.startPc == entry.startPc && handler.endPc == entry.endPc
						&& handler.handlerPc == entry.handlerPc)
				{
					return handler;
				}
			}
			return null;
		}
	}

	/**
	 * All statements in the method. Statements are sequences of instructions that
	 * have stack depth 0 at the beginning and at the end. Each statement contains
	 * at least one instruction. Statements are indexed by their starting addresses.
	 */
	private HashMap<Integer, Statement> statements = new HashMap<>();

	/**
	 * Execution context for the method. Context contains local variables, operand
	 * stack, and other stuff necessary for emulating instructions.
	 */
	private CppExecutionContext executionContext;

	/** True if method is static. */
	private boolean isStatic;

	/** True if method is abstract. */
	private boolean isAbstract;

	/**
	 * C++ class that owns this method. C++ class info is used to simplify field
	 * types.
	 */
	private CppClass cppClass;

	/** Exception handlers for the method. */
	private ExceptionHandlers exceptionHandlers;

	/**
	 * Parameter count for this method. Parameters and locals use same index space,
	 * so parameterCount is needed for filtering out true local variables. Anything
	 * with index greater or equal parameter count is a local variable.
	 */
	private int parameterCount;

	private Instruction[] instructions;

	public CppMethod(CppClass cppClass, ClassMethod javaMethod)
	{
		this.cppClass = cppClass;
		executionContext = new CppExecutionContext(this, cppClass, javaMethod);
		isStatic = javaMethod.isStatic();
		isAbstract = javaMethod.isAbstract();
		parameterCount = javaMethod.getParameterCount();
		instructions = javaMethod.getInstructions();
		if (!isAbstract)
		{
			exceptionHandlers = new ExceptionHandlers(javaMethod.getExceptionTable(), cppClass);
			parseStatements();
		}
	}

	public boolean containsStatementAt(int targetAddress)
	{
		return statements.containsKey(targetAddress);
	}

	public void generateHeader(IndentedOutputStream header, String newName, String newType)
	{
		if (newType.endsWith(")"))
		{
			// constructor type ends with ')', no return type
			header.iprintln(newName + newType + ";");
		}
		else
		{
			// anything non-static is virtual
			header.iprint(isStatic ? "static " : "virtual ");
			newType = addPointersIfNeeded(newType);
			int pos = newType.indexOf(')');
			header.print(newType.substring(pos + 1) + " " + newName + newType.substring(0, pos + 1));
			header.println(isAbstract ? " = 0;" : ";");
		}
	}

	/**
	 * Generate the method source code.
	 *
	 * @param source  Source output file.
	 * @param newName New method name, may be different from Java name such as init
	 *                or clinit.
	 * @param newType New method type, with redundant namespaces removed.
	 *                Constructor method type will not have return type component.
	 * @param fields  List of all fields in the class. Fields are needed for
	 *                constructor initialization sequence, and may be for other
	 *                reasons.
	 */
	public void generateSource(IndentedOutputStream source, String newName, String newType, CppField[] fields)
	{
		if (isAbstract)
		{
			return;
		}
		newType = addPointersIfNeeded(newType);
		int pos = newType.indexOf(')');
		String returnType = newType.substring(pos + 1);
		boolean isConstructor = returnType.length() == 0;
		source.iprint("");
		if (!isConstructor)
		{
			source.print(returnType + " ");
		}
		source.print(newName + newType.substring(0, pos + 1));
		if (isConstructor)
		{
			source.println(" :");
			source.indent(1);
			statements.get(0).generateSource(source, true);
			boolean first = true;
			for (CppField field : fields)
			{
				if (!field.isStatic())
				{
					String type = field.getType();
					String initializer = createInitializer(type);
					if (initializer.length() > 0)
					{
						if (first)
						{
							source.iprint(", ");
						}
						else
						{
							source.indent(2);
							source.iprint(", ");
							source.indent(-2);
						}
						first = false;
						source.println(field.getName() + "(" + initializer + ") //");
					}
				}
			}
			source.indent(-1);
		}
		else
		{
			source.println();
		}
		source.iprintln("{");
		source.indent(1);
		generateLocalVariables(source);
		source.lock();
		boolean isStaticConstructor = newName.endsWith("::ClassInit");
		generateStatementSource(source, isConstructor, isStaticConstructor);
		source.unlock();
		source.indent(-1);
		source.iprintln("}");
		source.println();
	}

	public Instruction[] getInstructions()
	{
		return instructions;
	}

	public String getName()
	{
		return executionContext.name;
	}

	public String getType()
	{
		return executionContext.cppType;
	}

	/**
	 * Combine multiple statements into one of they contain ternary operators.
	 * <p>
	 * Ternary operator is implemented using conditional ':' and unconditional '?'
	 * branches into the middle of the statement that contains the operator. All
	 * other branches in if and switch statements always target the beginning of a
	 * statement.
	 */
	private void combineStatements()
	{
		boolean changed = true;
		while (changed)
		{
			changed = false;
			for (int address = 0; address < executionContext.getCodeSize();)
			{
				Statement statement = statements.get(address);
				for (Instruction instruction : statement.getInstructions())
				{
					// Check all instructions that have branch targets
					int targets = instruction.getTargetCount();
					if (targets > 0)
					{
						for (int target = 0; target < targets; target++)
						{
							// Branch target to the beginning of some statement?
							int targetAddress = instruction.getTargetAddress(target);
							Statement targetStatement = statements.get(targetAddress);
							if (targetStatement == null)
							{
								// No such statement, so we're in the middle of something. Keep merging till we
								// merge the statement that we are targeting.
								// We may try to transfer into our own statement when all necessary merging is
								// complete, so test the target address to be within our statement.
								int nextAddress = address + statement.length();
								if (targetAddress >= address && targetAddress < nextAddress)
								{
									break;
								}
								// Merge all statements until we get the one we're targeting in the middle.
								for (;;)
								{
									nextAddress = address + statement.length();
									if (nextAddress > targetAddress)
									{
										break;
									}
									Statement nextStatement = statements.get(nextAddress);
									statement.merge(nextStatement);
									statements.remove(nextAddress);
								}
								changed = true;
								break;
							}
						}
					}
					if (changed)
					{
						break;
					}
				}
				if (changed)
				{
					break;
				}
				address += statement.length();
			}
		}
	}

	private String createInitializer(String type)
	{
		String initializer = " = 0";
		if (type.equals("bool"))
		{
			initializer = " = false";
		}
		type = addPointerIfNeeded(type);
		if (type.startsWith("Pointer<"))
		{
			initializer = "";
		}
		return initializer;
	}

	private void generateLocalVariableInitializer(IndentedOutputStream source, LocalVariable local)
	{
		String type = local.getType();
		String name = local.getName();
		String initializer = createInitializer(type);
		source.iprintln(addPointerIfNeeded(type) + " " + name + initializer + ";");
	}

	private void generateLocalVariables(IndentedOutputStream source)
	{
		ArrayList<LocalVariable> locals = executionContext.getLocalVariables();
		for (LocalVariable local : locals)
		{
			if (local.index < parameterCount)
			{
				continue;
			}
			if (exceptionHandlers.isParameter(local) != null)
			{
				continue;
			}
			if (exceptionHandlers.isTryVariable(local) != null)
			{
				continue;
			}
			generateLocalVariableInitializer(source, local);
		}
	}

	/**
	 * Generate statements in the block between (staring) address and end address.
	 * Optionally, we can prevent generating the label in first statement. All other
	 * statements can have their labels, if defined.
	 *
	 * @param source     Source file to use for output.
	 * @param address    Beginning address, inclusive, of the statement block. First
	 *                   statement will possible have its label missing.
	 * @param endAddress Ending address, exclusive, of the block, Statement at
	 *                   endAddress will not be generated.
	 * @param allowLabel If false, first statement label will not be generated.
	 * @return
	 */
	private int generateStatements(IndentedOutputStream source, int address, int endAddress, boolean allowLabel)
	{
		while (address < endAddress)
		{
			Statement statement = statements.get(address);
			Assert(statement != null, "Before try: Null statement");
			statement.generateSource(source, allowLabel);
			allowLabel = true;
			address += statement.length();
		}
		Assert(address == endAddress, "End address mismatch");
		return address;
	}

	/**
	 * Generate source for statements. It is assumed that in derived class
	 * constructor first statement is an invocation of the base class constructor,
	 * so it is handled separately where derived class constructor is defined, and
	 * it must be skipped in here to avoid duplication.
	 * <p>
	 * Special effort is needed to handle exception try/catch blocks. These
	 * exception blocks can overlap if catch blocks have different exception type.
	 * Single catch block can be used for multiple types if the resulting exception
	 * handling code is exactly same. In C++ code these catch blocks would be
	 * generated more than once, each catch block would have their unique exception
	 * type.
	 *
	 * @param source              Source file where to generate the method.
	 * @param isConstructor       True if this is a constructor and first statement
	 *                            must be skipped.
	 * @param isStaticConstructor True if this is static initializer. Super class
	 *                            static constructor is invoked first. Static field
	 *                            access code generation is modified.
	 */
	private void generateStatementSource(IndentedOutputStream source, boolean isConstructor,
			boolean isStaticConstructor)
	{
		executionContext.isStaticConstructor = isStaticConstructor;
		if (isStaticConstructor)
		{
			String parentClassName = cppClass.parentClassName;
			parentClassName = cppClass.simplifyType(parentClassName);
			source.iprintln(parentClassName + "::ClassInit();");
		}
		int address = 0;
		if (isConstructor)
		{
			address += statements.get(0).length();
		}
		int lastAddress = executionContext.getCodeSize();
		while (address < lastAddress)
		{
			ExceptionHandler handler = exceptionHandlers.findHandler(address);
			// No handlers left in the method? All remaining code is outside of any
			// exception handlers
			if (handler == null)
			{
				generateStatements(source, address, lastAddress, true);
				break;
			}
			// We have a handler. However, our current statement may be above the try block.
			address = generateStatements(source, address, handler.startPc, true);
			// Now statement is at the startPc of the exception address that we found.
			// Label is outside of try block, if necessary
			Statement statement = statements.get(address);
			statement.generateLabel(source);
			source.iprintln("try");
			source.iprintln("{");
			source.indent(1);
			// Generate try block variables
			generateTryBlockVariables(source, address, handler.endPc);
			// Generate all statements in the try block, but prohibit generating first
			// label.
			address = generateStatements(source, address, handler.endPc, false);
			// We stopped at endPc, however, there's one more statement with GOTO
			// instruction that transfers past the end of catch block. We need to generate
			// it and we also need to know the GOTO target so we would know where the catch
			// block ends.
			statement = statements.get(address);
			Assert(statement != null, "GOTO: Null statement");
			statement.generateSource(source, true);
			address += statement.length();
			source.indent(-1);
			source.iprintln("}");
			Assert(statement.getInstructionCount() == 1, "Try: Single instruction statement expected");
			Instruction instruction = statement.getLastInstruction();
			Assert(instruction instanceof InstructionGOTO, "GOTO expected in the end of try block");
			int endCatch = instruction.getTargetAddress(0);
			// Statement with GOTO past catch block is generated by now. Address is at catch
			// block ASTORE.
			for (String exceptionClass : handler.exceptionClasses)
			{
				// This is ASTORE statement.
				statement = statements.get(address);
				// First instruction is ASTORE into the exception variable. We don't generate it
				// as such, the equivalent effect of that statement in C++ is to declare the
				// parameter for the catch block.
				Assert(statement.getInstructionCount() == 1, "Catch: Single instruction statement expected");
				Assert(statement.getLastInstruction() instanceof InstructionASTORE,
						"Catch: ASTORE instruction expected");
				InstructionASTORE astore = (InstructionASTORE) statement.getLastInstruction();
				int catchStartPc = astore.address + astore.length();
				LocalVariable catchParam = executionContext.getLocalVariable(astore.getIndex(), catchStartPc);
				Assert(catchParam != null, "Catch: Parameter not found");
				source.iprintln("catch (" + exceptionClass + "* " + catchParam.getName() + ")");
				source.iprintln("{");
				source.indent(1);
				generateStatements(source, catchStartPc, endCatch, true);
				source.indent(-1);
				source.iprintln("}");
			}
			address = endCatch;
		}
	}

	private void generateTryBlockVariables(IndentedOutputStream source, int address, int endPc)
	{
		ArrayList<LocalVariable> locals = executionContext.getLocalVariables();
		for (LocalVariable local : locals)
		{
			if (local.endPc != endPc)
			{
				continue;
			}
			ExceptionHandler handler = exceptionHandlers.isTryVariable(local);
			if (handler == null)
			{
				continue;
			}
			if (local.startPc > handler.startPc && local.endPc == handler.endPc)
			{
				generateLocalVariableInitializer(source, local);
			}
		}
	}

	/**
	 * Parse code array and split it into statements. Statement is a sequence of
	 * instructions that starts with stack depth 0, ends with stack depth 0, and if
	 * there are instructions referenced by jumps their jump instructions must be in
	 * the same statement. Jumps into other statements are allowed only at the end
	 * of the statement so that stack depth would be 0.
	 */
	private void parseStatements()
	{
		Instruction[] code = executionContext.instructions;
		int[] depth = new int[code.length + 1];
		// Array of stack depths, -1 means no instruction or not analyzed. Location 0 is
		// set to 0, this is where the method starts so the value is always 0.
		Arrays.fill(depth, -1);
		depth[0] = 0;
		// Array of flags where we have instructions that are already processed. Array
		// is needed so we would know if we have to push the location to the stack or
		// not.
		boolean[] processed = new boolean[code.length];
		Arrays.fill(processed, false);
		// Stack of unparsed points in the code array. Each entry has address (lower 16
		// bits) and stack depth for that address (15 bits should be enough). We start
		// at 0:0000.
		System.out.println("Parsing instructions for " + executionContext.name);
		Stack<Integer> parseStack = new Stack<>();
		parseStack.push(0);
		// If the code has exceptions, we need to push them as well, with initial stack
		// depth +1.
		for (int address : exceptionHandlers.getCatchAddresses())
		{
			parseStack.push(0x10000 + address);
			depth[address] = 1;
		}
		System.out.print("");
		while (parseStack.size() > 0)
		{
			int entry = parseStack.pop();
			int stackDepth = entry >> 16;
			int address = entry & 0xFFFF;
			Assert(depth[address] == stackDepth,
					"Stack depth at " + hexString(address, 4) + " is not " + stackDepth + " but " + depth[address]);
			for (;;)
			{
				// Pre-check: If instruction is already processed, move on. Instruction can be
				// processed if we conditionally jump over some instructions, then target will
				// be pushed, we will continue processing instructions past the conditional jump
				// and may arrive at the target of that conditional jump in regular control
				// flow.
				if (processed[address])
				{
					break;
				}
				// For each instruction that is being processed:
				Instruction instruction = code[address];
				// 1. Next address after the instruction.
				int nextAddress = address + instruction.length();
				// 2. Stack depth change.
				stackDepth += instruction.getStackDepthChange();
				if (nextAddress < depth.length && instruction.fallsThrough())
				{
					depth[nextAddress] = stackDepth;
				}
				// Special case for non-fall-through *RETURN instruction at the end of method
				else if (nextAddress == depth.length - 1)
				{
					depth[nextAddress] = stackDepth;
				}
				else if (nextAddress > code.length)
				{
					Assert(false, "Instruction control flow past code array");
				}
				// 3. If instruction jumps somewhere, prepare all target locations.
				int targetCount = instruction.getTargetCount();
				for (int i = 0; i < targetCount; i++)
				{
					int targetAddress = instruction.getTargetAddress(i);
					// 3a. Each target has same stack depth as the depth after instruction executes
					// without jumping.
					if (depth[targetAddress] == -1)
					{
						depth[targetAddress] = stackDepth;
					}
					else
					{
						Assert(depth[targetAddress] == stackDepth,
								"Stack depth at target " + hexString(targetAddress, 4) + " is not " + stackDepth
										+ " but " + depth[targetAddress]);
					}
					// 3b. If target instruction is not processed yet, stack it for later.
					if (!processed[targetAddress])
					{
						parseStack.push(stackDepth << 16 | targetAddress);
					}
				}
				processed[address] = true;
				// Continue with the address next to the instruction if instruction can fall
				// through, otherwise exit address/nextAddress loop and get next entry from the
				// stack if something is there.
				if (!instruction.fallsThrough())
				{
					break;
				}
				address = nextAddress;
				if (address == code.length)
				{
					break;
				}
			}
		}
		// Parsing check: All instructions should be parsed
		for (int i = 0; i < code.length; i++)
		{
			if (code[i] != null)
			{
				Assert(processed[i], "Instruction at " + hexString(i, 4) + " was not processed");
				Assert(depth[i] >= 0, "Stack depth at " + hexString(i, 4) + " was not processed");
			}
		}
		System.out.println("All instructions are parsed, putting together a Statement list");
		if (Recompiler.dumpInstructions)
		{
			String traceFileName = executionContext.name;
			switch (executionContext.name)
			{
			case "<init>":
				traceFileName = cppClass.className;
				break;
			case "<clinit>":
				traceFileName = cppClass.className + "_Class";
				break;
			}
			traceFileName += ".insn.txt";
			try
			{
				IndentedOutputStream trace = new IndentedOutputStream(traceFileName);
				for (int i = 0; i < code.length; i++)
				{
					Instruction instruction = code[i];
					if (instruction != null)
					{
						trace.print(hexString(i, 4) + " " + depth[i] + "  ");
						instruction.oneLineDump(trace);
					}
				}
				trace.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		// Statements are defined as instruction sequences that start at stack depth 0
		// and end at stack depth 0. There are special cases for exception handler catch
		// blocks and ternary operators.
		int stackDepth = 0;
		ArrayList<Instruction> instructions = new ArrayList<>();
		for (int address = 0; address < code.length; address++)
		{
			Instruction instruction = code[address];
			if (instruction != null)
			{
				instructions.add(instruction);
				int nextAddress = address + instruction.length();
				stackDepth = depth[nextAddress];
				if (stackDepth == 0)
				{
					Statement statement = new Statement(executionContext, instructions);
					statements.put(statement.getAddress(), statement);
					instructions.clear();
				}
			}
		}
		if (instructions.size() != 0)
		{
			for (int address = 0; address < code.length; address++)
			{
				Instruction instruction = code[address];
				if (instruction != null)
				{
					System.out.println(hexString(address, 4) + " " + depth[address] + "  " + instruction.toString());
				}
			}
			System.err.println("Java stack depth is not 0 but " + stackDepth + " at the end of instruction array in "
					+ executionContext.name);
		}
		combineStatements();
		// Another hack: Since catch blocks are somewhat special in stack depth, their
		// ASTORE instruction gets merged into the previous statement. The previous
		// statement is a GOTO in the end of try block, so the pattern is easy to
		// recognize.
		for (int address : exceptionHandlers.getCatchAddresses())
		{
			int statementAddress = address - 3;
			Assert(code[statementAddress] instanceof InstructionGOTO, "GOTO expected");
			Statement statement = statements.get(statementAddress);
			Assert(statement != null, "Statement expected at the end of try block");
			Statement catchStatement = statement.splitLastInstruction();
			statements.put(address, catchStatement);
		}
		if (Recompiler.dumpStatements)
		{
			String traceFileName = executionContext.name;
			switch (executionContext.name)
			{
			case "<init>":
				traceFileName = cppClass.className;
				break;
			case "<clinit>":
				traceFileName = cppClass.className + "_Class";
				break;
			}
			traceFileName += ".stmt.txt";

			int address = 0;
			try
			{
				IndentedOutputStream trace = new IndentedOutputStream(traceFileName);
				for (;;)
				{
					Statement statement = statements.get(address);
					if (statement == null)
					{
						break;
					}
					statement.dumpInstructions(trace);
					address += statement.length();
				}
				trace.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
