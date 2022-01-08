package org.borium.javarecompiler.classfile.instruction;

/**
 * Boolean XOR int.
 */
public class InstructionIXOR extends Instruction
{
	public InstructionIXOR()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
