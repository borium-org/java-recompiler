package org.borium.javarecompiler.classfile.instruction;

/**
 * Return reference from method.
 */
public class InstructionARETURN extends Instruction
{
	public InstructionARETURN()
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
