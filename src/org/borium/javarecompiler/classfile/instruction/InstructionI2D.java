package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert int to double.
 */
public class InstructionI2D extends Instruction
{
	public InstructionI2D()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
