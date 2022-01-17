package org.borium.javarecompiler.cplusplus;

import static org.borium.javarecompiler.classfile.ClassField.*;

import org.borium.javarecompiler.classfile.*;

class CppField
{
	/** Field name, same for Java and C++ class. */
	private String name;

	/** Field type, translated from Java format to C++ format. */
	private String type;

	private int accessFlags;

	public CppField(ClassField javaField)
	{
		name = javaField.getName();
		String javaType = javaField.getType();
		accessFlags = javaField.getAccessFlags();
		type = new JavaTypeConverter(javaType, (accessFlags & AccessStatic) != 0).getCppType();
		// TODO Auto-generated constructor stub
		System.out.println(type + " " + name + "; // " + javaType);
	}

	/**
	 * Generate the field declaration in the header. Actual type of the field is
	 * determined by the class file, where redundant namespace declarations are
	 * removed.
	 *
	 * @param header  Header output stream.
	 * @param newType The actual type of the field.
	 */
	public void generateHeader(IndentedOutputStream header, String newType)
	{
		if ((accessFlags & AccessEnum) != 0)
		{
			throw new RuntimeException("Enum field not supported");
		}
		if ((accessFlags & AccessSynthetic) != 0)
		{
			throw new RuntimeException("Synthetic field not supported");
		}
		if ((accessFlags & AccessTransient) != 0)
		{
			throw new RuntimeException("Transient field not supported");
		}
		if ((accessFlags & AccessVolatile) != 0)
		{
			throw new RuntimeException("Volatile field not supported");
		}
		if ((accessFlags & AccessFinal) != 0)
		{
			throw new RuntimeException("Final field not supported");
		}
		if ((accessFlags & AccessStatic) != 0)
		{
			throw new RuntimeException("Static field not supported");
		}
		header.iprintln(newType + " " + name + ";");
	}

	public String getName()
	{
		return name;
	}

	public String getType()
	{
		return type;
	}

	public boolean isStatic()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
