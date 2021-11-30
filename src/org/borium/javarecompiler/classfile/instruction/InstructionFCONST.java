package org.borium.javarecompiler.classfile.instruction;

/**
 * Push float.
 */
public class InstructionFCONST extends Instruction
{
	@SuppressWarnings("unused")
	private double value;

	public InstructionFCONST(double value)
	{
		this.value = value;
	}
}
