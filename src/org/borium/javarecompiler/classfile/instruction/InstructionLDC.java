package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Push item from run-time constant pool.
 */
public class InstructionLDC extends Instruction
{
	private int index;

	private Constant c;

	private String value;

	public InstructionLDC(ByteInputStream in, ConstantPool cp)
	{
		index = in.u1();
		c = cp.get(index);
		if (c instanceof ConstantStringInfo stringValue)
		{
			value = stringValue.getValue(cp);
		}
		else if (c instanceof ConstantClassInfo classValue)
		{
			value = cp.getString(classValue.nameIndex);
		}
	}

	@Override
	public void detailedDump(IndentedOutputStream stream, int address)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		if (c instanceof ConstantStringInfo stringValue)
		{
			stream.iprintln(className + " \"" + value + "\"");
		}
		else if (c instanceof ConstantInteger intValue)
		{
			stream.iprintln(className + " " + intValue.getValue());
		}
		else if (c instanceof ConstantClassInfo classValue)
		{
			stream.iprintln(className + " " + value);
		}
		else
		{
			stream.iprintln(className + " " + index);
			throw new RuntimeException(className + ": Dump not implemented");
		}
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}

	@Override
	public int length()
	{
		return 2;
	}
}
