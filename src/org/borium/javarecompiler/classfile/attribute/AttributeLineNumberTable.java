package org.borium.javarecompiler.classfile.attribute;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

public class AttributeLineNumberTable extends ClassAttribute
{
	private int[] startPc;
	private int[] lineNumber;

	public AttributeLineNumberTable(ClassAttribute attribute, ConstantPool cp)
	{
		super(attribute);
		decode(cp);
	}

	@Override
	protected void detailedDump(IndentedOutputStream stream)
	{
		stream.iprintln("Line Numbers: " + startPc.length);
		stream.indent(1);
		for (int i = 0; i < startPc.length; i++)
		{
			stream.iprint(i + ": Line " + lineNumber[i] + " PC: ");
			stream.printHex(startPc[i], 4);
			stream.println();
		}
		stream.indent(-1);
	}

	private void decode(ConstantPool cp)
	{
		ByteInputStream in = new ByteInputStream(info);
		int numberOfEntries = in.u2();
		startPc = new int[numberOfEntries];
		lineNumber = new int[numberOfEntries];
		for (int i = 0; i < numberOfEntries; i++)
		{
			startPc[i] = in.u2();
			lineNumber[i] = in.u2();
		}
		in.close();
	}
}
