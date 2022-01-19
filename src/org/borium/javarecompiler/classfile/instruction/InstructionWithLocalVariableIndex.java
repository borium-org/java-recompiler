package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

abstract class InstructionWithLocalVariableIndex extends Instruction
{
	/**
	 * The index is an unsigned byte that must be an index into the local variable
	 * array of the current frame (2.6). The local variable at index must contain a
	 * reference. The objectref in the local variable at index is pushed onto the
	 * operand stack.
	 */
	protected int index;

	/**
	 * Index constant length, 0 for hard-coded index 0...3, 1 for generic one-byte
	 * index and 2 for wide 2-byte index.
	 */
	private int indexConstantLength;

	public InstructionWithLocalVariableIndex(ByteInputStream in, boolean wide)
	{
		indexConstantLength = wide ? 2 : 1;
		index = wide ? in.u2() : in.u1();
	}

	public InstructionWithLocalVariableIndex(int index)
	{
		indexConstantLength = 0;
		this.index = index;
	}

	@Override
	public void detailedDump(IndentedOutputStream stream)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		stream.iprintln(className + " " + index);
	}

	@Override
	public int length()
	{
		return 1 + indexConstantLength;
	}
}
