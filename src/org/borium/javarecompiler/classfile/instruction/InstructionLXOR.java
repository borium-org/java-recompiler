package org.borium.javarecompiler.classfile.instruction;

/**
 * Boolean XOR long.
 */
public class InstructionLXOR extends Instruction
{
	public InstructionLXOR()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
