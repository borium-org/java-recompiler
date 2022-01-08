package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert int to long.
 */
public class InstructionI2L extends Instruction
{
	public InstructionI2L()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
