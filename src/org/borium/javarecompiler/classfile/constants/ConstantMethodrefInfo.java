package org.borium.javarecompiler.classfile.constants;

import org.borium.javarecompiler.classfile.*;

/**
 * Fields, methods, and interface methods are represented by similar structures:
 *
 * <pre>
	CONSTANT_Methodref_info
	{
		u1 tag;
		u2 class_index;
		u2 name_and_type_index;
	}
 * </pre>
 *
 * The tag item of a CONSTANT_Methodref_info structure has the value
 * CONSTANT_Methodref (10).
 */
public class ConstantMethodrefInfo extends Constant
{
	/**
	 * The value of the class_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_Class_info structure (4.4.1) representing a class or interface type
	 * that has the method as a member.
	 * <p>
	 * In a CONSTANT_Methodref_info structure, the class_index item must be a class
	 * type, not an interface type.
	 */
	private int classIndex;

	/**
	 * The value of the name_and_type_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_NameAndType_info structure (4.4.6). This constant_pool entry
	 * indicates the name and descriptor of the method.
	 * <p>
	 * In a CONSTANT_Methodref_info structure, the indicated descriptor must be a
	 * method descriptor (4.3.3).
	 * <p>
	 * If the name of the method in a CONSTANT_Methodref_info structure begins with
	 * a '<' ('\u003c'), then the name must be the special name <init>, representing
	 * an instance initialization method (2.9.1). The return type of such a method
	 * must be void.
	 */
	private int nameAndTypeIndex;

	@Override
	protected void dump(IndentedOutputStream stream, ConstantPool constantPool)
	{
		stream.print("MethodRef: Class " + classIndex + " ");
		ConstantClassInfo classInfo = constantPool.get(classIndex, ConstantClassInfo.class);
		classInfo.dump(stream, constantPool);
		stream.print(" NameType " + nameAndTypeIndex + " ");
		ConstantNameAndTypeInfo nameTypeInfo = constantPool.get(nameAndTypeIndex, ConstantNameAndTypeInfo.class);
		nameTypeInfo.dump(stream, constantPool);
	}

	@Override
	protected void read(ByteInputStream in)
	{
		tag = CONSTANT_Methodref;
		classIndex = in.u2();
		nameAndTypeIndex = in.u2();
	}

	@Override
	protected boolean verify(int majorVersion, int minorVersion, ConstantPool cp, int index)
	{
		if (majorVersion < 45)
		{
			return false;
		}
		if (majorVersion == 45 && minorVersion < 3)
		{
			return false;
		}
		if (!cp.get(classIndex).is(CONSTANT_Class))
		{
			return false;
		}
		if (!cp.get(nameAndTypeIndex).is(CONSTANT_NameAndType))
		{
			return false;
		}
		// TODO verify class vs interface, verify method name
		return true;
	}
}
