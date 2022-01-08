package org.borium.javarecompiler.classfile.instruction;

/**
 * Swap the top two operand stack values.
 */
public class InstructionSWAP extends Instruction
{
	public InstructionSWAP()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
