package org.borium.javarecompiler.classfile.constants;

import org.borium.javarecompiler.classfile.*;

/**
 * The CONSTANT_Module_info structure is used to represent a module:
 *
 * <pre>
	CONSTANT_Module_info
	{
		u1 tag;
		u2 name_index;
	}
 * </pre>
 *
 * The tag item has the value CONSTANT_Module (19).
 * <p>
 * A CONSTANT_Module_info structure is permitted only in the constant pool of a
 * class file that declares a module, that is, a ClassFile structure where the
 * access_flags item has the ACC_MODULE flag set. In all other class files, a
 * CONSTANT_Module_info structure is illegal.
 */
public class ConstantModule extends Constant
{
	/**
	 * The value of the name_index item must be a valid index into the constant_pool
	 * table. The constant_pool entry at that index must be a CONSTANT_Utf8_info
	 * structure (4.4.7) representing a valid module name (4.2.3).
	 */
	private int nameIndex;

	private String name;

	@Override
	public void dump(IndentedOutputStream stream)
	{
		stream.println("Module: " + name);
	}

	@Override
	protected void fixup(ConstantPool constantPool)
	{
		name = constantPool.getString(nameIndex);
	}

	@Override
	protected void read(ByteInputStream in)
	{
		tag = CONSTANT_Module;
		nameIndex = in.u2();
	}

	@Override
	protected boolean verify(int majorVersion, int minorVersion, ConstantPool cp, int index)
	{
		if (majorVersion < 53 || minorVersion != 0)
		{
			return false;
		}
		// TODO extended validation
		return true;
	}
}
