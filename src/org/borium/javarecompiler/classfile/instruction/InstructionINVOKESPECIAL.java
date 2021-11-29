package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

public class InstructionINVOKESPECIAL extends Instruction
{
	/**
	 * The unsigned indexbyte1 and indexbyte2 are used to construct an index into
	 * the run-time constant pool of the current class (2.6), where the value of the
	 * index is (indexbyte1 << 8) | indexbyte2. The run-time constant pool entry at
	 * the index must be a symbolic reference to a method or an interface method
	 * (5.1), which gives the name and descriptor (4.3.3) of the method or interface
	 * method as well as a symbolic reference to the class or interface in which the
	 * method or interface method is to be found.
	 */
	@SuppressWarnings("unused")
	private int index;

	public InstructionINVOKESPECIAL(ByteArrayInputStream in)
	{
		index = in.read() << 8 | in.read();
	}

	@Override
	public int length()
	{
		return 3;
	}
}
