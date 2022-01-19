package org.borium.javarecompiler.classfile.instruction;

/**
 * Load float from array.
 */
public class InstructionFALOAD extends Instruction
{
	public InstructionFALOAD()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
