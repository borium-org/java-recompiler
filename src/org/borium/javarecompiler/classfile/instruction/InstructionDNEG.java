package org.borium.javarecompiler.classfile.instruction;

/**
 * Negate double.
 */
public class InstructionDNEG extends Instruction
{
	public InstructionDNEG()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
