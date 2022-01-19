package org.borium.javarecompiler.classfile.instruction;

/**
 * Remainder long.
 */
public class InstructionLREM extends Instruction
{
	public InstructionLREM()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
