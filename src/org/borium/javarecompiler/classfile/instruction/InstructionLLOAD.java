package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Load long from local variable.
 */
public class InstructionLLOAD extends InstructionWithLocalVariableIndex
{
	public InstructionLLOAD(ByteInputStream in, boolean wide)
	{
		super(in, wide);
	}

	public InstructionLLOAD(int index)
	{
		super(index);
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}
}
