package org.borium.javarecompiler.classfile.instruction;

import static org.borium.javarecompiler.Statics.*;

import org.borium.javarecompiler.classfile.*;

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
	private int offset;

	public InstructionGOTO_W(ByteInputStream in)
	{
		offset = in.s4();
	}

	@Override
	public void addLabel(int address, boolean[] labels)
	{
		labels[address + offset] = true;
	}

	@Override
	public void detailedDump(IndentedOutputStream stream)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		stream.iprintln(className + " L" + (address + offset));
	}

	@Override
	public boolean fallsThrough()
	{
		return false;
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}

	@Override
	public int getTargetAddress(int index)
	{
		Assert(index == 0, "InstructionGOTO_W: Bad target index");
		return address + offset;
	}

	@Override
	public int getTargetCount()
	{
		return 1;
	}

	@Override
	public int length()
	{
		return 5;
	}
}
