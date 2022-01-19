package org.borium.javarecompiler.classfile.attribute;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

public class AttributeExceptions extends ClassAttribute
{
	/**
	 * Each value in the exception_index_table array must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_Class_info structure (4.4.1) representing a class type that this
	 * method is declared to throw.
	 */
	private int[] exceptionIndexTable;

	private ConstantClassInfo[] classInfo;

	public AttributeExceptions(ClassAttribute attribute, ConstantPool cp)
	{
		super(attribute);
		decode(cp);
		classInfo = new ConstantClassInfo[exceptionIndexTable.length];
		for (int i = 0; i < classInfo.length; i++)
		{
			classInfo[i] = cp.get(exceptionIndexTable[i], ConstantClassInfo.class);
		}
	}

	@Override
	protected void detailedDump(IndentedOutputStream stream)
	{
		stream.iprintln("Exceptions: " + exceptionIndexTable.length);
		stream.indent(1);
		for (int i = 0; i < exceptionIndexTable.length; i++)
		{
			stream.iprint(i + ": " + exceptionIndexTable[i] + " ");
			classInfo[i].dump(stream);
			stream.println();
		}
		stream.indent(-1);
	}

	private void decode(ConstantPool cp)
	{
		ByteInputStream in = new ByteInputStream(info);
		int numberOfExceptions = in.u2();
		exceptionIndexTable = new int[numberOfExceptions];
		for (int i = 0; i < numberOfExceptions; i++)
		{
			exceptionIndexTable[i] = in.u2();
		}
		in.close();
	}
}
