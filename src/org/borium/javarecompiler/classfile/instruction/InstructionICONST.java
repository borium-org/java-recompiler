package org.borium.javarecompiler.classfile.instruction;

/**
 * Push int constant.
 */
public class InstructionICONST extends Instruction
{
	@SuppressWarnings("unused")
	private int value;

	public InstructionICONST(int value)
	{
		this.value = value;
	}
}
