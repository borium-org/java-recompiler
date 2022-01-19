package org.borium.javarecompiler.classfile.instruction;

/**
 * Divide float.
 */
public class InstructionFDIV extends Instruction
{
	public InstructionFDIV()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
