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

	private ConstantInterfaceMethodrefInfo methodref;

	private ConstantNameAndTypeInfo nameType;

	public InstructionINVOKEINTERFACE(ByteInputStream in, ConstantPool cp)
	{
		index = in.u2();
		methodref = cp.get(index, ConstantInterfaceMethodrefInfo.class);
		nameType = cp.get(methodref.nameAndTypeIndex, ConstantNameAndTypeInfo.class);
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
	public void detailedDump(IndentedOutputStream stream, int address)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
//		Constant classRef = cp.get(index);
		stream.iprintln(className + " " + index + " count " + count);
	}

	@Override
	public int getStackDepthChange()
	{
		int stackDepthChange = 0;
		stackDepthChange--; // INVOKEINTERFACE has 'this' pointer
		stackDepthChange -= nameType.getParameterCount(); // parameters, if any, are used and removed
		stackDepthChange += nameType.getReturnTypeCount(); // void (0) or anything else (1)
		return stackDepthChange;
	}

	@Override
	public int length()
	{
		return 5;
	}
}
