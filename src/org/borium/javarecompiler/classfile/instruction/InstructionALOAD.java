package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Load reference from local variable.
 */
public class InstructionALOAD extends Instruction
{
	/**
	 * The index is an unsigned byte that must be an index into the local variable
	 * array of the current frame (2.6). The local variable at index must contain a
	 * reference. The objectref in the local variable at index is pushed onto the
	 * operand stack.
	 */
	private int index;

	/**
	 * False if index was part of instruction code, true if index was provided in
	 * separate byte.
	 */
	private boolean separateIndexConstant;

	public InstructionALOAD(ByteArrayInputStream in)
	{
		separateIndexConstant = true;
		index = in.read();
		if (index == -1)
		{
			throw new ClassFormatError("ALOAD index error");
		}
	}

	public InstructionALOAD(ByteArrayInputStream in, int index)
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
