package org.borium.javarecompiler.classfile.instruction;

/**
 * Return long from method.
 */
public class InstructionLRETURN extends Instruction
{
	public InstructionLRETURN()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
