package org.borium.javarecompiler.classfile.instruction;

/**
 * Negate long.
 */
public class InstructionLNEG extends Instruction
{
	public InstructionLNEG()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
