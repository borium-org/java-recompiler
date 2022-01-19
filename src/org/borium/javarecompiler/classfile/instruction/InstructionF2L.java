package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert float to long.
 */
public class InstructionF2L extends Instruction
{
	public InstructionF2L()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
