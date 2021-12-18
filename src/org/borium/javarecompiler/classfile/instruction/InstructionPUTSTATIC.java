package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Set static field in class.
 */
public class InstructionPUTSTATIC extends InstructionWithFieldIndex
{
	public InstructionPUTSTATIC(ByteInputStream in)
	{
		super(in);
	}
}
