package org.borium.javarecompiler.classfile.instruction;

/**
 * Duplicate the top operand stack value and insert two or three values down.
 */
public class InstructionDUP_X2 extends Instruction
{
	public InstructionDUP_X2()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}
}
