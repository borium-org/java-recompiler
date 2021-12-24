package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Push byte.
 */
public class InstructionBIPUSH extends Instruction
{
	/**
	 * The immediate byte is sign-extended to an int value. That value is pushed
	 * onto the operand stack.
	 */
	private int value;

	public InstructionBIPUSH(ByteInputStream in)
	{
		value = in.s1();
	}

	@Override
	public void detailedDump(IndentedOutputStream stream, int address, ConstantPool cp)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		stream.iprintln(className + " " + value);
	}

	@Override
	public int length()
	{
		return 2;
	}
}
