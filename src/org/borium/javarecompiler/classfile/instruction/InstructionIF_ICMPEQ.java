package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionIF_ICMPEQ extends InstructionWithLabel
{
	public InstructionIF_ICMPEQ(ByteInputStream in)
	{
		super(in);
	}

	@Override
	public int getStackDepthChange()
	{
		return -2;
	}
}
