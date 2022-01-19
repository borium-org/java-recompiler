package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionFLOAD extends InstructionWithLocalVariableIndex
{
	public InstructionFLOAD(ByteInputStream in, boolean wide)
	{
		super(in, wide);
	}

	public InstructionFLOAD(int index)
	{
		super(index);
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}
}
