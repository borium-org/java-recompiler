package org.borium.javarecompiler.classfile.instruction;

/**
 * Multiply double.
 */
public class InstructionDMUL extends Instruction
{
	public InstructionDMUL()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
