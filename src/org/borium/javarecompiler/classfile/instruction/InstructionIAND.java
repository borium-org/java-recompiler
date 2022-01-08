package org.borium.javarecompiler.classfile.instruction;

/**
 * Boolean AND int.
 */
public class InstructionIAND extends Instruction
{
	public InstructionIAND()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
