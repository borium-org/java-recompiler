package org.borium.javarecompiler.classfile.instruction;

/**
 * Duplicate the top operand stack value and insert two values down.
 */
public class InstructionDUP_X1 extends Instruction
{
	public InstructionDUP_X1()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}
}
