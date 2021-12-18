package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Access jump table by index and jump.
 */
public class InstructionTABLESWITCH extends Instruction
{
	public InstructionTABLESWITCH(ByteInputStream in)
	{
		// TODO Auto-generated constructor stub
		throw new RuntimeException("TABLESWITCH not yet supported");
	}

	@Override
	public void addLabel(int address, boolean[] labels)
	{
//		labels[address+offset]=true;
		throw new RuntimeException("TABLESWITCH not yet supported");
	}

	@Override
	public void detailedDump(IndentedOutputStream stream, int address)
	{
		// TODO Auto-generated method stub
		throw new RuntimeException("TABLESWITCH not yet supported");
	}
}
