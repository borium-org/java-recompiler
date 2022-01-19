package org.borium.javarecompiler.classfile.instruction;

/**
 * Multiply float.
 */
public class InstructionFMUL extends Instruction
{
	public InstructionFMUL()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
