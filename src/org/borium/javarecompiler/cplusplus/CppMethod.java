package org.borium.javarecompiler.cplusplus;

import java.io.*;
import java.util.*;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.instruction.*;

class CppMethod
{
	private String name;

	private String type;

	private ArrayList<Statement> statements = new ArrayList<>();

	public CppMethod(ClassMethod javaMethod)
	{
		name = javaMethod.getName();
		String javaType = javaMethod.getDescriptor();
		type = new JavaTypeConverter(javaType).getCppType();
		parseBytecodes(javaMethod);
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
		return name;
	}

	public String getType()
	{
		return type;
	}

	private void parseBytecodes(ClassMethod javaMethod)
	{
		Instruction[] code = javaMethod.getInstructions();
		try
		{
			IndentedOutputStream stream = new IndentedOutputStream("Instructions.txt");
			int stackDepth = 0;
			ArrayList<Instruction> instructions = new ArrayList<>();
			for (int address = 0; address < code.length; address++)
			{
				Instruction instruction = code[address];
				if (instruction != null)
				{
					stream.print("L");
					stream.printHex(address, 4);
					stream.print(": ");
					instruction.detailedDump(stream);
					instructions.add(instruction);
					stackDepth += instruction.getStackDepthChange();
					stream.println(" // " + stackDepth);
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
				System.err.println("Java stack depth is not 0 at the end of instruction array");
			}
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
