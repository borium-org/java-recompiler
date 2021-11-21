package org.borium.javarecompiler.classfile;

import java.io.*;

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
	 * For all attributes, the attribute_name_index item must be a valid unsigned
	 * 16-bit index into the constant pool of the class. The constant_pool entry at
	 * attribute_name_index must be a CONSTANT_Utf8_info structure (4.4.7)
	 * representing the name of the attribute.
	 */
	@SuppressWarnings("unused")
	private int attributeNameIndex;

	/**
	 * The value of the attribute_length item indicates the length of the subsequent
	 * information in bytes. The length does not include the initial six bytes that
	 * contain the attribute_name_index and attribute_length items.
	 */
	private int attributeLength;

	/**
	 * Attribute data.
	 */
	private byte[] info;

	public void read(ClassInputStream in) throws IOException
	{
		attributeNameIndex = in.u2();
		attributeLength = in.u4();
		info = new byte[attributeLength];
		in.read(info);
		// TODO validation and decoding
	}
}
