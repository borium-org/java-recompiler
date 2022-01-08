package org.borium.javarecompiler.classfile.instruction;

/**
 * Negate int.
 */
public class InstructionINEG extends Instruction
{
	public InstructionINEG()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
