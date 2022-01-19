package org.borium.javarecompiler.classfile.instruction;

/**
 * Divide double.
 */
public class InstructionDDIV extends Instruction
{
	public InstructionDDIV()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
