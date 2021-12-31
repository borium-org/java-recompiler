package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionINVOKEDYNAMIC extends Instruction
{
	/**
	 * the unsigned indexbyte1 and indexbyte2 are used to construct an index into
	 * the run-time constant pool of the current class (2.6), where the value of the
	 * index is (indexbyte1 << 8) | indexbyte2. The run-time constant pool entry at
	 * the index must be a symbolic reference to a dynamically-computed call site
	 * (5.1).
	 */
	private int index;

	/**
	 * The values of the third and fourth operand bytes must always be zero.
	 */
	private int zero;

	public InstructionINVOKEDYNAMIC(ByteInputStream in)
	{
		index = in.u2();
		zero = in.u2();
		if (zero != 0)
		{
			throw new ClassFormatError("INVOKEDYNAMIC non-zero");
		}
	}

	@Override
	public void detailedDump(IndentedOutputStream stream, int address)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
//		Constant classRef = cp.get(index);
		stream.iprintln(className + " " + index);
		throw new RuntimeException(className + ": Dump not implemented");
	}

	@Override
	public int length()
	{
		return 5;
	}
}
