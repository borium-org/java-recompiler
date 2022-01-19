package org.borium.javarecompiler.classfile.instruction;

/**
 * Compare double.
 */
public class InstructionDCMPG extends Instruction
{
	public InstructionDCMPG()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
