package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Store reference into local variable.
 */
public class InstructionASTORE extends Instruction
{
	/**
	 * The index is an unsigned byte that must be an index into the local variable
	 * array of the current frame (2.6). The objectref on the top of the operand
	 * stack must be of type returnAddress or of type reference. It is popped from
	 * the operand stack, and the value of the local variable at index is set to
	 * objectref.
	 */
	private int index;

	/**
	 * False if index was part of instruction code, true if index was provided in
	 * separate byte.
	 */
	private boolean separateIndexConstant;

	public InstructionASTORE(ByteArrayInputStream in)
	{
		separateIndexConstant = true;
		index = in.read();
		if (index == -1)
		{
			throw new ClassFormatError("ASTORE index error");
		}
	}

	public InstructionASTORE(ByteArrayInputStream in, int index)
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
