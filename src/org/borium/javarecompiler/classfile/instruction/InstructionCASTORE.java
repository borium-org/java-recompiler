package org.borium.javarecompiler.classfile.instruction;

/**
 * Store into char array.
 */
public class InstructionCASTORE extends Instruction
{
	public InstructionCASTORE()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -3;
	}
}
