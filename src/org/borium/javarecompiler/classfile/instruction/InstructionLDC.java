package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Push item from run-time constant pool.
 */
public class InstructionLDC extends Instruction
{
	@SuppressWarnings("unused")
	private int index;

	public InstructionLDC(ByteInputStream in)
	{
		index = in.u1();
	}

	@Override
	public int length()
	{
		return 2;
	}
}
