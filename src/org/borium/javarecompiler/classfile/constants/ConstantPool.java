package org.borium.javarecompiler.classfile.constants;

import java.io.*;
import java.util.*;

import org.borium.javarecompiler.classfile.*;

public class ConstantPool
{
	private ArrayList<Constant> constants = new ArrayList<>();

	public Constant get(int index)
	{
		if (index < 0 || index >= constants.size())
		{
			throw new ClassFormatError("Constant index " + index + " is out of range 0.." + constants.size());
		}
		return constants.get(index);
	}

	public String getString(int index)
	{
		Constant constant = get(index);
		if (constant instanceof ConstantUtf8Info utf8)
		{
			return utf8.string();
		}
		throw new ClassFormatError("Index " + index + " is not a string but " + constant.getClass().getSimpleName());
	}

	public void read(ClassInputStream in) throws IOException
	{
		int count = in.u2();
		constants.add(null);
		for (int i = 1; i < count; i++)
		{
			int tag = in.u1();
			Constant constant = Constant.create(tag);
			constant.read(in);
			constants.add(constant);
		}
	}

	public void verify(int majorVersion, int minorVersion)
	{
		for (int i = 0; i < constants.size(); i++)
		{
			Constant constant = constants.get(i);
			if (constant != null)
			{
				constant.verify(majorVersion, minorVersion, this, i);
			}
		}
	}
}
