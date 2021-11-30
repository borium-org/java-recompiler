package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Push short.
 */
public class InstructionSIPUSH extends Instruction
{
	/**
	 * The immediate unsigned byte1 and byte2 values are assembled into an
	 * intermediate short, where the value of the short is (byte1 << 8) | byte2. The
	 * intermediate value is then sign-extended to an int value. That value is
	 * pushed onto the operand stack.
	 */
	private int value;

	public InstructionSIPUSH(ByteArrayInputStream in)
	{
		value = in.read() << 8 | in.read();
		if (value >= 0x8000)
		{
			value -= 0x10000;
		}
	}

	@Override
	public int length()
	{
		return 3;
	}
}
