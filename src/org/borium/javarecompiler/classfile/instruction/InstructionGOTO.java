package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

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

	public InstructionGOTO(ByteInputStream in)
	{
		offset = in.s2();
	}

	@Override
	public int length()
	{
		return 3;
	}
}
