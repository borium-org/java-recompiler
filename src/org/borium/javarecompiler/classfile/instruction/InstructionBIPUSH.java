package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Push byte.
 */
public class InstructionBIPUSH extends Instruction
{
	/**
	 * The immediate byte is sign-extended to an int value. That value is pushed
	 * onto the operand stack.
	 */
	@SuppressWarnings("unused")
	private int value;

	public InstructionBIPUSH(ByteInputStream in)
	{
		value = in.s1();
	}
}
