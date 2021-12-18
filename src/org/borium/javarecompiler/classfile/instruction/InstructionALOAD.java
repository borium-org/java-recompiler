package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Load reference from local variable.
 */
public class InstructionALOAD extends InstructionWithIndex
{
	public InstructionALOAD(ByteInputStream in, boolean wide)
	{
		super(in, wide);
	}

	public InstructionALOAD(int index)
	{
		super(index);
	}
}
