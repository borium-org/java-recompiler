package org.borium.javarecompiler.classfile.instruction;

/**
 * Push long constant.
 */
public class InstructionLCONST extends Instruction
{
	@SuppressWarnings("unused")
	private long value;

	public InstructionLCONST(long value)
	{
		this.value = value;
	}
}
