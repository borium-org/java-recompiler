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

	public void generateSource(IndentedOutputStream source, String newName, String newType)
	{
		int pos = newType.indexOf(')');
		String returnType = newType.substring(pos + 1);
		boolean isConstructor = returnType.length() == 0;
		source.iprint("");
		if (!isConstructor)
		{
			source.print(returnType + " ");
		}
		source.println(newName + newType.substring(0, pos + 1));
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
