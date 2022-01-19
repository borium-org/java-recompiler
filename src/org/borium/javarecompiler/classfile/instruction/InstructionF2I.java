package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert float to int.
 */
public class InstructionF2I extends Instruction
{
	public InstructionF2I()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
