package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionDLOAD extends InstructionWithLocalVariableIndex
{
	public InstructionDLOAD(ByteInputStream in, boolean wide)
	{
		super(in, wide);
	}

	public InstructionDLOAD(int index)
	{
		super(index);
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}
}
