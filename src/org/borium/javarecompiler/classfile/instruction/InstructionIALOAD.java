package org.borium.javarecompiler.classfile.instruction;

/**
 * Load int from array.
 */
public class InstructionIALOAD extends Instruction
{
	public InstructionIALOAD()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
