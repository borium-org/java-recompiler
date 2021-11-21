package org.borium.javarecompiler.classfile.constants;

import java.io.*;

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
	private int nameIndex;

	/**
	 * The value of the descriptor_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_Utf8_info structure (4.4.7) representing a valid field descriptor or
	 * method descriptor (4.3.2, 4.3.3).
	 */
	private int descriptorIndex;

	@Override
	protected void read(ClassInputStream in) throws IOException
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
}
