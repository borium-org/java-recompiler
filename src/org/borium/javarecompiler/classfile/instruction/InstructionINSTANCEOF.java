package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

public class InstructionINSTANCEOF extends InstructionWithTypeIndex
{
	public InstructionINSTANCEOF(ByteInputStream in, ConstantPool cp)
	{
		super(in, cp);
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}
}
