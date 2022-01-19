package org.borium.javarecompiler.classfile.instruction;

/**
 * Multiply long.
 */
public class InstructionLMUL extends Instruction
{
	public InstructionLMUL()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
