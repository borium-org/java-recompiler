package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Branch always.
 */
public class InstructionGOTO extends InstructionWithLabel
{
	public InstructionGOTO(ByteInputStream in)
	{
		super(in);
	}

	@Override
	public boolean fallsThrough()
	{
		return false;
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
