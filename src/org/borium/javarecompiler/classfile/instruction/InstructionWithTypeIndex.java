package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

abstract class InstructionWithTypeIndex extends Instruction
{
	/**
	 * The unsigned indexbyte1 and indexbyte2 are used to construct an index into
	 * the run-time constant pool of the current class (2.6), where the value of the
	 * index is (indexbyte1 << 8) | indexbyte2. The run-time constant pool entry at
	 * the index must be a symbolic reference to a class, array, or interface type.
	 */
	private int index;

	private ConstantClassInfo classInfo;

	private String methodClassName;

	public InstructionWithTypeIndex(ByteInputStream in, ConstantPool cp)
	{
		index = in.u2();
		classInfo = cp.get(index, ConstantClassInfo.class);
		methodClassName = cp.getString(classInfo.nameIndex).replace('/', '.');
	}

	@Override
	public void detailedDump(IndentedOutputStream stream)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		stream.iprintln(className + " " + methodClassName);
	}

	public String getMethodClassName()
	{
		return methodClassName;
	}

	@Override
	public int length()
	{
		return 3;
	}
}
