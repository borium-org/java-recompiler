package org.borium.javarecompiler.classfile.constants;

import static org.borium.javarecompiler.classfile.constants.Constant.*;

import org.borium.javarecompiler.classfile.*;

public class ConstantPool
{
	private Constant[] constants;

	public void addReferencedClasses(ReferencedClasses referencedClasses)
	{
		for (Constant c : constants)
		{
			if (c instanceof ConstantClassInfo ci)
			{
				String className = ci.getName();
				if (className.startsWith("["))
				{
					while (className.startsWith("["))
					{
						className = className.substring(1);
					}
					referencedClasses.add(className);
				}
				else
				{
					referencedClasses.add("L" + className + ";");
				}
			}
		}
	}

	public void dump(IndentedOutputStream stream)
	{
		stream.println("Constants:");
		stream.indent(1);
		for (int i = 1; i < constants.length; i++)
		{
			if (constants[i] != null)
			{
				stream.iprint(i + ": ");
				constants[i].dump(stream);
				stream.println();
			}
		}
		stream.indent(-1);
	}

	public Constant get(int index)
	{
		if (index < 0 || index >= constants.length)
		{
			throw new ClassFormatError("Constant index " + index + " is out of range 0.." + (constants.length - 1));
		}
		return constants[index];
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

	/**
	 * Read the constant pool in two phases:
	 * <ol>
	 * <li>Raw data read</li>
	 * <li>Fix up references to other constants</li>
	 * </ol>
	 * Two phases are necessary because all constants that refer to other constants
	 * have forward references which are nulls in first phase.
	 *
	 * @param in
	 */
	public void read(ByteInputStream in)
	{
		int count = in.u2();
		constants = new Constant[count];
		for (int i = 1; i < count; i++)
		{
			int tag = in.u1();
			Constant constant = Constant.create(tag);
			constant.read(in);
			constants[i] = constant;
			if (constant.is(CONSTANT_Long) || constant.is(CONSTANT_Double))
			{
				i++;
			}
		}
		for (int i = 1; i < count; i++)
		{
			if (constants[i] != null)
			{
				constants[i].fixup(this);
			}
		}
	}

	public void verify(int majorVersion, int minorVersion)
	{
		for (int i = 0; i < constants.length; i++)
		{
			Constant constant = constants[i];
			if (constant != null)
			{
				constant.verify(majorVersion, minorVersion, this, i);
			}
		}
	}
}
