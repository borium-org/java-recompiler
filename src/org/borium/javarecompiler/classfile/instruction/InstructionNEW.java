package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Create new object.
 */
public class InstructionNEW extends InstructionWithTypeIndex
{
	public InstructionNEW(ByteInputStream in, ConstantPool cp)
	{
		super(in, cp);
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}
}
