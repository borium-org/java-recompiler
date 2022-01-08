package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionIFLT extends InstructionWithLabel
{
	public InstructionIFLT(ByteInputStream in)
	{
		super(in);
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
