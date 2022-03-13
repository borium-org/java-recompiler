package org.borium.javarecompiler.classfile.attribute;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

public class AttributeConstantValue extends ClassAttribute
{
	private Constant constantValue;

	public AttributeConstantValue(ClassAttribute attribute, ConstantPool cp)
	{
		super(attribute);
		decode(cp);
	}

	public Constant getConstant()
	{
		return constantValue;
	}

	@Override
	protected void detailedDump(IndentedOutputStream stream)
	{
		stream.iprintln("ConstantValue:");
		stream.indent(1);
		stream.iprint("");
		constantValue.dump(stream);
		stream.println();
		stream.indent(-1);
	}

	private void decode(ConstantPool cp)
	{
		ByteInputStream in = new ByteInputStream(info);
		int constantIndex = in.u2();
		in.close();
		constantValue = cp.get(constantIndex);
	}
}
