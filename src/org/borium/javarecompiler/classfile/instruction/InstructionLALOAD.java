package org.borium.javarecompiler.classfile.instruction;

/**
 * Load long from array.
 */
public class InstructionLALOAD extends Instruction
{
	public InstructionLALOAD()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
