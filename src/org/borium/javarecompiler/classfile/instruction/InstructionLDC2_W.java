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

	public InstructionLDC2_W(ByteInputStream in)
	{
		index = in.u2();
	}

	@Override
	public void detailedDump(IndentedOutputStream stream, int address, ConstantPool cp)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		Constant constant = cp.get(index);
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
	public int length()
	{
		return 3;
	}
}
