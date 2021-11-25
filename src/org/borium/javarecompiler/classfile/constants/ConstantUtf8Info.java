package org.borium.javarecompiler.classfile.constants;

import java.io.*;

import org.borium.javarecompiler.classfile.*;

/**
 * The CONSTANT_Utf8_info structure is used to represent constant string values:
 *
 * <pre>
	CONSTANT_Utf8_info
	{
		u1 tag;
		u2 length;
		u1 bytes[length];
	}
 * </pre>
 *
 * The tag item has the value CONSTANT_Utf8 (1).
 */
public class ConstantUtf8Info extends Constant
{
	/**
	 * String content is encoded in modified UTF-8. Modified UTF-8 strings are
	 * encoded so that code point sequences that contain only non-null ASCII
	 * characters can be represented using only 1 byte per code point, but all code
	 * points in the Unicode codespace can be represented. Modified UTF-8 strings
	 * are not null-terminated.
	 */
	@SuppressWarnings("unused")
	private String utf8;

	@Override
	protected void read(ClassInputStream in) throws IOException
	{
		utf8 = in.readUtf8();
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