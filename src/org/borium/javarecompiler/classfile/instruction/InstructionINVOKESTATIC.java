package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

public class InstructionINVOKESTATIC extends Instruction
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
	private int index;
	private ConstantMethodrefInfo methodref;

	private ConstantClassInfo classInfo;

	private ConstantNameAndTypeInfo nameType;

	private String methodClassName;

	private String methodName;

	public InstructionINVOKESTATIC(ByteInputStream in, ConstantPool cp)
	{
		index = in.u2();
		methodref = cp.get(index, ConstantMethodrefInfo.class);
		classInfo = cp.get(methodref.classIndex, ConstantClassInfo.class);
		nameType = cp.get(methodref.nameAndTypeIndex, ConstantNameAndTypeInfo.class);
		methodClassName = cp.getString(classInfo.nameIndex).replace('/', '.');
		methodName = cp.getString(nameType.nameIndex);
	}

	@Override
	public void detailedDump(IndentedOutputStream stream, int address)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		stream.iprintln(className + " " + methodClassName + "." + methodName);
	}

	@Override
	public int length()
	{
		return 3;
	}
}
