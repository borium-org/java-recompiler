package org.borium.javarecompiler.cplusplus;

import static org.borium.javarecompiler.Statics.*;

import java.util.*;

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

	public CppMethod(CppClass cppClass, ClassMethod javaMethod)
	{
		this.cppClass = cppClass;
		executionContext = new CppExecutionContext(this, cppClass, javaMethod);
		isStatic = javaMethod.isStatic();
		isAbstract = javaMethod.isAbstract();
		parameterCount = javaMethod.getParameterCount();
		if (!isAbstract)
		{
			exceptionHandlers = new ExceptionHandlers(javaMethod.getExceptionTable(), cppClass);
			parseStatements();
		}
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
			source.indent(2);
			statements.get(0).generateSource(source);
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
			source.indent(-2);
		}
		else
		{
			source.println();
		}
		source.iprintln("{");
		source.indent(1);
		generateLocalVariables(source);
		boolean isStaticConstructor = newName.endsWith("::ClassInit");
		generateStatementSource(source, isConstructor, isStaticConstructor);
		source.indent(-1);
		source.iprintln("}");
		source.println();
	}

	public String getName()
	{
		return executionContext.name;
	}

	public String getType()
	{
		return executionContext.cppType;
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

	private int generateStatements(IndentedOutputStream source, int address, int endAddress)
	{
		while (address < endAddress)
		{
			Statement statement = statements.get(address);
			Assert(statement != null, "Before try: Null statement");
			statement.generateSource(source);
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
				generateStatements(source, address, lastAddress);
				break;
			}
			// We have a handler. However, our current statement may be above the try block.
			address = generateStatements(source, address, handler.startPc);
			// Now statement is at the startPc of the exception address that we found.
			source.iprintln("try");
			source.iprintln("{");
			source.indent(1);
			// Generate try block variables
			generateTryBlockVariables(source, address, handler.endPc);
			// Generate all statements in the try block.
			address = generateStatements(source, address, handler.endPc);
			// We stopped at endPc, however, there's one more statement with GOTO
			// instruction that transfers past the end of catch block. We need to generate
			// it and we also need to know the GOTO target so we would know where the catch
			// block ends.
			Statement statement = statements.get(address);
			Assert(statement != null, "GOTO: Null statement");
			statement.generateSource(source);
			address += statement.length();
			source.indent(-1);
			source.iprintln("}");
			Assert(statement.getInstructionCount() == 1, "Try: Single instruction statement expected");
			Instruction instruction = statement.getLastInstruction();
			Assert(instruction instanceof InstructionGOTO, "GOTO expected in the end of try block");
			InstructionGOTO g = (InstructionGOTO) instruction;
			int endCatch = g.getTargetAddress();
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
				source.iprintln("catch (" + exceptionClass + " *" + catchParam.getName() + ")");
				source.iprintln("{");
				source.indent(1);
				generateStatements(source, catchStartPc, endCatch);
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

	private void parseStatements()
	{
		Instruction[] code = executionContext.instructions;
		int stackDepth = 0;
		ArrayList<Instruction> instructions = new ArrayList<>();
		for (int address = 0; address < code.length; address++)
		{
			Instruction instruction = code[address];
			if (instruction != null)
			{
				instructions.add(instruction);
				stackDepth += instruction.getStackDepthChange();
				// Stack depth can become -1 at the beginning of the catch block in exception
				// handler. It is implied that JVM pushes the exception object reference to
				// stack during ATHROW and first instruction in catch block is ASTORE into a
				// local variable. We can't update stack at ATHROW location. We need to handle
				// ASTORE stack depth in here if the stack depth goes negative.
				if (stackDepth == -1)
				{
					Assert(instruction instanceof InstructionASTORE, "Stack negative: ASTORE expected");
					Assert(exceptionHandlers.isCatchBlock(address), "Stack negative: ASTORE must be in catch block");
					stackDepth = 0;
				}
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
			System.err.println("Java stack depth is not 0 but " + stackDepth + " at the end of instruction array");
		}
	}
}
