package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert long to double.
 */
public class InstructionL2D extends Instruction
{
	public InstructionL2D()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
