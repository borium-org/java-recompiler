package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

public class InstructionINVOKEVIRTUAL extends Instruction
{
	/**
	 * The unsigned indexbyte1 and indexbyte2 are used to construct an index into
	 * the run-time constant pool of the current class (2.6), where the value of the
	 * index is (indexbyte1 << 8) | indexbyte2. The run-time constant pool entry at
	 * the index must be a symbolic reference to a method (5.1), which gives the
	 * name and descriptor (4.3.3) of the method as well as a symbolic reference to
	 * the class in which the method is to be found.
	 */
	@SuppressWarnings("unused")
	private int index;

	public InstructionINVOKEVIRTUAL(ByteInputStream in)
	{
		index = in.u2();
	}

	@Override
	public int length()
	{
		return 3;
	}
}
