package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Create new object.
 */
public class InstructionNEW extends InstructionWithTypeIndex
{
	public InstructionNEW(ByteInputStream in)
	{
		super(in);
	}
}
