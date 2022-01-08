package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert float to double.
 */
public class InstructionF2D extends Instruction
{
	public InstructionF2D()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
