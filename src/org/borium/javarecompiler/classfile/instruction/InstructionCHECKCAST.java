package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Check whether object is of given type.
 */
public class InstructionCHECKCAST extends Instruction
{
	/**
	 * The objectref must be of type reference. The unsigned indexbyte1 and
	 * indexbyte2 are used to construct an index into the run-time constant pool of
	 * the current class (2.6), where the value of the index is (indexbyte1 << 8) |
	 * indexbyte2. The run-time constant pool entry at the index must be a symbolic
	 * reference to a class, array, or interface type.
	 */
	@SuppressWarnings("unused")
	private int index;

	public InstructionCHECKCAST(ByteArrayInputStream in)
	{
		index = in.read() << 8 | in.read();
	}

	@Override
	public int length()
	{
		return 3;
	}
}
