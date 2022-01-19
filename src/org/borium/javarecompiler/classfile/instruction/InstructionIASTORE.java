package org.borium.javarecompiler.classfile.instruction;

/**
 * Store into int array.
 */
public class InstructionIASTORE extends Instruction
{
	public InstructionIASTORE()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -3;
	}
}
