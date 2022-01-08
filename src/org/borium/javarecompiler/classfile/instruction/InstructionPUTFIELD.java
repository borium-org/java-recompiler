package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Set field in object.
 */
public class InstructionPUTFIELD extends InstructionWithFieldIndex
{
	public InstructionPUTFIELD(ByteInputStream in, ConstantPool cp)
	{
		super(in, cp);
	}

	@Override
	public int getStackDepthChange()
	{
		return -2;
	}
}
