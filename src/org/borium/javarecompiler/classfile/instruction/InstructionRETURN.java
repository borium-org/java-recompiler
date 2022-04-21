package org.borium.javarecompiler.classfile.instruction;

/**
 * Return void from method.
 */
public class InstructionRETURN extends Instruction
{
	public InstructionRETURN()
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
		return 0;
	}
}
