package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Base class for instructions that branch to a single target label. All
 * instructions are expected to be 3 bytes long unless overridden with other
 * length.
 */
abstract class InstructionWithLabel extends Instruction
{
	/**
	 * If the comparison succeeds, the unsigned branchbyte1 and branchbyte2 are used
	 * to construct a signed 16-bit offset, where the offset is calculated to be
	 * (branchbyte1 << 8) | branchbyte2. Execution then proceeds at that offset from
	 * the address of the opcode of this if_acmp&lt;cond&gt; instruction. The target
	 * address must be that of an opcode of an instruction within the method that
	 * contains this if_acmp&lt;cond&gt; instruction.
	 */
	protected int offset;

	protected InstructionWithLabel(ByteInputStream in)
	{
		offset = in.s2();
	}

	@Override
	public void addLabel(int address, boolean[] labels)
	{
		labels[address + offset] = true;
	}

	@Override
	public void detailedDump(IndentedOutputStream stream, int address, ConstantPool cp)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		stream.iprintln(className + " L" + (address + offset));
	}

	@Override
	public int length()
	{
		return 3;
	}
}
