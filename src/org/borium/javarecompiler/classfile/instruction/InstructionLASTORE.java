package org.borium.javarecompiler.classfile.instruction;

/**
 * Store into long array.
 */
public class InstructionLASTORE extends Instruction
{
	public InstructionLASTORE()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -3;
	}
}
