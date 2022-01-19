package org.borium.javarecompiler.classfile.instruction;

/**
 * Get length of array.
 */
public class InstructionARRAYLENGTH extends Instruction
{
	public InstructionARRAYLENGTH()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
