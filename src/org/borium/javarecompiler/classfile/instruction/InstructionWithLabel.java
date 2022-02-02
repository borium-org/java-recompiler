package org.borium.javarecompiler.classfile.instruction;

import static org.borium.javarecompiler.Statics.*;

import org.borium.javarecompiler.classfile.*;

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
	public void detailedDump(IndentedOutputStream stream)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		stream.iprint(className + " L");
		stream.printHex(address + offset, 4);
		stream.println();
	}

	public String getLabel()
	{
		return "L" + hexString(address + offset, 4);
	}

	@Override
	public int length()
	{
		return 3;
	}
}
