package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert long to int.
 */
public class InstructionL2I extends Instruction
{
	public InstructionL2I()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
