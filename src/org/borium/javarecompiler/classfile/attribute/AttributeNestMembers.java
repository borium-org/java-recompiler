package org.borium.javarecompiler.classfile.attribute;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

public class AttributeNestMembers extends ClassAttribute
{
	/**
	 * Each value in the classes array must be a valid index into the constant_pool
	 * table. The constant_pool entry at that index must be a CONSTANT_Class_info
	 * structure (4.4.1) representing a class or interface which is a member of the
	 * nest hosted by the current class or interface.
	 *
	 */
	private int[] classTable;

	public AttributeNestMembers(ClassAttribute attribute, ConstantPool cp)
	{
		super(attribute);
		decode(cp);
	}

	@Override
	protected void detailedDump(IndentedOutputStream stream, ConstantPool cp)
	{
		stream.iprintln("Nest members: " + classTable.length);
		stream.indent(1);
		for (int i = 0; i < classTable.length; i++)
		{
			stream.iprint(i + ": " + classTable[i] + " ");
			ConstantClassInfo classInfo = cp.get(classTable[i], ConstantClassInfo.class);
			classInfo.dump(stream, cp);
			stream.println();
		}
		stream.indent(-1);
	}

	private void decode(ConstantPool cp)
	{
		ByteInputStream in = new ByteInputStream(info);
		int numberOfClasses = in.u2();
		classTable = new int[numberOfClasses];
		for (int i = 0; i < numberOfClasses; i++)
		{
			classTable[i] = in.u2();
		}
		in.close();
	}
}
