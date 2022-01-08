package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionIFNONNULL extends InstructionWithLabel
{
	public InstructionIFNONNULL(ByteInputStream in)
	{
		super(in);
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
