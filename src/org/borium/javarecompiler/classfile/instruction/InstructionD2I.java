package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert double to int.
 */
public class InstructionD2I extends Instruction
{
	public InstructionD2I()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
