package org.borium.javarecompiler.classfile.instruction;

/**
 * Remainder float.
 */
public class InstructionFREM extends Instruction
{
	public InstructionFREM()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
