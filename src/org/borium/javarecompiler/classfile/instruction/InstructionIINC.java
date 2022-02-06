package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Increment local variable by constant.
 */
public class InstructionIINC extends Instruction
{
	/**
	 * The index is an unsigned byte that must be an index into the local variable
	 * array of the current frame (2.6). The local variable at index must contain an
	 * int.
	 */
	private int index;

	/**
	 * The const is an immediate signed byte. The value const is first sign-extended
	 * to an int, and then the local variable at index is incremented by that
	 * amount.
	 */
	private int constant;

	private boolean wide;

	public InstructionIINC(ByteInputStream in, boolean wide)
	{
		this.wide = wide;
		index = wide ? in.u2() : in.u1();
		constant = wide ? in.s2() : in.s1();
	}

	@Override
	public void detailedDump(IndentedOutputStream stream)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		stream.iprintln(className + " " + index + " " + constant);
	}

	public int getIndex()
	{
		return index;
	}

	@Override
	public int getStackDepthChange()
	{
		return 0;
	}

	public int getValue()
	{
		return constant;
	}

	@Override
	public int length()
	{
		return wide ? 5 : 3;
	}
}
