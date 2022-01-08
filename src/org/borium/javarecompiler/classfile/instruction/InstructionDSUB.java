package org.borium.javarecompiler.classfile.instruction;

/**
 * Subtract double.
 */
public class InstructionDSUB extends Instruction
{
	public InstructionDSUB()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
