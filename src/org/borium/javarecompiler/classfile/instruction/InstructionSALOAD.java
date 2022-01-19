package org.borium.javarecompiler.classfile.instruction;

/**
 * Load short from array.
 */
public class InstructionSALOAD extends Instruction
{
	public InstructionSALOAD()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
