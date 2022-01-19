package org.borium.javarecompiler.classfile.instruction;

/**
 * Convert long to float.
 */
public class InstructionL2F extends Instruction
{
	public InstructionL2F()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
