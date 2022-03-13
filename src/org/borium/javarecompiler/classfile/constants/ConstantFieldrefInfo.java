package org.borium.javarecompiler.classfile.constants;

import org.borium.javarecompiler.classfile.*;

/**
 * Fields, methods, and interface methods are represented by similar structures:
 *
 * <pre>
	CONSTANT_Fieldref_info
	{
		u1 tag;
		u2 class_index;
		u2 name_and_type_index;
	}
 * </pre>
 *
 * The tag item of a CONSTANT_Fieldref_info structure has the value
 * CONSTANT_Fieldref (9).
 */
public class ConstantFieldrefInfo extends Constant
{
	/**
	 * The value of the class_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_Class_info structure (4.4.1) representing a class or interface type
	 * that has the field as a member.
	 * <p>
	 * In a CONSTANT_Fieldref_info structure, the class_index item may be either a
	 * class type or an interface type.
	 */
	private int classIndex;

	/**
	 * The value of the name_and_type_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_NameAndType_info structure (4.4.6). This constant_pool entry
	 * indicates the name and descriptor of the field.
	 * <p>
	 * In a CONSTANT_Fieldref_info structure, the indicated descriptor must be a
	 * field descriptor (4.3.2).
	 */
	public int nameAndTypeIndex;

	private ConstantClassInfo classInfo;

	private ConstantNameAndTypeInfo nameTypeInfo;

	@Override
	public void dump(IndentedOutputStream stream)
	{
		stream.print("FieldRef:  Class " + classIndex + " ");
		classInfo.dump(stream);
		stream.print(" NameType " + nameAndTypeIndex + " ");
		nameTypeInfo.dump(stream);
	}

	public String getClassName()
	{
		return classInfo.getName();
	}

	@Override
	protected void fixup(ConstantPool constantPool)
	{
		classInfo = constantPool.get(classIndex, ConstantClassInfo.class);
		nameTypeInfo = constantPool.get(nameAndTypeIndex, ConstantNameAndTypeInfo.class);
	}

	@Override
	protected void read(ByteInputStream in)
	{
		tag = CONSTANT_Fieldref;
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
		// TODO extended validation for field descriptor in 4.3.2
		return true;
	}
}
