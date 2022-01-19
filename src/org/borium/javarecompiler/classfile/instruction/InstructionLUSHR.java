package org.borium.javarecompiler.classfile.instruction;

/**
 * Logical shift right long.
 */
public class InstructionLUSHR extends Instruction
{
	public InstructionLUSHR()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
