package org.borium.javarecompiler.classfile.instruction;

/**
 * Remainder double.
 */
public class InstructionDREM extends Instruction
{
	public InstructionDREM()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
