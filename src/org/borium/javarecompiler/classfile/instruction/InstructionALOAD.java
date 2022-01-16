package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Load reference from local variable.
 */
public class InstructionALOAD extends InstructionWithLocalVariableIndex
{
	public InstructionALOAD(ByteInputStream in, boolean wide)
	{
		super(in, wide);
	}

	public InstructionALOAD(int index)
	{
		super(index);
	}

	public int getIndex()
	{
		return index;
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}
}
