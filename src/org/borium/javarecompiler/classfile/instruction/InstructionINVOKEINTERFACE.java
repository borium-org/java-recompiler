package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

public class InstructionINVOKEINTERFACE extends Instruction
{
	/**
	 * The unsigned indexbyte1 and indexbyte2 are used to construct an index into
	 * the run-time constant pool of the current class (2.6), where the value of the
	 * index is (indexbyte1 << 8) | indexbyte2. The run-time constant pool entry at
	 * the index must be a symbolic reference to an interface method (5.1), which
	 * gives the name and descriptor (4.3.3) of the interface method as well as a
	 * symbolic reference to the interface in which the interface method is to be
	 * found.
	 */
	@SuppressWarnings("unused")
	private int index;

	/**
	 * The count operand is an unsigned byte that must not be zero.
	 */
	private int count;
	/**
	 * The value of the fourth operand byte must always be zero.
	 */
	private int zero;

	public InstructionINVOKEINTERFACE(ByteArrayInputStream in)
	{
		index = in.read() << 8 | in.read();
		count = in.read();
		if (count == 0)
		{
			throw new ClassFormatError("INVOKEINTERFACE count-zero");
		}
		zero = in.read();
		if (zero != 0)
		{
			throw new ClassFormatError("INVOKEINTERFACE non-zero");
		}
	}

	@Override
	public int length()
	{
		return 5;
	}
}
