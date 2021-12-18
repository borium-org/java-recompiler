package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Store reference into local variable.
 */
public class InstructionASTORE extends InstructionWithIndex
{
	public InstructionASTORE(ByteInputStream in, boolean wide)
	{
		super(in, wide);
	}

	public InstructionASTORE(int index)
	{
		super(index);
	}
}
