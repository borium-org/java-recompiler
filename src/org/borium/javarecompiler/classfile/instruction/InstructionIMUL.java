package org.borium.javarecompiler.classfile.instruction;

/**
 * Multiply int.
 */
public class InstructionIMUL extends Instruction
{
	public InstructionIMUL()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
