package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

public class InstructionLDC_W extends Instruction
{
	/**
	 * The unsigned indexbyte1 and indexbyte2 are assembled into an unsigned 16-bit
	 * index into the run-time constant pool of the current class (2.5.5), where the
	 * value of the index is calculated as (indexbyte1 << 8) | indexbyte2. The index
	 * must be a valid index into the run-time constant pool of the current class.
	 */
	private int index;

	private Constant c;

	private String value;

	public InstructionLDC_W(ByteInputStream in, ConstantPool cp)
	{
		index = in.u2();
		c = cp.get(index);
		if (c instanceof ConstantStringInfo stringValue)
		{
			value = stringValue.getValue(cp);
		}
		else if (c instanceof ConstantClassInfo classInfo)
		{
			value = classInfo.getName();
		}
	}

	@Override
	public void detailedDump(IndentedOutputStream stream)
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
		else if (c instanceof ConstantClassInfo classInfo)
		{
			stream.iprintln(className + " " + value + ".class");
		}
		else
		{
//		Constant classRef = cp.get(index);
			stream.iprintln(className + " " + index);
			throw new RuntimeException(className + ": Dump not implemented");
		}
	}

	public Constant getConstant()
	{
		return c;
	}

	@Override
	public int getStackDepthChange()
	{
		return 1;
	}

	@Override
	public int length()
	{
		return 3;
	}
}
