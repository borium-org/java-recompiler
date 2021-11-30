package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

public class InstructionLDC_W extends Instruction
{
	/**
	 * The unsigned indexbyte1 and indexbyte2 are assembled into an unsigned 16-bit
	 * index into the run-time constant pool of the current class (2.5.5), where the
	 * value of the index is calculated as (indexbyte1 << 8) | indexbyte2. The index
	 * must be a valid index into the run-time constant pool of the current class.
	 */
	@SuppressWarnings("unused")
	private int index;

	public InstructionLDC_W(ByteArrayInputStream in)
	{
		index = in.read() << 8 | in.read();
	}

	@Override
	public int length()
	{
		return 3;
	}
}
