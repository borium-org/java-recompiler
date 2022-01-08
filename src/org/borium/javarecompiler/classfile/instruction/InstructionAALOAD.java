package org.borium.javarecompiler.classfile.instruction;

/**
 * Load reference from array.
 */
public class InstructionAALOAD extends Instruction
{
	public InstructionAALOAD()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
