package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Store int into local variable.
 */
public class InstructionISTORE extends InstructionWithLocalVariableIndex
{
	public InstructionISTORE(ByteInputStream in, boolean wide)
	{
		super(in, wide);
	}

	public InstructionISTORE(int index)
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
