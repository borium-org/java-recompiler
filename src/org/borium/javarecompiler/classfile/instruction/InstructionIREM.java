package org.borium.javarecompiler.classfile.instruction;

/**
 * Remainder int.
 */
public class InstructionIREM extends Instruction
{
	public InstructionIREM()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
