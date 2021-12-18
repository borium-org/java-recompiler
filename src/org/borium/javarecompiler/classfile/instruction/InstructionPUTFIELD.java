package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Set field in object.
 */
public class InstructionPUTFIELD extends InstructionWithField
{
	public InstructionPUTFIELD(ByteInputStream in)
	{
		super(in);
	}
}
