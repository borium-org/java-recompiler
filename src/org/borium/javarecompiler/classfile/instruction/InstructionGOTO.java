package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Branch always.
 */
public class InstructionGOTO extends Instruction
{
	/**
	 * The unsigned bytes branchbyte1 and branchbyte2 are used to construct a signed
	 * 16-bit branchoffset, where branchoffset is (branchbyte1 << 8) | branchbyte2.
	 * Execution proceeds at that offset from the address of the opcode of this goto
	 * instruction. The target address must be that of an opcode of an instruction
	 * within the method that contains this goto instruction.
	 */
	@SuppressWarnings("unused")
	private int offset;

	public InstructionGOTO(ByteArrayInputStream in)
	{
		offset = in.read() << 8 | in.read();
		if (offset >= 0x8000)
		{
			offset -= 0x10000;
		}
	}

	@Override
	public int length()
	{
		return 3;
	}
}
