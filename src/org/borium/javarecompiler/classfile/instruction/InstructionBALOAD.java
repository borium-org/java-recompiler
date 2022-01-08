package org.borium.javarecompiler.classfile.instruction;

/**
 * Load byte or boolean from array.
 */
public class InstructionBALOAD extends Instruction
{
	public InstructionBALOAD()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
