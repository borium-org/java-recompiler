package org.borium.javarecompiler.classfile.instruction;

/**
 * Compare float.
 */
public class InstructionFCMPG extends Instruction
{
	public InstructionFCMPG()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
