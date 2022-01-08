package org.borium.javarecompiler.classfile.instruction;

/**
 * Subtract int.
 */
public class InstructionISUB extends Instruction
{
	public InstructionISUB()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
