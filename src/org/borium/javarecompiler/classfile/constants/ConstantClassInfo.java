package org.borium.javarecompiler.classfile.constants;

import org.borium.javarecompiler.classfile.*;

/**
 * The CONSTANT_Class_info structure is used to represent a class or an
 * interface:
 *
 * <pre>
	CONSTANT_Class_info
	{
		u1 tag;
		u2 name_index;
	}
 * </pre>
 *
 * The tag item has the value CONSTANT_Class (7).
 */
public class ConstantClassInfo extends Constant
{
	/**
	 * The value of the name_index item must be a valid index into the constant_pool
	 * table. The constant_pool entry at that index must be a CONSTANT_Utf8_info
	 * structure (4.4.7) representing a valid binary class or interface name encoded
	 * in internal form (4.2.1).
	 */
	public int nameIndex;

	private String name;

	@Override
	public void dump(IndentedOutputStream stream)
	{
		stream.print("ClassInfo: " + nameIndex + " " + name);
	}

	public String getName()
	{
		return name;
	}

	public String getValue()
	{
		return name;
	}

	@Override
	protected void fixup(ConstantPool constantPool)
	{
		name = constantPool.getString(nameIndex);
	}

	@Override
	protected void read(ByteInputStream in)
	{
		tag = CONSTANT_Class;
		nameIndex = in.u2();
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
		// TODO extended validation for encoding in 4.2.1
		return cp.get(nameIndex).is(CONSTANT_Utf8);
	}
}
