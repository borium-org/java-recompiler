package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert int to float.
 */
public class InstructionI2F extends Instruction
{
	public InstructionI2F()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
