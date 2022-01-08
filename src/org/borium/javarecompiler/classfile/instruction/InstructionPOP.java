package org.borium.javarecompiler.classfile.instruction;

/**
 * Pop the top operand stack value.
 */
public class InstructionPOP extends Instruction
{
	public InstructionPOP()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
