package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionINSTANCEOF extends Instruction
{
	/**
	 * The unsigned indexbyte1 and indexbyte2 are used to construct an index into
	 * the run-time constant pool of the current class (2.6), where the value of the
	 * index is (indexbyte1 << 8) | indexbyte2. The run-time constant pool entry at
	 * the index must be a symbolic reference to a class, array, or interface type.
	 */
	@SuppressWarnings("unused")
	private int index;

	public InstructionINSTANCEOF(ByteInputStream in)
	{
		index = in.u2();
	}

	@Override
	public int length()
	{
		return 3;
	}
}
