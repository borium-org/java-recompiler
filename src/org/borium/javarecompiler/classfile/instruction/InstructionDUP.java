package org.borium.javarecompiler.classfile.instruction;

/**
 * Duplicate the top operand stack value.
 */
public class InstructionDUP extends Instruction
{
	public InstructionDUP()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}
}
