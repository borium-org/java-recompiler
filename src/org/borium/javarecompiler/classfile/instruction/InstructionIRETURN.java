package org.borium.javarecompiler.classfile.instruction;

/**
 * Return int from method.
 */
public class InstructionIRETURN extends Instruction
{
	public InstructionIRETURN()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
