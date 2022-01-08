package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionIF_ICMPGE extends InstructionWithLabel
{
	public InstructionIF_ICMPGE(ByteInputStream in)
	{
		super(in);
	}

	@Override
	public int getStackDepthChange()
	{
		return -2;
	}
}
