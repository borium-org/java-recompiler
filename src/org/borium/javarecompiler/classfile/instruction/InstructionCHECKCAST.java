package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Check whether object is of given type.
 */
public class InstructionCHECKCAST extends InstructionWithTypeIndex
{
	public InstructionCHECKCAST(ByteInputStream in, ConstantPool cp)
	{
		super(in, cp);
	}
}
