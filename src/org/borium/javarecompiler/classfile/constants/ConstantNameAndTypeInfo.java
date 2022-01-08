package org.borium.javarecompiler.classfile.constants;

import org.borium.javarecompiler.classfile.*;

/**
 * The CONSTANT_NameAndType_info structure is used to represent a field or
 * method, without indicating which class or interface type it belongs to:
 *
 * <pre>
	CONSTANT_NameAndType_info
	{
		u1 tag;
		u2 name_index;
		u2 descriptor_index;
	}
 * </pre>
 *
 * The tag item has the value CONSTANT_NameAndType (12).
 */
public class ConstantNameAndTypeInfo extends Constant
{
	/**
	 * The value of the name_index item must be a valid index into the constant_pool
	 * table. The constant_pool entry at that index must be a CONSTANT_Utf8_info
	 * structure (4.4.7) representing either a valid unqualified name denoting a
	 * field or method (4.2.2), or the special method name <init> (2.9.1).
	 */
	public int nameIndex;

	/**
	 * The value of the descriptor_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_Utf8_info structure (4.4.7) representing a valid field descriptor or
	 * method descriptor (4.3.2, 4.3.3).
	 */
	private int descriptorIndex;

	private String name;

	private String descriptor;

	/**
	 * Calculate number of parameters to the method that is part of this name and
	 * type info. 'This' is not assumed to be present.
	 *
	 * @return Parameter count, excluding optional 'this'.
	 */
	public int getParameterCount()
	{
		if (!descriptor.startsWith("("))
		{
			throw new RuntimeException("Get parameter count for non-method");
		}
		int[] data = { 1, 0 };
		while (descriptor.charAt(data[0]) != ')')
		{
			parseSingleType(data);
		}
		return data[1];
	}

	/**
	 * Calculate return value count. The method id used in calculating stack depth
	 * after the method is executed.
	 *
	 * @return 0 (void) or 1 (anything else).
	 */
	public int getReturnTypeCount()
	{
		if (!descriptor.startsWith("("))
		{
			throw new RuntimeException("Get return type for non-method");
		}
		int pos = descriptor.indexOf(')');
		if (pos == -1)
		{
			throw new RuntimeException("No closing ')'");
		}

		return descriptor.charAt(pos + 1) != 'V' ? 1 : 0;
	}

	@Override
	protected void dump(IndentedOutputStream stream)
	{
		stream.print("NameType: Name " + nameIndex + " " + name + " Descriptor " + descriptorIndex + " " + descriptor);
	}

	@Override
	protected void fixup(ConstantPool constantPool)
	{
		name = constantPool.getString(nameIndex);
		descriptor = constantPool.getString(descriptorIndex);
	}

	@Override
	protected void read(ByteInputStream in)
	{
		tag = CONSTANT_Class;
		nameIndex = in.u2();
		descriptorIndex = in.u2();
	}

	@Override
	protected boolean verify(int majorVersion, int minorVersion, ConstantPool cp, int index)
	{
		if (majorVersion < 45)
		{
			return false;
		}
		if (majorVersion == 45 && minorVersion < 3)
		{
			return false;
		}
		if (!cp.get(nameIndex).is(CONSTANT_Utf8))
		{
			return false;
		}
		if (!cp.get(descriptorIndex).is(CONSTANT_Utf8))
		{
			return false;
		}
		return true;
	}

	private void parseClass(int[] data)
	{
		while (descriptor.charAt(data[0]) != ';' && descriptor.charAt(data[0]) != '<')
		{
			data[0]++;
		}
		if (descriptor.charAt(data[0]) == '<')
		{
			throw new RuntimeException("Templates not supported");
//			data[0]++;
//			while (descriptor.charAt(data[0]) != '>')
//			{
//				int count = data[1];
//				parseSingleType(data);
//				data[1] = count;
//			}
//			data[0]++;
		}
		if (descriptor.charAt(data[0]) == ';')
		{
			data[0]++;
		}
	}

	private void parseSingleType(int[] data)
	{
		while (descriptor.charAt(data[0]) == '[')
		{
			data[0]++;
		}
		switch (descriptor.charAt(data[0]))
		{
		case 'B':
		case 'C':
		case 'D':
		case 'F':
		case 'I':
		case 'J':
		case 'S':
		case 'Z':
			data[0]++;
			data[1]++;
			break;
		case 'L':
			data[0]++;
			data[1]++;
			parseClass(data);
			break;
		case 'V':
			data[0]++;
			break;
		}
	}
}
