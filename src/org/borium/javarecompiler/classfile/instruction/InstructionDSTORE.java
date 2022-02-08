package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Store double into local variable.
 */
public class InstructionDSTORE extends InstructionWithLocalVariableIndex
{
	public InstructionDSTORE(ByteInputStream in, boolean wide)
	{
		super(in, wide);
	}

	public InstructionDSTORE(int index)
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
