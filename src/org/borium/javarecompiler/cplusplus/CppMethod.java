package org.borium.javarecompiler.cplusplus;

import org.borium.javarecompiler.classfile.*;

class CppMethod
{
	private String name;
	private String type;

	public CppMethod(ClassMethod javaMethod)
	{
		name = javaMethod.getName();
		String javaType = javaMethod.getDescriptor();
		type = new JavaTypeConverter(javaType).getCppType();
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
}
