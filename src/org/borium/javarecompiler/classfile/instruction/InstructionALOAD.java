package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

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
	@SuppressWarnings("unused")
	private int index;

	/**
	 * Index constant length, 0 for hard-coded index 0...3, 1 for generic one-byte
	 * index and 2 for wide 2-byte index.
	 */
	private int indexConstantLength;

	public InstructionALOAD(ByteInputStream in, boolean wide)
	{
		indexConstantLength = wide ? 2 : 1;
		index = wide ? in.u2() : in.u1();
	}

	public InstructionALOAD(int index)
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
