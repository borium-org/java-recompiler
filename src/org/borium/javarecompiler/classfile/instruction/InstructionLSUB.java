package org.borium.javarecompiler.classfile.instruction;

/**
 * Subtract long.
 */
public class InstructionLSUB extends Instruction
{
	public InstructionLSUB()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
