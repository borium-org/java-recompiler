package org.borium.javarecompiler.classfile.instruction;

/**
 * Add int.
 */
public class InstructionIADD extends Instruction
{
	public InstructionIADD()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
