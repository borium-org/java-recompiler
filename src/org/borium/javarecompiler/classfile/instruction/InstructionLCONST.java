package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Push long constant.
 */
public class InstructionLCONST extends Instruction
{
	@SuppressWarnings("unused")
	private long value;

	public InstructionLCONST(ByteArrayInputStream in, long value)
	{
		this.value = value;
	}
}
