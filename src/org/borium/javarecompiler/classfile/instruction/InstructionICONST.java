package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

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
	public void detailedDump(IndentedOutputStream stream)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		stream.iprintln(className + " " + value);
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}
}
