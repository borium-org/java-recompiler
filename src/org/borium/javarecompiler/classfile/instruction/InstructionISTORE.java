package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Store int into local variable.
 */
public class InstructionISTORE extends Instruction
{
	/**
	 * The index is an unsigned byte that must be an index into the local variable
	 * array of the current frame (2.6). The value on the top of the operand stack
	 * must be of type int. It is popped from the operand stack, and the value of
	 * the local variable at index is set to value.
	 */
	@SuppressWarnings("unused")
	private int index;

	/**
	 * Index constant length, 0 for hard-coded index 0...3, 1 for generic one-byte
	 * index and 2 for wide 2-byte index.
	 */
	private int indexConstantLength;

	public InstructionISTORE(ByteInputStream in, boolean wide)
	{
		indexConstantLength = wide ? 2 : 1;
		index = wide ? in.u2() : in.u1();
	}

	public InstructionISTORE(int index)
	{
		indexConstantLength = 0;
		this.index = index;
	}

	@Override
	public int length()
	{
		return 1 + indexConstantLength;
	}
}
