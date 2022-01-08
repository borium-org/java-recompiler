package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Access jump table by key match and jump.
 */
public class InstructionLOOKUPSWITCH extends Instruction
{
	/**
	 * Immediately after the lookupswitch opcode, between zero and three bytes must
	 * act as padding, such that defaultbyte1 begins at an address that is a
	 * multiple of four bytes from the start of the current method (the opcode of
	 * its first instruction).
	 */
	private int padding;

	/**
	 * Address of the default label. If default case is not present in the switch
	 * then the label is right past the switch block.
	 */
	private int defaultLabel;

	/** Match values for each case, in incrementing order. */
	private int[] match;

	/** Offsets for each case block. */
	private int[] offset;

	public InstructionLOOKUPSWITCH(ByteInputStream in)
	{
		int position = in.getPosition();
		while ((position & 3) != 0)
		{
			in.u1();
			padding++;
			position++;
		}
		defaultLabel = in.s4();
		int nPairs = in.u4();
		match = new int[nPairs];
		offset = new int[nPairs];
		for (int i = 0; i < nPairs; i++)
		{
			match[i] = in.s4();
			offset[i] = in.s4();
		}
	}

	@Override
	public void addLabel(int address, boolean[] labels)
	{
		for (int offs : offset)
		{
			labels[address + offs] = true;
		}
	}

	@Override
	public void detailedDump(IndentedOutputStream stream)
	{
		stream.iprintln("lookupswitch");
		stream.indent(1);
		stream.iprintln("padding: " + padding);
		stream.iprintln("cases: " + match.length);
		stream.iprint("default: L");
		stream.printHex(address + defaultLabel, 4);
		stream.println();

		for (int i = 0; i < match.length; i++)
		{
			stream.iprint("case " + match[i] + ": goto L");
			stream.printHex(address + offset[i], 4);
			stream.println();
		}
		stream.indent(-1);
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}

	@Override
	public int length()
	{
		return 1 + padding + 4 + 4 + match.length * 8;
	}
}
