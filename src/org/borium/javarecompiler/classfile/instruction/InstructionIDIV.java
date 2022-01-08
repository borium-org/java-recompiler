package org.borium.javarecompiler.classfile.instruction;

/**
 * Divide int.
 */
public class InstructionIDIV extends Instruction
{
	public InstructionIDIV()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
