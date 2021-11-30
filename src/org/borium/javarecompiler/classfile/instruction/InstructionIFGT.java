package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionIFGT extends Instruction
{
	/**
	 * If the comparison succeeds, the unsigned branchbyte1 and branchbyte2 are used
	 * to construct a signed 16-bit offset, where the offset is calculated to be
	 * (branchbyte1 << 8) | branchbyte2. Execution then proceeds at that offset from
	 * the address of the opcode of this if_acmp&lt;cond&gt; instruction. The target
	 * address must be that of an opcode of an instruction within the method that
	 * contains this if_acmp&lt;cond&gt; instruction.
	 */
	@SuppressWarnings("unused")
	private int offset;

	public InstructionIFGT(ByteInputStream in)
	{
		offset = in.s2();
	}

	@Override
	public int length()
	{
		return 3;
	}
}
