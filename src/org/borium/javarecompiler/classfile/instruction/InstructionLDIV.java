package org.borium.javarecompiler.classfile.instruction;

/**
 * Divide long.
 */
public class InstructionLDIV extends Instruction
{
	public InstructionLDIV()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
