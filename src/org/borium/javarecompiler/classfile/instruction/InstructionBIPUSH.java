package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

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

	public InstructionBIPUSH(ByteArrayInputStream in)
	{
		value = in.read();
		if (value >= 0x80)
		{
			value = value - 0x100;
		}
	}
}
