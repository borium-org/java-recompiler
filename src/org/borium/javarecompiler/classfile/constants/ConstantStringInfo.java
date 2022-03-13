package org.borium.javarecompiler.classfile.constants;

import org.borium.javarecompiler.classfile.*;

/**
 * The CONSTANT_String_info structure is used to represent constant objects of
 * the type String:
 *
 * <pre>
	CONSTANT_String_info
	{
		u1 tag;
		u2 string_index;
	}
 * </pre>
 *
 * The tag item has the value CONSTANT_String (8).
 */
public class ConstantStringInfo extends Constant
{
	/**
	 * The value of the string_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_Utf8_info structure (4.4.7) representing the sequence of Unicode
	 * code points to which the String object is to be initialized.
	 */
	private int stringIndex;

	private String string;

	@Override
	public void dump(IndentedOutputStream stream)
	{
		stream.print("String: " + stringIndex + " '" + string + "'");
	}

	public String getString()
	{
		return string;
	}

	public String getValue(ConstantPool constantPool)
	{
		return constantPool.getString(stringIndex);
	}

	@Override
	protected void fixup(ConstantPool constantPool)
	{
		string = constantPool.getString(stringIndex);
	}

	@Override
	protected void read(ByteInputStream in)
	{
		tag = CONSTANT_String;
		stringIndex = in.u2();
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
		if (!cp.get(stringIndex).is(CONSTANT_Utf8))
		{
			return false;
		}
		return true;
	}
}
