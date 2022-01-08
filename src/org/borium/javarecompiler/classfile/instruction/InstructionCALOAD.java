package org.borium.javarecompiler.classfile.instruction;

/**
 * Load char from array.
 */
public class InstructionCALOAD extends Instruction
{
	public InstructionCALOAD()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
