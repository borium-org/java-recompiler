package org.borium.javarecompiler.classfile.constants;

import org.borium.javarecompiler.classfile.*;

/**
 * Fields, methods, and interface methods are represented by similar structures:
 *
 * <pre>
	CONSTANT_InterfaceMethodref_info
	{
		u1 tag;
		u2 class_index;
		u2 name_and_type_index;
	}
 * </pre>
 *
 * The tag item of a CONSTANT_InterfaceMethodref_info structure has the value
 * CONSTANT_InterfaceMethodref (11).
 */
public class ConstantInterfaceMethodrefInfo extends Constant
{
	/**
	 * The value of the class_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_Class_info structure (4.4.1) representing a class or interface type
	 * that has the method as a member.
	 * <p>
	 * In a CONSTANT_InterfaceMethodref_info structure, the class_index item must be
	 * an interface type, not a class type.
	 */
	private int classIndex;

	/**
	 * The value of the name_and_type_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_NameAndType_info structure (4.4.6). This constant_pool entry
	 * indicates the name and descriptor of the method.
	 */
	private int nameAndTypeIndex;

	@Override
	protected void read(ByteInputStream in)
	{
		tag = CONSTANT_InterfaceMethodref;
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
		// TODO verify class vs interface
		return true;
	}
}
