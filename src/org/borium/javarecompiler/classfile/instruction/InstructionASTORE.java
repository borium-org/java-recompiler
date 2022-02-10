package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Store reference into local variable.
 */
public class InstructionASTORE extends InstructionWithLocalVariableIndex
{
	public InstructionASTORE(ByteInputStream in, boolean wide)
	{
		super(in, wide);
	}

	public InstructionASTORE(int index)
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
