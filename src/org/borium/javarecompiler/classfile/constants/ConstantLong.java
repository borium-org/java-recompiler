package org.borium.javarecompiler.classfile.constants;

import org.borium.javarecompiler.classfile.*;

/**
 * The CONSTANT_Long_info represents 8-byte numeric long constants:
 *
 * <pre>
	CONSTANT_Long_info
	{
		u1 tag;
		u4 high_bytes;
		u4 low_bytes;
	}
 * </pre>
 *
 * The tag item of the CONSTANT_Long_info structure has the value CONSTANT_Long
 * (5).
 */
public class ConstantLong extends Constant
{
	/**
	 * The unsigned high_bytes and low_bytes items of the CONSTANT_Long_info
	 * structure together represent the value of the long constant ((long)
	 * high_bytes << 32) + low_bytes where the bytes of each of high_bytes and
	 * low_bytes are stored in big-endian (high byte first) order.
	 */
	public long value;

	@Override
	public void dump(IndentedOutputStream stream)
	{
		stream.print("Long: " + value);
	}

	@Override
	protected void read(ByteInputStream in)
	{
		tag = CONSTANT_Long;
		value = in.u8();
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
		if (cp.get(index + 1) != null)
		{
			return false;
		}
		return true;
	}
}
