package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

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

	private boolean wide;

	public InstructionRET(ByteInputStream in, boolean wide)
	{
		this.wide = wide;
		index = wide ? in.u2() : in.u1();
	}

	@Override
	public int length()
	{
		return wide ? 3 : 2;
	}
}
