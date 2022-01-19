package org.borium.javarecompiler.classfile.instruction;

/**
 * Store into byte or boolean array.
 */
public class InstructionBASTORE extends Instruction
{
	public InstructionBASTORE()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -3;
	}
}
