package org.borium.javarecompiler.classfile.instruction;

/**
 * Boolean OR long.
 */
public class InstructionLOR extends Instruction
{
	public InstructionLOR()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
