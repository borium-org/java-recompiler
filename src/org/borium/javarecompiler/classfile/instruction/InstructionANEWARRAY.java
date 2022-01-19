package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Create new array of reference.
 */
public class InstructionANEWARRAY extends InstructionWithTypeIndex
{
	public InstructionANEWARRAY(ByteInputStream in, ConstantPool cp)
	{
		super(in, cp);
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
