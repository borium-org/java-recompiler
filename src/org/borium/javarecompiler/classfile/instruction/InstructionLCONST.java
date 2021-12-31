package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Push long constant.
 */
public class InstructionLCONST extends Instruction
{
	private long value;

	public InstructionLCONST(long value)
	{
		this.value = value;
	}

	@Override
	public void detailedDump(IndentedOutputStream stream, int address)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		stream.iprintln(className + " " + value);
	}
}
