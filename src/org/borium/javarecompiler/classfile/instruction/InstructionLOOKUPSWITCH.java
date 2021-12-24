package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Access jump table by key match and jump.
 */
public class InstructionLOOKUPSWITCH extends Instruction
{
	/**
	 * n. Immediately after the lookupswitch opcode, between zero and three bytes
	 * must act as padding, such that defaultbyte1 begins at an address that is a
	 * multiple of four bytes from the start of the current method (the opcode of
	 * its first instruction).
	 */
	@SuppressWarnings("unused")
	private int padding;

	/**
	 * Address of the default label. If default case is not present in the switch
	 * then the label is right past the switch block.
	 */
	@SuppressWarnings("unused")
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
			labels[offs] = true;
		}
	}

	@Override
	public void detailedDump(IndentedOutputStream stream, int address, ConstantPool cp)
	{
		stream.iprintln("lookupswitch");
		stream.indent(1);
		stream.iprintln("cases: " + match.length);
		stream.iprintln("default: " + defaultLabel);
		for (int i = 0; i < match.length; i++)
		{
			stream.iprintln("case " + match[i] + ": goto L" + (address + offset[i]));
		}
		stream.indent(-1);
	}

	@Override
	public int length()
	{
		return 1 + padding + 4 + 4 + match.length * 8;
	}
}
