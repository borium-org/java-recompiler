package org.borium.javarecompiler.classfile.instruction;

/**
 * Boolean OR int.
 */
public class InstructionIOR extends Instruction
{
	public InstructionIOR()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
