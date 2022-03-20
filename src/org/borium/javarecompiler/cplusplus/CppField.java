package org.borium.javarecompiler.cplusplus;

import static org.borium.javarecompiler.Statics.*;
import static org.borium.javarecompiler.classfile.ClassField.*;
import static org.borium.javarecompiler.classfile.constants.Constant.*;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.attribute.*;
import org.borium.javarecompiler.classfile.constants.*;

class CppField
{
	/** Field name, same for Java and C++ class. */
	private String name;

	/** Field type, translated from Java format to C++ format. */
	private String type;

	private int accessFlags;

	private AttributeConstantValue attributeConstantValue;

	public CppField(ClassField javaField)
	{
		name = javaField.getName();
		String javaType = javaField.getType();
		accessFlags = javaField.getAccessFlags();
		attributeConstantValue = javaField.getAttribute(AttributeConstantValue.class);
		type = new JavaTypeConverter(javaType, (accessFlags & AccessStatic) != 0).getCppType();
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
			Assert((accessFlags & AccessStatic) != 0, "Final must be static");
			if (attributeConstantValue == null)
			{
				System.err.println("bang");
			}
			Constant constant = attributeConstantValue.getConstant();
			String value = "";
			switch (newType)
			{
			case "int":
				Assert(constant.is(CONSTANT_Integer), "Int: Int expected");
				value = String.valueOf(((ConstantInteger) constant).getValue());
				break;
			default:
				Assert(false, "Unsupported final type '" + newType + "'");
			}
			header.iprintln("const " + newType + " " + name + " = " + value + ";");
			return;
		}
		if ((accessFlags & AccessStatic) != 0)
		{
			// ignore access level and mask off static, see if we have anything else
			int mask = ~AccessStatic & ~AccessPrivate & ~AccessProtected & ~AccessPublic;
			if ((accessFlags & mask) != 0)
			{
				throw new RuntimeException("Static field with other flags not supported");
			}
			header.iprintln("static " + addPointerIfNeeded(newType) + " " + name + ";");
			return;
		}
		header.iprintln(addPointerIfNeeded(newType) + " " + name + ";");
	}

	public String getName()
	{
		return name;
	}

	public String getType()
	{
		return type;
	}

	public boolean isFinal()
	{
		return (accessFlags & AccessFinal) != 0;
	}

	public boolean isStatic()
	{
		return (accessFlags & AccessStatic) != 0;
	}
}
