package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Push item from run-time constant pool.
 */
public class InstructionLDC extends Instruction
{
	private int index;

	public InstructionLDC(ByteInputStream in)
	{
		index = in.u1();
	}

	@Override
	public void detailedDump(IndentedOutputStream stream, int address, ConstantPool cp)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		Constant c = cp.get(index);
		if (c instanceof ConstantStringInfo stringValue)
		{
			stream.iprintln(className + " \"" + stringValue.getValue(cp) + "\"");

		}
		else if (c instanceof ConstantInteger intValue)
		{
			stream.iprintln(className + " " + intValue.getValue());
		}
		else
		{
			stream.iprintln(className + " " + index);
			throw new RuntimeException(className + ": Dump not implemented");
		}
	}

	@Override
	public int length()
	{
		return 2;
	}
}
