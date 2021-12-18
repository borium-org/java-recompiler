package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

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
	public void detailedDump(IndentedOutputStream stream, int address, ConstantPool cp)
	{
		// TODO Auto-generated method stub
		throw new RuntimeException("TABLESWITCH not yet supported");
	}
}
