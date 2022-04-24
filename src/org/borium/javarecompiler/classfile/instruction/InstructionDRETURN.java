package org.borium.javarecompiler.classfile.instruction;

/**
 * Return double from method.
 */
public class InstructionDRETURN extends Instruction
{
	public InstructionDRETURN()
	{
	}

	@Override
	public boolean fallsThrough()
	{
		return false;
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
