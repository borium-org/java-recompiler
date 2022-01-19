package org.borium.javarecompiler.classfile.attribute;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

public class AttributeSourceFile extends ClassAttribute
{
	/**
	 * The value of the sourcefile_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_Utf8_info structure representing a string.
	 */
	private int index;

	private String sourceFile;

	public AttributeSourceFile(ClassAttribute attribute, ConstantPool cp)
	{
		super(attribute);
		decode(cp);
		sourceFile = cp.getString(index);
	}

	@Override
	protected void detailedDump(IndentedOutputStream stream)
	{
		stream.iprintln("Source File: " + sourceFile);
	}

	private void decode(ConstantPool cp)
	{
		ByteInputStream in = new ByteInputStream(info);
		index = in.u2();
		in.close();
	}
}
