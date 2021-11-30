package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Jump subroutine.
 */
public class InstructionJSR extends Instruction
{
	/**
	 * The address of the opcode of the instruction immediately following this jsr
	 * instruction is pushed onto the operand stack as a value of type
	 * returnAddress. The unsigned branchbyte1 and branchbyte2 are used to construct
	 * a signed 16-bit offset, where the offset is (branchbyte1 << 8) | branchbyte2.
	 * Execution proceeds at that offset from the address of this jsr instruction.
	 */
	private int offset;

	public InstructionJSR(ByteArrayInputStream in)
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
