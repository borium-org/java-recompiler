package org.borium.javarecompiler.classfile.instruction;

/**
 * Return float from method.
 */
public class InstructionFRETURN extends Instruction
{
	public InstructionFRETURN()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
