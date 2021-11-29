package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

public class InstructionIF_ACMPNE extends Instruction
{
	/**
	 * If the comparison succeeds, the unsigned branchbyte1 and branchbyte2 are used
	 * to construct a signed 16-bit offset, where the offset is calculated to be
	 * (branchbyte1 << 8) | branchbyte2. Execution then proceeds at that offset from
	 * the address of the opcode of this if_acmp&lt;cond&gt; instruction. The target
	 * address must be that of an opcode of an instruction within the method that
	 * contains this if_acmp&lt;cond&gt; instruction.
	 */
	private int offset;

	public InstructionIF_ACMPNE(ByteArrayInputStream in)
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
