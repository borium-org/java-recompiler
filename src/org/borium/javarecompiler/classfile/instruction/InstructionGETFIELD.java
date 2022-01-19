package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Fetch field from object.
 */
public class InstructionGETFIELD extends InstructionWithFieldIndex
{
	public InstructionGETFIELD(ByteInputStream in, ConstantPool cp)
	{
		super(in, cp);
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
