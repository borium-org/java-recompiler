package org.borium.javarecompiler.classfile.instruction;

/**
 * Push null.
 */
public class InstructionACONST_NULL extends Instruction
{
	public InstructionACONST_NULL()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}
}
