package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Return from subroutine.
 */
public class InstructionRET extends Instruction
{
	/**
	 * The index is an unsigned byte between 0 and 255, inclusive. The local
	 * variable at index in the current frame (2.6) must contain a value of type
	 * returnAddress.
	 */
	@SuppressWarnings("unused")
	private int index;

	public InstructionRET(ByteArrayInputStream in)
	{
		index = in.read();
	}

	@Override
	public int length()
	{
		return 2;
	}
}
