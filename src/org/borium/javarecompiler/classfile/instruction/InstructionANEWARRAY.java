package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Create new array of reference.
 */
public class InstructionANEWARRAY extends InstructionWithTypeIndex
{
	public InstructionANEWARRAY(ByteInputStream in)
	{
		super(in);
	}
}
