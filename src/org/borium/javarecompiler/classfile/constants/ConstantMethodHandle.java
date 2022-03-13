package org.borium.javarecompiler.classfile.constants;

import org.borium.javarecompiler.classfile.*;

/**
 * The CONSTANT_MethodHandle_info structure is used to represent a method
 * handle:
 *
 * <pre>
	CONSTANT_MethodHandle_info
	{
		u1 tag;
		u1 reference_kind;
		u2 reference_index;
	}
 * </pre>
 *
 * The tag item has the value CONSTANT_MethodHandle (15).
 */
public class ConstantMethodHandle extends Constant
{
	/**
	 * The value of the reference_kind item must be in the range 1 to 9. The value
	 * denotes the kind of this method handle, which characterizes its bytecode
	 * behavior (5.4.3.5).
	 */
	private int referenceKind;

	/**
	 * The value of the reference_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be as
	 * follows:
	 * <ul>
	 * <li>If the value of the reference_kind item is 1 (REF_getField), 2
	 * (REF_getStatic), 3 (REF_putField), or 4 (REF_putStatic), then the
	 * constant_pool entry at that index must be a CONSTANT_Fieldref_info structure
	 * (4.4.2) representing a field for which a method handle is to be created.</li>
	 * <li>If the value of the reference_kind item is 5 (REF_invokeVirtual) or 8
	 * (REF_newInvokeSpecial), then the constant_pool entry at that index must be a
	 * CONSTANT_Methodref_info structure (4.4.2) representing a class's method or
	 * constructor (2.9.1) for which a method handle is to be created.</li>
	 * <li>If the value of the reference_kind item is 6 (REF_invokeStatic) or 7
	 * (REF_invokeSpecial), then if the class file version number is less than 52.0,
	 * the constant_pool entry at that index must be a CONSTANT_Methodref_info
	 * structure representing a class's method for which a method handle is to be
	 * created; if the class file version number is 52.0 or above, the constant_pool
	 * entry at that index must be either a CONSTANT_Methodref_info structure or a
	 * CONSTANT_InterfaceMethodref_info structure (4.4.2) representing a class's or
	 * interface's method for which a method handle is to be created.</li>
	 * <li>If the value of the reference_kind item is 9 (REF_invokeInterface), then
	 * the constant_pool entry at that index must be a
	 * CONSTANT_InterfaceMethodref_info structure representing an interface's method
	 * for which a method handle is to be created.</li>
	 * </ul>
	 * If the value of the reference_kind item is 5 (REF_invokeVirtual), 6
	 * (REF_invokeStatic), 7 (REF_invokeSpecial), or 9 (REF_invokeInterface), the
	 * name of the method represented by a CONSTANT_Methodref_info structure or a
	 * CONSTANT_InterfaceMethodref_info structure must not be &lt;init&gt; or
	 * &lt;clinit&gt;.
	 * <p>
	 * If the value is 8 (REF_newInvokeSpecial), the name of the method represented
	 * by a CONSTANT_Methodref_info structure must be &lt;init&gt;.
	 */
	private int referenceIndex;

	@Override
	public void dump(IndentedOutputStream stream)
	{
		stream.println("MethodHandle: Kind " + referenceKind + " Index " + referenceIndex);
	}

	@Override
	protected void read(ByteInputStream in)
	{
		tag = CONSTANT_MethodHandle;
		referenceKind = in.u1();
		referenceIndex = in.u2();
	}

	@Override
	protected boolean verify(int majorVersion, int minorVersion, ConstantPool cp, int index)
	{
		if (majorVersion < 51 || minorVersion != 0)
		{
			return false;
		}
		// TODO extended kind/index validation
		return true;
	}
}
