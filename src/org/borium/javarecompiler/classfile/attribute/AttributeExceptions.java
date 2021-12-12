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

	public AttributeExceptions(ClassAttribute attribute, ConstantPool cp)
	{
		super(attribute);
		decode(cp);
	}

	@Override
	protected void detailedDump(IndentedOutputStream stream, ConstantPool cp)
	{
		stream.iprintln("Exceptions: " + exceptionIndexTable.length);
		stream.indent(1);
		for (int i = 0; i < exceptionIndexTable.length; i++)
		{
			stream.iprint(i + ": " + exceptionIndexTable[i] + " ");
			ConstantClassInfo classInfo = cp.get(exceptionIndexTable[i], ConstantClassInfo.class);
			classInfo.dump(stream, cp);
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
