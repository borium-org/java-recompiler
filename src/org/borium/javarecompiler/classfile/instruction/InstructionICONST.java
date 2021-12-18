package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Push int constant.
 */
public class InstructionICONST extends Instruction
{
	private int value;

	public InstructionICONST(int value)
	{
		this.value = value;
	}

	@Override
	public void detailedDump(IndentedOutputStream stream, int address, ConstantPool cp)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		stream.iprintln(className + " " + value);
	}
}
