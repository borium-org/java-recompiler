package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Fetch field from object.
 */
public class InstructionGETFIELD extends Instruction
{
	/**
	 * The unsigned indexbyte1 and indexbyte2 are used to construct an index into
	 * the run-time constant pool of the current class (2.6), where the value of the
	 * index is (indexbyte1 << 8) | indexbyte2.
	 */
	@SuppressWarnings("unused")
	private int index;

	public InstructionGETFIELD(ByteArrayInputStream in)
	{
		index = in.read() << 8 | in.read();
	}

	@Override
	public int length()
	{
		return 3;
	}
}
