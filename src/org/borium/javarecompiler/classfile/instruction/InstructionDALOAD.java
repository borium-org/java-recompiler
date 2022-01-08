package org.borium.javarecompiler.classfile.instruction;

/**
 * Load double from array.
 */
public class InstructionDALOAD extends Instruction
{
	public InstructionDALOAD()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
