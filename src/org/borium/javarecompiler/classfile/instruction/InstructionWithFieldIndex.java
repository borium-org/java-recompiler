package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

abstract class InstructionWithFieldIndex extends Instruction
{
	/**
	 * The unsigned indexbyte1 and indexbyte2 are used to construct an index into
	 * the run-time constant pool of the current class (2.6), where the value of the
	 * index is (indexbyte1 << 8) | indexbyte2.
	 */
	private int index;

	public InstructionWithFieldIndex(ByteInputStream in)
	{
		index = in.u2();
	}

	@Override
	public void detailedDump(IndentedOutputStream stream, int address, ConstantPool cp)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		ConstantFieldrefInfo fieldref = cp.get(index, ConstantFieldrefInfo.class);
		ConstantNameAndTypeInfo nameType = cp.get(fieldref.nameAndTypeIndex, ConstantNameAndTypeInfo.class);
		stream.iprintln(className + " " + index + " " + cp.getString(nameType.nameIndex));
	}

	@Override
	public int length()
	{
		return 3;
	}
}
