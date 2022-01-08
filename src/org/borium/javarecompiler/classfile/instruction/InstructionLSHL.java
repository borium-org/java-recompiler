package org.borium.javarecompiler.classfile.instruction;

/**
 * Shift left long.
 */
public class InstructionLSHL extends Instruction
{
	public InstructionLSHL()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
