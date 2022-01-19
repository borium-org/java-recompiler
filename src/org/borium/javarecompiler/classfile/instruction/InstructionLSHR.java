package org.borium.javarecompiler.classfile.instruction;

/**
 * Arithmetic shift right long.
 */
public class InstructionLSHR extends Instruction
{
	public InstructionLSHR()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
