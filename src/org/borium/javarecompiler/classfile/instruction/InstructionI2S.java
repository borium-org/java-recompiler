package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert int to short.
 */
public class InstructionI2S extends Instruction
{
	public InstructionI2S()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
