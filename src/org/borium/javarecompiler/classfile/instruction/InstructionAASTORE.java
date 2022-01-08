package org.borium.javarecompiler.classfile.instruction;

/**
 * Store into reference array.
 */
public class InstructionAASTORE extends Instruction
{
	public InstructionAASTORE()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -3;
	}
}
