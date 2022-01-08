package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionIF_ICMPNE extends InstructionWithLabel
{
	public InstructionIF_ICMPNE(ByteInputStream in)
	{
		super(in);
	}

	@Override
	public int getStackDepthChange()
	{
		return -2;
	}
}
