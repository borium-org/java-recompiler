package org.borium.javarecompiler.classfile.instruction;

/**
 * Subtract float.
 */
public class InstructionFSUB extends Instruction
{
	public InstructionFSUB()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
