package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Jump subroutine (wide index).
 */
public class InstructionJSR_W extends Instruction
{
	/**
	 * The address of the opcode of the instruction immediately following this jsr_w
	 * instruction is pushed onto the operand stack as a value of type
	 * returnAddress. The unsigned branchbyte1, branchbyte2, branchbyte3, and
	 * branchbyte4 are used to construct a signed 32-bit offset, where the offset is
	 * (branchbyte1 << 24) | (branchbyte2 << 16) | (branchbyte3 << 8) | branchbyte4.
	 * Execution proceeds at that offset from the address of this jsr_w instruction.
	 * The target address must be that of an opcode of an instruction within the
	 * method that contains this jsr_w instruction.
	 */
	@SuppressWarnings("unused")
	private int offset;

	public InstructionJSR_W(ByteArrayInputStream in)
	{
		offset = in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read();
	}

	@Override
	public int length()
	{
		return 5;
	}
}
