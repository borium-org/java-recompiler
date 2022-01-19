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
	private CppExecutionContext executionContext;

	/** True if method is static. */
	private boolean isStatic;

	/**
	 * C++ class that owns this method. C++ class info is used to simplify field
	 * types.
	 */
	private CppClass cppClass;

	public CppMethod(CppClass cppClass, ClassMethod javaMethod)
	{
		this.cppClass = cppClass;
		executionContext = new CppExecutionContext(this, cppClass, javaMethod);
		isStatic = javaMethod.isStatic();
		parseStatements();
	}

	public void generateHeader(IndentedOutputStream header, String newName, String newType)
	{
		if (newType.endsWith(")"))
		{
			header.iprintln(newName + newType + ";");
		}
		else
		{
			header.iprint(isStatic ? "static " : "");
			int pos = newType.indexOf(')');
			header.println(newType.substring(pos + 1) + " " + newName + newType.substring(0, pos + 1) + ";");
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
					source.println(field.getName() + "(0) //");
					String fieldType = cppClass.simplifyType(field.getType());
					if (fieldType.endsWith("*"))
					{
						source.indent(2);
						source.iprintln(", ref_" + field.getName() + "(" + field.getName() + ") //");
						source.indent(-2);
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
		generateStatementSource(source, isConstructor);
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
		return executionContext.cppType;
	}

	/**
	 * Generate source for statements. It is assumed that in derived class
	 * constructor first statement is an invocation of the base class constructor,
	 * so it is handled separately where derived class constructor is defined, and
	 * it must be skipped in here to avoid duplication.
	 *
	 * @param source        Source file where to generate the method.
	 * @param isConstructor True if this is a constructor and first statement must
	 *                      be skipped.
	 */
	private void generateStatementSource(IndentedOutputStream source, boolean isConstructor)
	{
		boolean skip = isConstructor;
		for (Statement statement : statements)
		{
			if (!skip)
			{
				statement.generateSource(source);
			}
			skip = false;
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
				if (stackDepth == 0)
				{
					Statement statement = new Statement(executionContext, instructions);
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
