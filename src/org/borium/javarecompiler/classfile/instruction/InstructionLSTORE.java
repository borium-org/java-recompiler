package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Store long into local variable.
 */
public class InstructionLSTORE extends InstructionWithLocalVariableIndex
{
	public InstructionLSTORE(ByteInputStream in, boolean wide)
	{
		super(in, wide);
	}

	public InstructionLSTORE(int index)
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
