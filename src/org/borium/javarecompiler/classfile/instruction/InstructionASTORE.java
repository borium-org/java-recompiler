package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

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
	@SuppressWarnings("unused")
	private int index;

	/**
	 * Index constant length, 0 for hard-coded index 0...3, 1 for generic one-byte
	 * index and 2 for wide 2-byte index.
	 */
	private int indexConstantLength;

	public InstructionASTORE(ByteInputStream in, boolean wide)
	{
		indexConstantLength = wide ? 2 : 1;
		index = wide ? in.u2() : in.u1();
	}

	public InstructionASTORE(int index)
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
