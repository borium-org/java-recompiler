package org.borium.javarecompiler.classfile.instruction;

/**
 * Logical shift right int.
 */
public class InstructionIUSHR extends Instruction
{
	public InstructionIUSHR()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
