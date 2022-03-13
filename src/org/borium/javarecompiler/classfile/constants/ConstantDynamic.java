package org.borium.javarecompiler.classfile.constants;

import org.borium.javarecompiler.classfile.*;

/**
 * The CONSTANT_Dynamic_info structure is used to represent a dynamically
 * computed constant, an arbitrary value that is produced by invocation of a
 * bootstrap method in the course of an LDC instruction, among others. The
 * auxiliary type specified by the structure constrains the type of the
 * dynamically computed constant.
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
 * The tag item of a CONSTANT_Dynamic_info structure has the value
 * CONSTANT_Dynamic (17).
 */
public class ConstantDynamic extends Constant
{
	/**
	 * The value of the bootstrap_method_attr_index item must be a valid index into
	 * the bootstrap_methods array of the bootstrap method table of this class file
	 * (4.7.23).
	 * <p>
	 * CONSTANT_Dynamic_info structures are unique in that they are syntactically
	 * allowed to refer to themselves via the bootstrap method table. Rather than
	 * mandating that such cycles are detected when classes are loaded (a
	 * potentially expensive check), we permit cycles initially but mandate a
	 * failure at resolution (5.4.3.6).
	 */
	private int bootstrapMethodAttrIndex;

	/**
	 * The value of the name_and_type_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_NameAndType_info structure (4.4.6). This constant_pool entry
	 * indicates a name and descriptor.
	 * <p>
	 * In a CONSTANT_Dynamic_info structure, the indicated descriptor must be a
	 * field descriptor (4.3.2).
	 */
	private int nameAndTypeIndex;

	private String nameAndType;

	private String bootstrap;

	@Override
	public void dump(IndentedOutputStream stream)
	{
		stream.println("Dynamic: Bootstrap " + bootstrap + " Name and type " + nameAndType);
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
		tag = CONSTANT_Dynamic;
		bootstrapMethodAttrIndex = in.u2();
		nameAndTypeIndex = in.u2();
	}

	@Override
	protected boolean verify(int majorVersion, int minorVersion, ConstantPool cp, int index)
	{
		if (majorVersion < 55 || minorVersion != 0)
		{
			return false;
		}
		// TODO extended validation
		return true;
	}
}
