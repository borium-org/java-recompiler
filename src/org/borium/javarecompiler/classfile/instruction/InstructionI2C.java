package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert int to char.
 */
public class InstructionI2C extends Instruction
{
	public InstructionI2C()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
