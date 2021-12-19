package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Check whether object is of given type.
 */
public class InstructionCHECKCAST extends InstructionWithTypeIndex
{
	public InstructionCHECKCAST(ByteInputStream in)
	{
		super(in);
	}
}
