package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Push long or double from run-time constant pool (wide index).
 */
public class InstructionLDC2_W extends Instruction
{
	/**
	 * The unsigned indexbyte1 and indexbyte2 are assembled into an unsigned 16-bit
	 * index into the run-time constant pool of the current class (2.5.5), where the
	 * value of the index is calculated as (indexbyte1 << 8) | indexbyte2. The index
	 * must be a valid index into the run-time constant pool of the current class.
	 */
	private int index;

	private Constant constant;

	public InstructionLDC2_W(ByteInputStream in, ConstantPool cp)
	{
		index = in.u2();
		constant = cp.get(index);
	}

	@Override
	public void detailedDump(IndentedOutputStream stream)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		if (constant instanceof ConstantLong constantLong)
		{
			stream.iprintln(className + " " + index + " " + constantLong.value);
		}
		else if (constant instanceof ConstantDouble constantDouble)
		{
			stream.iprintln(className + " " + index + " " + constantDouble.value);
		}
		else
		{
			stream.iprintln(className + " " + index + " " + constant.getClass().getSimpleName());
		}
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}

	@Override
	public int length()
	{
		return 3;
	}
}
