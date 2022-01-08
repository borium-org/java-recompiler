package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionIF_ICMPLT extends InstructionWithLabel
{
	public InstructionIF_ICMPLT(ByteInputStream in)
	{
		super(in);
	}

	@Override
	public int getStackDepthChange()
	{
		return -2;
	}
}
