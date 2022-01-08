package org.borium.javarecompiler.classfile.instruction;

/**
 * Arithmetic shift right int.
 */
public class InstructionISHR extends Instruction
{
	public InstructionISHR()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
