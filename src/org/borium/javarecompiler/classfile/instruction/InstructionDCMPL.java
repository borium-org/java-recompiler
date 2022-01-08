package org.borium.javarecompiler.classfile.instruction;

/**
 * Compare double.
 */
public class InstructionDCMPL extends Instruction
{
	public InstructionDCMPL()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
