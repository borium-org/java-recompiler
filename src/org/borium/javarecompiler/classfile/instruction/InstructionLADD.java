package org.borium.javarecompiler.classfile.instruction;

/**
 * Add long.
 */
public class InstructionLADD extends Instruction
{
	public InstructionLADD()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
