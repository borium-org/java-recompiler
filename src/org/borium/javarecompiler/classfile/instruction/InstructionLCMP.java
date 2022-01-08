package org.borium.javarecompiler.classfile.instruction;

/**
 * Compare long.
 */
public class InstructionLCMP extends Instruction
{
	public InstructionLCMP()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
