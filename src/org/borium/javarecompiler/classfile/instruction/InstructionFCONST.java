package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Push float.
 */
public class InstructionFCONST extends Instruction
{
	@SuppressWarnings("unused")
	private double value;

	public InstructionFCONST(ByteArrayInputStream in, double value)
	{
		this.value = value;
	}
}
