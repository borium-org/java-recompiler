package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Get static field from class.
 */
public class InstructionGETSTATIC extends InstructionWithFieldIndex
{
	public InstructionGETSTATIC(ByteInputStream in, ConstantPool cp)
	{
		super(in, cp);
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}
}
