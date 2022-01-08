package org.borium.javarecompiler.classfile.instruction;

/**
 * Exit monitor for object.
 */
public class InstructionMONITOREXIT extends Instruction
{
	public InstructionMONITOREXIT()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
