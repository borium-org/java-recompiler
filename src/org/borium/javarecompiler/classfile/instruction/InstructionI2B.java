package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert int to byte.
 */
public class InstructionI2B extends Instruction
{
	public InstructionI2B()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
