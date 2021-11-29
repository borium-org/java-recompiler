package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Branch always (wide index).
 */
public class InstructionGOTO_W extends Instruction
{
	/**
	 * The unsigned bytes branchbyte1, branchbyte2, branchbyte3, and branchbyte4 are
	 * used to construct a signed 32-bit branchoffset, where branchoffset is
	 * (branchbyte1 << 24) | (branchbyte2 << 16) | (branchbyte3 << 8) | branchbyte4.
	 * Execution proceeds at that offset from the address of the opcode of this
	 * goto_w instruction. The target address must be that of an opcode of an
	 * instruction within the method that contains this goto_w instruction.
	 */
	@SuppressWarnings("unused")
	private int offset;

	public InstructionGOTO_W(ByteArrayInputStream in)
	{
		offset = in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read();
	}

	@Override
	public int length()
	{
		return 5;
	}
}
