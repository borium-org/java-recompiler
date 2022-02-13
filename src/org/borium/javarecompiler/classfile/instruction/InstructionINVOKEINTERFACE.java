package org.borium.javarecompiler.classfile.instruction;

import static org.borium.javarecompiler.Statics.*;

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

	private ConstantClassInfo classInfo;

	private String methodClassName;

	private String methodName;

	public InstructionINVOKEINTERFACE(ByteInputStream in, ConstantPool cp)
	{
		index = in.u2();
		methodref = cp.get(index, ConstantInterfaceMethodrefInfo.class);
		classInfo = cp.get(methodref.classIndex, ConstantClassInfo.class);
		nameType = cp.get(methodref.nameAndTypeIndex, ConstantNameAndTypeInfo.class);
		count = in.u1();
		Assert(count != 0, "INVOKEINTERFACE: Count is zero");
		zero = in.u1();
		Assert(zero == 0, "INVOKEINTERFACE: Zero is not zero");
		methodClassName = cp.getString(classInfo.nameIndex).replace('/', '.');
		methodName = nameType.getName();
	}

	@Override
	public void detailedDump(IndentedOutputStream stream)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		stream.iprintln(className + " " + index + " " + nameType.getName() + " count " + count);
	}

	public int getCount()
	{
		return count;
	}

	public String getMethodClassName()
	{
		return methodClassName;
	}

	public String getmethodDescriptor()
	{
		return nameType.getDescriptor();
	}

	public String getMethodName()
	{
		return methodName;
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
