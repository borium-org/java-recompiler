package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Push item from run-time constant pool.
 */
public class InstructionLDC extends Instruction
{
	@SuppressWarnings("unused")
	private int index;

	public InstructionLDC(ByteArrayInputStream in)
	{
		index = in.read();
	}

	@Override
	public int length()
	{
		return 2;
	}
}
