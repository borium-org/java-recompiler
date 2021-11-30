package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Store long into local variable.
 */
public class InstructionLSTORE extends Instruction
{
	/**
	 * The index is an unsigned byte. Both index and index+1 must be indices into
	 * the local variable array of the current frame (2.6). The value on the top of
	 * the operand stack must be of type long. It is popped from the operand stack,
	 * and the local variables at index and index+1 are set to value.
	 * 
	 */
	private int index;

	/**
	 * False if index was part of instruction code, true if index was provided in
	 * separate byte.
	 */
	private boolean separateIndexConstant;

	public InstructionLSTORE(ByteArrayInputStream in)
	{
		separateIndexConstant = true;
		index = in.read();
		if (index == -1)
		{
			throw new ClassFormatError("ALOAD index error");
		}
	}

	public InstructionLSTORE(ByteArrayInputStream in, int index)
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
