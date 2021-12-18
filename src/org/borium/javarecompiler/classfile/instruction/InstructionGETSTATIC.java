package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Get static field from class.
 */
public class InstructionGETSTATIC extends InstructionWithFieldIndex
{
	public InstructionGETSTATIC(ByteInputStream in)
	{
		super(in);
	}
}
