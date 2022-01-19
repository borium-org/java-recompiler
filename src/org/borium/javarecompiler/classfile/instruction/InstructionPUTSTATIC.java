package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Set static field in class.
 */
public class InstructionPUTSTATIC extends InstructionWithFieldIndex
{
	public InstructionPUTSTATIC(ByteInputStream in, ConstantPool cp)
	{
		super(in, cp);
	}

	@Override
	public int getStackDepthChange()
	{
		return -1;
	}
}
