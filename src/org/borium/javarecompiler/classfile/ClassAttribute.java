package org.borium.javarecompiler.classfile;

import org.borium.javarecompiler.classfile.attribute.*;
import org.borium.javarecompiler.classfile.constants.*;

/**
 * Attributes are used in the ClassFile, field_info, method_info,
 * Code_attribute, and record_component_info structures of the class file format
 * (4.1, 4.5, 4.6, 4.7.3, 4.7.30).
 * <p>
 * All attributes have the following general format:
 *
 * <pre>
	attribute_info
	{
		u2 attribute_name_index;
		u4 attribute_length;
		u1 info[attribute_length];
	}
 * </pre>
 */
public class ClassAttribute
{
	/**
	 * Read an attribute from class input stream.
	 *
	 * @param in        Class input stream.
	 * @param constants Constant pool to be used for specializing the attribute
	 *                  object.
	 * @return Generic ClassAttribute object or specific derived object, based on
	 *         attribute name.
	 */
	public static ClassAttribute readAttribute(ByteInputStream in, ConstantPool constants)
	{
		ClassAttribute attribute = new ClassAttribute();
		attribute.read(in, constants);
		switch (attribute.attributeName)
		{
		case "Code":
			return new AttributeCode(attribute, constants);
		}
		return attribute;
	}

	/**
	 * For all attributes, the attribute_name_index item must be a valid unsigned
	 * 16-bit index into the constant pool of the class. The constant_pool entry at
	 * attribute_name_index must be a CONSTANT_Utf8_info structure (4.4.7)
	 * representing the name of the attribute.
	 */
	private int attributeNameIndex;

	/**
	 * The value of the attribute_length item indicates the length of the subsequent
	 * information in bytes. The length does not include the initial six bytes that
	 * contain the attribute_name_index and attribute_length items.
	 */
	private int attributeLength;

	/**
	 * Name of the attribute, extracted from constant pool.
	 */
	private String attributeName;

	/**
	 * Attribute data.
	 */
	protected byte[] info;

	/**
	 * Copy constructor for decoded attributes. All fields from base class are
	 * copied over.
	 *
	 * @param attribute Source attribute with all the basic data without any
	 *                  decoding.
	 */
	protected ClassAttribute(ClassAttribute attribute)
	{
		attributeNameIndex = attribute.attributeNameIndex;
		attributeLength = attribute.attributeLength;
		attributeName = attribute.attributeName;
		info = attribute.info;
	}

	/**
	 * Private constructor to create a basic attribute that has generic fields.
	 * Read() will fill the object and then create a new specific attribute object
	 * based on attribute name.
	 */
	private ClassAttribute()
	{
	}

	public void dump(IndentedOutputStream stream, ConstantPool cp)
	{
		// TODO Auto-generated method stub
	}

	public String getName()
	{
		return attributeName;
	}

	private void read(ByteInputStream in, ConstantPool constants)
	{
		attributeNameIndex = in.u2();
		attributeLength = in.u4();
		info = in.read(attributeLength);
		// TODO validation and decoding
		attributeName = constants.getString(attributeNameIndex);
	}
}
