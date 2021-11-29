package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Push int constant.
 */
public class InstructionICONST extends Instruction
{
	@SuppressWarnings("unused")
	private int value;

	public InstructionICONST(ByteArrayInputStream in, int value)
	{
		this.value = value;
	}
}
