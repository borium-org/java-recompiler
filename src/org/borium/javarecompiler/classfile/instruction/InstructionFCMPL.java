package org.borium.javarecompiler.classfile.instruction;

/**
 * Compare float.
 */
public class InstructionFCMPL extends Instruction
{
	public InstructionFCMPL()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
