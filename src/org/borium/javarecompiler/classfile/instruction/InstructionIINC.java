package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Increment local variable by constant.
 */
public class InstructionIINC extends Instruction
{
	/**
	 * The index is an unsigned byte that must be an index into the local variable
	 * array of the current frame (2.6). The local variable at index must contain an
	 * int.
	 */
	@SuppressWarnings("unused")
	private int index;

	/**
	 * The const is an immediate signed byte. The value const is first sign-extended
	 * to an int, and then the local variable at index is incremented by that
	 * amount.
	 */
	private int constant;

	public InstructionIINC(ByteArrayInputStream in)
	{
		index = in.read();
		constant = in.read();
		if (constant >= 0x80)
		{
			constant -= 0x100;
		}
	}

	@Override
	public int length()
	{
		return 3;
	}
}
