package org.borium.javarecompiler.classfile.attribute;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

public class AttributeSignature extends ClassAttribute
{
	/**
	 * The value of the signature_index item must be a valid index into the
	 * constant_pool table. The constant_pool entry at that index must be a
	 * CONSTANT_Utf8_info structure (4.4.7) representing a class signature if this
	 * Signature attribute is an attribute of a ClassFile structure; a method
	 * signature if this Signature attribute is an attribute of a method_info
	 * structure; or a field signature otherwise.
	 */
	private int signatureIndex;

	/** The string that matches the signature index. */
	private String signature;

	public AttributeSignature(ClassAttribute attribute, ConstantPool cp)
	{
		super(attribute);
		decode(cp);
		signature = cp.getString(signatureIndex);
	}

	public String getSignature()
	{
		return signature;
	}

	@Override
	protected void detailedDump(IndentedOutputStream stream)
	{
		stream.iprintln("Signature: " + signatureIndex + " " + signature);
	}

	private void decode(ConstantPool cp)
	{
		ByteInputStream in = new ByteInputStream(info);
		signatureIndex = in.u2();
		in.close();
	}
}
