package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert double to float.
 */
public class InstructionD2F extends Instruction
{
	public InstructionD2F()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
