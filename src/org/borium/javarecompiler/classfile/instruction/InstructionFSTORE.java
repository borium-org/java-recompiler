package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionFSTORE extends InstructionWithLocalVariableIndex
{
	public InstructionFSTORE(ByteInputStream in, boolean wide)
	{
		super(in, wide);
	}

	public InstructionFSTORE(int index)
	{
		super(index);
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}

	@Override
	public boolean isXStore()
	{
		return true;
	}
}
