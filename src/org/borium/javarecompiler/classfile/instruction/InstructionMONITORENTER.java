package org.borium.javarecompiler.classfile.instruction;

/**
 * Enter monitor for object.
 */
public class InstructionMONITORENTER extends Instruction
{
	public InstructionMONITORENTER()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
