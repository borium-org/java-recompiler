package org.borium.javarecompiler.classfile.constants;

import org.borium.javarecompiler.classfile.*;

/**
 * The CONSTANT_InvokeDynamic_info structure is used to represent a
 * dynamically-computed call site, an instance of java.lang.invoke.CallSite that
 * is produced by invocation of a bootstrap method in the course of an invoke
 * dynamic instruction. The auxiliary type specified by the structure constrains
 * the method type of the dynamically-computed call site.
 *
 * <pre>
	CONSTANT_Dynamic_info
	{
		u1 tag;
		u2 bootstrap_method_attr_index;
		u2 name_and_type_index;
	}
 * </pre>
 *
 * The tag item of a CONSTANT_InvokeDynamic_info structure has the value
 * CONSTANT_InvokeDynamic (18).
 */
public class ConstantInvokeDynamic extends Constant
{
	/**
	 * The value of the bootstrap_method_attr_index item must be a valid index into
	 * the bootstrap_methods array of the bootstrap method table of this class file
	 * (4.7.23).
	 */
	private int bootstrapMethodAttrIndex;

	/**
	 * The value of the name_and_type_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_NameAndType_info structure (4.4.6). This constant_pool entry
	 * indicates a name and descriptor.
	 * <p>
	 * In a CONSTANT_InvokeDynamic_info structure, the indicated descriptor must be
	 * a method descriptor (4.3.3).
	 */
	private int nameAndTypeIndex;

	private String bootstrap;

	private String nameAndType;

	@Override
	public void dump(IndentedOutputStream stream)
	{
		stream.println("InvokeDynamic: Bootstrap " + bootstrap + " Name and type " + nameAndType);
	}

	@Override
	protected void fixup(ConstantPool constantPool)
	{
		bootstrap = constantPool.getString(bootstrapMethodAttrIndex);
		nameAndType = constantPool.getString(nameAndTypeIndex);
	}

	@Override
	protected void read(ByteInputStream in)
	{
		tag = CONSTANT_InvokeDynamic;
		bootstrapMethodAttrIndex = in.u2();
		nameAndTypeIndex = in.u2();
	}

	@Override
	protected boolean verify(int majorVersion, int minorVersion, ConstantPool cp, int index)
	{
		if (majorVersion < 51 || minorVersion != 0)
		{
			return false;
		}
		// TODO extended validation
		return true;
	}

}
