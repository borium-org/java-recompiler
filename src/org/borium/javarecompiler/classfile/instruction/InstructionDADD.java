package org.borium.javarecompiler.classfile.instruction;

/**
 * Add double.
 */
public class InstructionDADD extends Instruction
{
	public InstructionDADD()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
