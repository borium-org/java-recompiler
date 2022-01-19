package org.borium.javarecompiler.classfile.instruction;

/**
 * Negate float.
 */
public class InstructionFNEG extends Instruction
{
	public InstructionFNEG()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
