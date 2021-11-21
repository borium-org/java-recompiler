package org.borium.javarecompiler.classfile;

import java.io.*;
import java.util.*;

import org.borium.javarecompiler.classfile.constants.*;

public class ClassFile
{
	private ClassInputStream in = new ClassInputStream();

	/**
	 * Major version number, 45..61
	 */
	private int majorVersion;

	/**
	 * For a class file whose major_version is 56 or above, the minor_version must
	 * be 0 or 65535.
	 * <p>
	 * For a class file whose major_version is between 45 and 55 inclusive, the
	 * minor_version may be any value.
	 */
	private int minorVersion;

	/**
	 * The constant_pool is a table of structures (4.4) representing various string
	 * constants, class and interface names, field names, and other constants that
	 * are referred to within the ClassFile structure and its substructures. The
	 * format of each constant_pool table entry is indicated by its first "tag"
	 * byte. The constant_pool table is indexed from 1 to constant_pool_count - 1.
	 * <p>
	 * Implementation of constant pool contains null at index 0 for easy indexing.
	 */
	private ConstantPool cp = new ConstantPool();

	/**
	 * The value of the access_flags item is a mask of flags used to denote access
	 * permissions to and properties of this class or interface. The interpretation
	 * of each flag, when set, is specified in Table 4.1-B: <table border>
	 * <tr>
	 * <th>Flag Name</th>
	 * <th>Value</th>
	 * <th>Interpretation</th>
	 * </tr>
	 * <tr>
	 * <td>ACC_PUBLIC</td>
	 * <td>0x0001</td>
	 * <td>Declared public; may be accessed from outside its package.</td>
	 * </tr>
	 * <tr>
	 * <td>ACC_FINAL</td>
	 * <td>0x0010</td>
	 * <td>Declared final; no subclasses allowed.</td>
	 * </tr>
	 * <tr>
	 * <td>ACC_SUPER</td>
	 * <td>0x0020</td>
	 * <td>Treat superclass methods specially when invoked by the invokespecial
	 * instruction.</td>
	 * </tr>
	 * <tr>
	 * <td>ACC_INTERFACE</td>
	 * <td>0x0200</td>
	 * <td>Is an interface, not a class.</td>
	 * </tr>
	 * <tr>
	 * <td>ACC_ABSTRACT</td>
	 * <td>0x0400</td>
	 * <td>Declared abstract; must not be instantiated.</td>
	 * </tr>
	 * <tr>
	 * <td>ACC_SYNTHETIC</td>
	 * <td>0x1000</td>
	 * <td>Declared synthetic; not present in the source code.</td>
	 * </tr>
	 * <tr>
	 * <td>ACC_ANNOTATION</td>
	 * <td>0x2000</td>
	 * <td>Declared as an annotation interface.</td>
	 * </tr>
	 * <tr>
	 * <td>ACC_ENUM</td>
	 * <td>0x4000</td>
	 * <td>Declared as an enum class.</td>
	 * </tr>
	 * <tr>
	 * <td>ACC_MODULE</td>
	 * <td>0x8000</td>
	 * <td>Is a module, not a class or interface.</td>
	 * </tr>
	 * </table>
	 * The ACC_MODULE flag indicates that this class file defines a module, not a
	 * class or interface. If the ACC_MODULE flag is set, then special rules apply
	 * to the class file which are given at the end of this section. If the
	 * ACC_MODULE flag is not set, then the rules immediately below the current
	 * paragraph apply to the class file.
	 * <p>
	 * An interface is distinguished by the ACC_INTERFACE flag being set. If the
	 * ACC_INTERFACE flag is not set, this class file defines a class, not an
	 * interface or module.
	 * <p>
	 * If the ACC_INTERFACE flag is set, the ACC_ABSTRACT flag must also be set, and
	 * the ACC_FINAL, ACC_SUPER, ACC_ENUM, and ACC_MODULE flags set must not be set.
	 * <p>
	 * If the ACC_INTERFACE flag is not set, any of the other flags in Table 4.1-B
	 * may be set except ACC_ANNOTATION and ACC_MODULE. However, such a class file
	 * must not have both its ACC_FINAL and ACC_ABSTRACT flags set (JLS 8.1.1.2).
	 * <p>
	 * The ACC_SUPER flag indicates which of two alternative semantics is to be
	 * expressed by the invokespecial instruction (invokespecial) if it appears in
	 * this class or interface. Compilers to the instruction set of the Java Virtual
	 * Machine should set the ACC_SUPER flag. In Java SE 8 and above, the Java
	 * Virtual Machine considers the ACC_SUPER flag to be set in every class file,
	 * regardless of the actual value of the flag in the class file and the version
	 * of the class file.
	 * <p>
	 * The ACC_SUPER flag exists for backward compatibility with code compiled by
	 * older compilers for the Java programming language. Prior to JDK 1.0.2, the
	 * compiler generated access_flags in which the flag now representing ACC_SUPER
	 * had no assigned meaning, and Oracle's Java Virtual Machine implementation
	 * ignored the flag if it was set.
	 * <p>
	 * The ACC_SYNTHETIC flag indicates that this class or interface was generated
	 * by a compiler and does not appear in source code.
	 * <p>
	 * An annotation interface (JLS 9.6) must have its ACC_ANNOTATION flag set. If
	 * the ACC_ANNOTATION flag is set, the ACC_INTERFACE flag must also be set.
	 * <p>
	 * The ACC_ENUM flag indicates that this class or its superclass is declared as
	 * an enum class (JLS 8.9).
	 * <p>
	 * All bits of the access_flags item not assigned in Table 4.1-B are reserved
	 * for future use. They should be set to zero in generated class files and
	 * should be ignored by Java Virtual Machine implementations.
	 */
	@SuppressWarnings("unused")
	private int accessFlags;

	/**
	 * The value of the this_class item must be a valid index into the constant_pool
	 * table. The constant_pool entry at that index must be a CONSTANT_Class_info
	 * structure (4.4.1) representing the class or interface defined by this class
	 * file.
	 */
	@SuppressWarnings("unused")
	private int thisClass;

	/**
	 * For a class, the value of the super_class item either must be zero or must be
	 * a valid index into the constant_pool table. If the value of the super_class
	 * item is nonzero, the constant_pool entry at that index must be a
	 * CONSTANT_Class_info structure representing the direct superclass of the class
	 * defined by this class file. Neither the direct superclass nor any of its
	 * superclasses may have the ACC_FINAL flag set in the access_flags item of its
	 * Class0. File structure.
	 * <p>
	 * If the value of the super_class item is zero, then this class file must
	 * represent the class Object, the only class or interface without a direct
	 * superclass.
	 * <p>
	 * For an interface, the value of the super_class item must always be a valid
	 * index into the constant_pool table. The constant_pool entry at that index
	 * must be a CONSTANT_Class_info structure representing the class Object.
	 */
	@SuppressWarnings("unused")
	private int superClass;

	/**
	 * Each value in the interfaces array must be a valid index into the
	 * constant_pool table. The constant_pool entry at each value of interfaces[i],
	 * where 0 &lt;= i &lt; interfaces_count, must be a CONSTANT_Class_info
	 * structure representing an interface that is a direct superinterface of this
	 * class or interface type, in the left-to-right order given in the source for
	 * the type.
	 */
	private int[] interfaces;

	private ArrayList<ClassField> fields = new ArrayList<>();

	private ArrayList<ClassMethod> methods = new ArrayList<>();

	private ArrayList<ClassAttribute> attributes = new ArrayList<>();

	public void read(String fileName) throws IOException, ClassFormatError
	{
		in.open(fileName);
		readID();
		readVersion();
		readConstants();
		readClassInfo();
		readInterfaces();
		readFields();
		readMethods();
		readAttributes();
		in.close();
	}

	private void readAttributes() throws IOException
	{
		int attributeCount = in.u2();
		for (int i = 0; i < attributeCount; i++)
		{
			ClassAttribute attribute = new ClassAttribute();
			attribute.read(in);
			attributes.add(attribute);
		}
		// TODO validation
	}

	private void readClassInfo() throws IOException
	{
		accessFlags = in.u2();
		thisClass = in.u2();
		superClass = in.u2();
		// TODO extended validation
		// throw new ClassFormatError("Feature is not supported yet");
	}

	private void readConstants() throws IOException
	{
		cp.read(in);
		cp.verify(majorVersion, minorVersion);
	}

	private void readFields() throws IOException
	{
		int count = in.u2();
		for (int i = 0; i < count; i++)
		{
			ClassField field = new ClassField();
			field.read(in);
			fields.add(field);
		}
		// TODO extended validation
		// throw new ClassFormatError("Feature is not supported yet");
	}

	private void readID() throws IOException, ClassFormatError
	{
		int magic = in.u4();
		if (magic != 0xCAFEBABE)
		{
			throw new ClassFormatError("CAFEBABE not found");
		}
	}

	private void readInterfaces() throws IOException
	{
		int count = in.u2();
		interfaces = new int[count];
		for (int i = 0; i < count; i++)
		{
			interfaces[i] = in.u2();
		}
		// TODO validation
		// throw new ClassFormatError("Feature is not supported yet");
	}

	private void readMethods() throws IOException
	{
		int count = in.u2();
		for (int i = 0; i < count; i++)
		{
			ClassMethod method = new ClassMethod();
			method.read(in);
			methods.add(method);
		}
		// TODO validation
		// throw new ClassFormatError("Feature is not supported yet");
	}

	private void readVersion() throws IOException, ClassFormatError
	{
		minorVersion = in.u2();
		majorVersion = in.u2();
		if (majorVersion != 60 || minorVersion != 0)
		{
			throw new ClassFormatError("Unsupported version " + majorVersion + ":" + minorVersion);
		}
	}
}
