package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Store double into local variable.
 */
public class InstructionDSTORE extends Instruction
{
	/**
	 * The index is an unsigned byte. Both index and index+1 must be indices into
	 * the local variable array of the current frame (2.6). The value on the top of
	 * the operand stack must be of type double. It is popped from the operand
	 * stack. The local variables at index and index+1 are set to value.
	 */
	@SuppressWarnings("unused")
	private int index;

	/**
	 * Index constant length, 0 for hard-coded index 0...3, 1 for generic one-byte
	 * index and 2 for wide 2-byte index.
	 */
	private int indexConstantLength;

	public InstructionDSTORE(ByteInputStream in, boolean wide)
	{
		indexConstantLength = wide ? 2 : 1;
		index = wide ? in.u2() : in.u1();
	}

	public InstructionDSTORE(int index)
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
