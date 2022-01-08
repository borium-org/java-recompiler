package org.borium.javarecompiler.classfile.instruction;

/**
 * Boolean AND long.
 */
public class InstructionLAND extends Instruction
{
	public InstructionLAND()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
