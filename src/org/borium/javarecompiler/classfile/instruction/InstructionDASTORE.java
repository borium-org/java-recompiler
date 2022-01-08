package org.borium.javarecompiler.classfile.instruction;

/**
 * Store into double array.
 */
public class InstructionDASTORE extends Instruction
{
	public InstructionDASTORE()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -3;
	}
}
