package org.borium.javarecompiler.classfile.instruction;

/**
 * Add float.
 */
public class InstructionFADD extends Instruction
{
	public InstructionFADD()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
