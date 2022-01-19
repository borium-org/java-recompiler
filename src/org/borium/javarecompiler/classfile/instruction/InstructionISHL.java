package org.borium.javarecompiler.classfile.instruction;

/**
 * Shift left int.
 */
public class InstructionISHL extends Instruction
{
	public InstructionISHL()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
