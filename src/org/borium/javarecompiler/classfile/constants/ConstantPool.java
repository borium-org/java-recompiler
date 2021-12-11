package org.borium.javarecompiler.classfile.constants;

import java.util.*;

import org.borium.javarecompiler.classfile.*;

public class ConstantPool
{
	private ArrayList<Constant> constants = new ArrayList<>();

	public void dump(IndentedOutputStream stream)
	{
		stream.println("Constants:");
		stream.indent(1);
		for (int i = 1; i < constants.size(); i++)
		{
			stream.iprint(i + ": ");
			constants.get(i).dump(stream, this);
			stream.println();
		}
		stream.indent(-1);
	}

	public Constant get(int index)
	{
		if (index < 0 || index >= constants.size())
		{
			throw new ClassFormatError("Constant index " + index + " is out of range 0.." + constants.size());
		}
		return constants.get(index);
	}

	/**
	 * Get a constant, verify that it is of correct type.
	 *
	 * @param <T>   Constant-derived class to match the constant type.
	 * @param index Constant index in the pool.
	 * @param clazz Expected class of the constant.
	 * @return Constant that is converted to expected type.
	 * @throws ClassFormatError Constant is not of expected type.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Constant> T get(int index, Class<T> clazz) throws ClassFormatError
	{
		Constant c = get(index);
		if (clazz.isInstance(c))
		{
			return (T) c;
		}
		throw new ClassFormatError("Constant " + index + " is not " + clazz.getSimpleName());
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

	public void read(ByteInputStream in)
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
