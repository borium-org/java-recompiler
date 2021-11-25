package org.borium.javarecompiler.classfile.constants;

import java.io.*;

import org.borium.javarecompiler.classfile.*;

/**
 * The CONSTANT_Integer_info structure represents 4-byte numeric int constant:
 *
 * <pre>
	CONSTANT_Integer_info
	{
		u1 tag;
		u4 bytes;
	}
 * </pre>
 *
 * The tag item of the CONSTANT_Integer_info structure has the value
 * CONSTANT_Integer (3).
 */
public class ConstantInteger extends Constant
{
	/**
	 * The bytes item of the CONSTANT_Integer_info structure represents the value of
	 * the int constant. The bytes of the value are stored in big-endian (high byte
	 * first) order.
	 */
	@SuppressWarnings("unused")
	private int value;

	@Override
	protected void read(ClassInputStream in) throws IOException
	{
		tag = CONSTANT_Integer;
		value = in.u4();
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