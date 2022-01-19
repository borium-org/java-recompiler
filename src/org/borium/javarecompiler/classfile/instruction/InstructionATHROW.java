package org.borium.javarecompiler.classfile.instruction;

/**
 * Throw exception or error
 */
public class InstructionATHROW extends Instruction
{
	public InstructionATHROW()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
