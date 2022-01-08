package org.borium.javarecompiler.classfile.instruction;

public class InstructionNOP extends Instruction
{
	public InstructionNOP()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
