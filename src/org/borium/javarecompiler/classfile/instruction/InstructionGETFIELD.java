package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Fetch field from object.
 */
public class InstructionGETFIELD extends InstructionWithFieldIndex
{
	public InstructionGETFIELD(ByteInputStream in)
	{
		super(in);
	}
}
