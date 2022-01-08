package org.borium.javarecompiler.classfile.instruction;

/**
 * Duplicate the top one or two operand stack values.
 */
public class InstructionDUP2 extends Instruction
{
	public InstructionDUP2()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 2;
	}
}
