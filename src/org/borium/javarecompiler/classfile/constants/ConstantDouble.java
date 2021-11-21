package org.borium.javarecompiler.classfile.constants;

import java.io.*;

import org.borium.javarecompiler.classfile.*;

/**
 * The CONSTANT_Double_info represents 8-byte numeric double constants:
 *
 * <pre>
	CONSTANT_Double_info
	{
		u1 tag;
		u4 high_bytes;
		u4 low_bytes;
	}
 * </pre>
 *
 * The tag item of the CONSTANT_Double_info structure has the value
 * CONSTANT_Double (6).
 */
public class ConstantDouble extends Constant
{
	/**
	 * The high_bytes and low_bytes items of the CONSTANT_Double_info structure
	 * together represent the double value in IEEE 754 binary64 floating point
	 * format (2.3.2). The bytes of each item are stored in big-endian (high byte
	 * first) order.
	 */
	@SuppressWarnings("unused")
	private double value;

	@Override
	protected void read(ClassInputStream in) throws IOException
	{
		tag = CONSTANT_Double;
		value = in.f8();
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
