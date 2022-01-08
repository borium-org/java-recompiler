package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Jump subroutine.
 */
public class InstructionJSR extends InstructionWithLabel
{
	public InstructionJSR(ByteInputStream in)
	{
		super(in);
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}
}
