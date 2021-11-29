package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

public class InstructionDLOAD extends Instruction
{
	/**
	 * The index is an unsigned byte. Both index and index+1 must be indices into
	 * the local variable array of the current frame (2.6). The local variable at
	 * index must contain a double. The value of the local variable at index is
	 * pushed onto the operand stack.
	 */
	private int index;

	/**
	 * False if index was part of instruction code, true if index was provided in
	 * separate byte.
	 */
	private boolean separateIndexConstant;

	public InstructionDLOAD(ByteArrayInputStream in)
	{
		separateIndexConstant = true;
		index = in.read();
		if (index == -1)
		{
			throw new ClassFormatError("DLOAD index error");
		}
	}

	public InstructionDLOAD(ByteArrayInputStream in, int index)
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
