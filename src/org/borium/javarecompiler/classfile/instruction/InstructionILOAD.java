package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionILOAD extends InstructionWithLocalVariableIndex
{
	public InstructionILOAD(ByteInputStream in, boolean wide)
	{
		super(in, wide);
	}

	public InstructionILOAD(int index)
	{
		super(index);
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}
}
