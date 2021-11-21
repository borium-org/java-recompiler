package org.borium.javarecompiler.classfile.constants;

import java.io.*;

import org.borium.javarecompiler.classfile.*;

public abstract class Constant
{
	protected static final int CONSTANT_Utf8 = 1;
	protected static final int CONSTANT_Integer = 3;
	protected static final int CONSTANT_Float = 4;
	protected static final int CONSTANT_Long = 5;
	protected static final int CONSTANT_Double = 6;
	protected static final int CONSTANT_Class = 7;
	protected static final int CONSTANT_String = 8;
	protected static final int CONSTANT_Fieldref = 9;
	protected static final int CONSTANT_Methodref = 10;
	protected static final int CONSTANT_InterfaceMethodref = 11;
	protected static final int CONSTANT_NameAndType = 12;
	protected static final int CONSTANT_MethodHandle = 15;
	protected static final int CONSTANT_MethodType = 16;
	protected static final int CONSTANT_Dynamic = 17;
	protected static final int CONSTANT_InvokeDynamic = 18;
	protected static final int CONSTANT_Module = 19;
	protected static final int CONSTANT_Package = 20;

	/**
	 * Create a Constant-derived object according to the tag from class input
	 * stream.
	 *
	 * @param tag Constant tag from section 4.4.
	 * @return Constant-derived object for the tagged constant.
	 */
	public static Constant create(int tag)
	{
		switch (tag)
		{
		case CONSTANT_Utf8:
			return new ConstantUtf8Info();
		case CONSTANT_Integer:
			return new ConstantInteger();
		case CONSTANT_Float:
			return new ConstantFloat();
//		case CONSTANT_Long:
//		case CONSTANT_Double:
		case CONSTANT_Class:
			return new ConstantClassInfo();
		case CONSTANT_String:
			return new ConstantStringInfo();
		case CONSTANT_Fieldref:
			return new ConstantFieldrefInfo();
		case CONSTANT_Methodref:
			return new ConstantMethodrefInfo();
		case CONSTANT_InterfaceMethodref:
			return new ConstantInterfaceMethodrefInfo();
		case CONSTANT_NameAndType:
			return new ConstantNameAndTypeInfo();
//		case CONSTANT_MethodHandle:
//		case CONSTANT_MethodType:
//		case CONSTANT_Dynamic:
//		case CONSTANT_InvokeDynamic:
//		case CONSTANT_Module:
//		case CONSTANT_Package:
		}
		throw new ClassFormatError("Bad constant type " + tag);
	}

	/**
	 * One of CONSTANT_* values.
	 */
	protected int tag;

	public boolean is(int constantType)
	{
		return constantType == tag;
	}

	/**
	 * Read a constant object from the class input stream.
	 *
	 * @param in The class input stream.
	 * @throws IOException I/O Error.
	 */
	protected abstract void read(ClassInputStream in) throws IOException;

	/**
	 * Verify if the constant is valid, considering the entire constant pool if
	 * necessary.
	 *
	 * @param majorVersion Major version beginning with which the constant is to be
	 *                     used.
	 * @param minorVersion Minor version beginning with which the constant is to be
	 *                     used.
	 * @param cp           Constant pool in which the constant is located. In many
	 *                     cases a field in the constant refers to another constant
	 *                     in the constant pool, so verification code needs to
	 *                     access any other constant.
	 * @param index        Index of this constant in the constant pool. Long and
	 *                     double constants occupy two slots in the constant pool,
	 *                     so in their case code also verifies that the next
	 *                     constant is unused.
	 * @return true if constant was verified successfully.
	 */
	protected abstract boolean verify(int majorVersion, int minorVersion, ConstantPool cp, int index);
}
