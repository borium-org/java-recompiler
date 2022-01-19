package org.borium.javarecompiler.classfile.instruction;

/**
 * Pop the top one or two operand stack values.
 */
public class InstructionPOP2 extends Instruction
{
	public InstructionPOP2()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -2;
	}
}
