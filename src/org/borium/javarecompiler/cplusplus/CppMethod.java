package org.borium.javarecompiler.cplusplus;

import java.util.*;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.instruction.*;

class CppMethod
{
	/**
	 * List of statements in the method. Statements are sequences of instructions
	 * that have stack depth 0 at the beginning and at the end. Each statement
	 * contains at least one instruction.
	 */
	private ArrayList<Statement> statements = new ArrayList<>();

	/**
	 * Execution context for the method. Context contains local variables, operand
	 * stack, and other stuff necessary for emulating instructions.
	 */
	private ExecutionContext executionContext;

	public CppMethod(ClassMethod javaMethod)
	{
		executionContext = new CppExecutionContext(javaMethod);
		parseStatements();
	}

	public void generateHeader(IndentedOutputStream header, String newName, String newType)
	{
		int pos = newType.indexOf(')');
		header.iprintln(newType.substring(pos + 1) + " " + newName + newType.substring(0, pos + 1) + ";");
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
			source.indent(2);
			boolean first = true;
			for (CppField field : fields)
			{
				if (!field.isStatic())
				{
					if (first)
					{
						source.println(" :");
						source.iprint("");
					}
					else
					{
						source.indent(2);
						source.iprint(", ");
						source.indent(-2);
					}
					first = false;
					source.print(field.getName() + "(0) //");
					source.println();
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
		generateInstructionComments(source);
		// TODO the other stuff
		if (!isConstructor && !returnType.equals("void"))
		{
			source.iprintln("return 0;");
		}
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
		return executionContext.type;
	}

	private void generateInstructionComments(IndentedOutputStream source)
	{
		for (Statement statement : statements)
		{
			statement.generateComments(source);
		}
		// TODO Auto-generated method stub
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
				if (stackDepth == 0)
				{
					Statement statement = new Statement(instructions);
					statements.add(statement);
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
