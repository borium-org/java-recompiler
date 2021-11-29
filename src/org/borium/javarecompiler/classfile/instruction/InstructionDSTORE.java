package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

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
	private int index;
	/**
	 * False if index was part of instruction code, true if index was provided in
	 * separate byte.
	 */
	private boolean separateIndexConstant;

	public InstructionDSTORE(ByteArrayInputStream in)
	{
		separateIndexConstant = true;
		index = in.read();
		if (index == -1)
		{
			throw new ClassFormatError("DSTORE index error");
		}
	}

	public InstructionDSTORE(ByteArrayInputStream in, int index)
	{
		separateIndexConstant = false;
		this.index = index;
	}

	@Override
	public int length()
	{
		return separateIndexConstant ? 2 : 1;
	}
}
