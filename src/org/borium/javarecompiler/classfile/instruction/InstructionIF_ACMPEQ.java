package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionIF_ACMPEQ extends InstructionWithLabel
{
	public InstructionIF_ACMPEQ(ByteInputStream in)
	{
		super(in);
	}

	@Override
	public int getStackDepthChange()
	{
		return -2;
	}
}
