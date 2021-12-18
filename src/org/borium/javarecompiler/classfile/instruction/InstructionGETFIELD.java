package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Fetch field from object.
 */
public class InstructionGETFIELD extends InstructionWithField
{
	public InstructionGETFIELD(ByteInputStream in)
	{
		super(in);
	}
}
