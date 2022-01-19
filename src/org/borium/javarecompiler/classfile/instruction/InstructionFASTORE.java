package org.borium.javarecompiler.classfile.instruction;

/**
 * Store into float array.
 */
public class InstructionFASTORE extends Instruction
{
	public InstructionFASTORE()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -3;
	}
}
