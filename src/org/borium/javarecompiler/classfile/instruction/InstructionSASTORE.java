package org.borium.javarecompiler.classfile.instruction;

/**
 * Store into short array.
 */
public class InstructionSASTORE extends Instruction
{
	public InstructionSASTORE()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -3;
	}
}
