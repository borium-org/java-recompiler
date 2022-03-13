package org.borium.javarecompiler.classfile.constants;

import org.borium.javarecompiler.classfile.*;

/**
 * The CONSTANT_MethodType_info structure is used to represent a method type:
 *
 * <pre>
	CONSTANT_MethodType_info
	{
		u1 tag;
		u2 descriptor_index;
	}
 * </pre>
 *
 * The tag item has the value CONSTANT_MethodType (16).
 */
public class ConstantMethodType extends Constant
{
	/**
	 * The value of the descriptor_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_Utf8_info structure (4.4.7) representing a method descriptor
	 * (4.3.3).
	 */
	private int descriptorIndex;

	private String descriptor;

	@Override
	public void dump(IndentedOutputStream stream)
	{
		stream.println("MethodType: " + descriptor);
	}

	@Override
	protected void fixup(ConstantPool constantPool)
	{
		descriptor = constantPool.getString(descriptorIndex);
	}

	@Override
	protected void read(ByteInputStream in)
	{
		tag = CONSTANT_MethodType;
		descriptorIndex = in.u2();
	}

	@Override
	protected boolean verify(int majorVersion, int minorVersion, ConstantPool cp, int index)
	{
		if (majorVersion < 51 || minorVersion != 0)
		{
			return false;
		}
		if (!cp.get(descriptorIndex).is(CONSTANT_Utf8))
		{
			return false;
		}
		// TODO extended descriptor validation
		return true;
	}
}
