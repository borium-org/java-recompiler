package org.borium.javarecompiler.cplusplus;

import org.borium.javarecompiler.classfile.*;

class CppField
{
	/** Field name, same for Java and C++ class. */
	private String name;

	/** Field type, translated from Java format to C++ format. */
	private String type;

	public CppField(ClassField javaField)
	{
		name = javaField.getName();
		String javaType = javaField.getType();
		type = new JavaTypeConverter(javaType).getCppType();
		// TODO Auto-generated constructor stub
		System.out.println(type + " " + name + "; // " + javaType);
	}
}
