package org.borium.javarecompiler.classfile.instruction;

/**
 * Push double.
 */
public class InstructionDCONST extends Instruction
{
	@SuppressWarnings("unused")
	private double value;

	public InstructionDCONST(double value)
	{
		this.value = value;
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}
}
