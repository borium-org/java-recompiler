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

	public String getName()
	{
		return name;
	}

	public String getType()
	{
		return type;
	}
}
