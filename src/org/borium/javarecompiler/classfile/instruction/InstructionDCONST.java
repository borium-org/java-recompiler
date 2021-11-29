package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Push double.
 */
public class InstructionDCONST extends Instruction
{
	@SuppressWarnings("unused")
	private double value;

	public InstructionDCONST(ByteArrayInputStream in, double value)
	{
		this.value = value;
	}
}
