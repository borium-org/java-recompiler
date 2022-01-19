package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert double to long.
 */
public class InstructionD2L extends Instruction
{
	public InstructionD2L()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
