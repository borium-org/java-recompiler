package org.borium.javarecompiler.classfile.attribute;

import java.io.*;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.instruction.*;

public class AttributeCode extends ClassAttribute
{
	private Instruction[] instructions;

	public AttributeCode(ClassAttribute attribute)
	{
		super(attribute);
		decode();
	}

	private void decode()
	{
		instructions = new Instruction[info.length];
		ByteArrayInputStream in = new ByteArrayInputStream(info);
		int index = 0;
		while (in.available() > 0)
		{
			instructions[index] = Instruction.read(in);
			index += instructions[index].length();
		}
	}
}
