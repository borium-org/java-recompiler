package org.borium.javarecompiler.classfile.constants;

import java.io.*;

import org.borium.javarecompiler.classfile.*;

/**
 * The CONSTANT_Float_info structure represents 4-byte numeric float constant:
 *
 * <pre>
	CONSTANT_Float_info
	{
		u1 tag;
		u4 bytes;
	}
 * </pre>
 *
 * The tag item of the CONSTANT_Float_info structure has the value
 * CONSTANT_Float (4).
 */
public class ConstantFloat extends Constant
{
	/**
	 * The bytes item of the CONSTANT_Float_info structure represents the value of
	 * the float constant in IEEE 754 binary32 floating-point format (2.3.2). The
	 * bytes of the item are stored in big-endian (high byte first) order.
	 */
	@SuppressWarnings("unused")
	private double value;

	@Override
	protected void read(ClassInputStream in) throws IOException
	{
		tag = CONSTANT_Float;
		value = in.f4();
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
		return true;
	}
}
