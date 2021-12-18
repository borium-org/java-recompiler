package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

public class InstructionINVOKEINTERFACE extends Instruction
{
	/**
	 * The unsigned indexbyte1 and indexbyte2 are used to construct an index into
	 * the run-time constant pool of the current class (2.6), where the value of the
	 * index is (indexbyte1 << 8) | indexbyte2. The run-time constant pool entry at
	 * the index must be a symbolic reference to an interface method (5.1), which
	 * gives the name and descriptor (4.3.3) of the interface method as well as a
	 * symbolic reference to the interface in which the interface method is to be
	 * found.
	 */
	private int index;

	/**
	 * The count operand is an unsigned byte that must not be zero.
	 */
	private int count;
	/**
	 * The value of the fourth operand byte must always be zero.
	 */
	private int zero;

	public InstructionINVOKEINTERFACE(ByteInputStream in)
	{
		index = in.u2();
		count = in.u1();
		if (count == 0)
		{
			throw new ClassFormatError("INVOKEINTERFACE count-zero");
		}
		zero = in.u1();
		if (zero != 0)
		{
			throw new ClassFormatError("INVOKEINTERFACE non-zero");
		}
	}

	@Override
	public void detailedDump(IndentedOutputStream stream, int address, ConstantPool cp)
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
