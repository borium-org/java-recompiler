package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Access jump table by key match and jump.
 */
public class InstructionLOOKUPSWITCH extends Instruction
{
	public InstructionLOOKUPSWITCH(ByteInputStream in)
	{
		// TODO Auto-generated constructor stub
		throw new RuntimeException("LOOKUPSWITCH not yet supported");
	}

	@Override
	public void addLabel(int address, boolean[] labels)
	{
//		labels[address+offset]=true;
		throw new RuntimeException("LOOKUPSWITCH not yet supported");
	}
}
