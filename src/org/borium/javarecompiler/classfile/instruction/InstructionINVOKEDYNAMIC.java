package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

public class InstructionINVOKEDYNAMIC extends Instruction
{
	/**
	 * the unsigned indexbyte1 and indexbyte2 are used to construct an index into
	 * the run-time constant pool of the current class (2.6), where the value of the
	 * index is (indexbyte1 << 8) | indexbyte2. The run-time constant pool entry at
	 * the index must be a symbolic reference to a dynamically-computed call site
	 * (5.1).
	 */
	@SuppressWarnings("unused")
	private int index;

	/**
	 * The values of the third and fourth operand bytes must always be zero.
	 */
	private int zero;

	public InstructionINVOKEDYNAMIC(ByteArrayInputStream in)
	{
		index = in.read() << 8 | in.read();
		zero = in.read() << 8 | in.read();
		if (zero != 0)
		{
			throw new ClassFormatError("INVOKEDYNAMIC non-zero");
		}
	}

	@Override
	public int length()
	{
		return 5;
	}
}
