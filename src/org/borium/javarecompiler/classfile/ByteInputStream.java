package org.borium.javarecompiler.classfile;

import java.io.*;
import java.nio.*;

public class ByteInputStream
{
	private static double convertToDouble(byte[] array)
	{
		ByteBuffer buffer = ByteBuffer.wrap(array);
		return buffer.getDouble();
	}

	private static double convertToFloat(byte[] array)
	{
		ByteBuffer buffer = ByteBuffer.wrap(array);
		return buffer.getFloat();
	}

	private ByteArrayInputStream in;
	private int length;

	public ByteInputStream(byte[] data)
	{
		in = new ByteArrayInputStream(data);
		length = data.length;
	}

	public int available()
	{
		return in.available();
	}

	public void close()
	{
		try
		{
			if (available() > 0)
			{
				throw new ClassFormatError("Closing with " + available() + " bytes available");
			}
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public double f4()
	{
		if (available() < Float.BYTES)
		{
			throw new ClassFormatError("Not enough data available to read a float");
		}
		byte[] data = read(4);
		double value = convertToFloat(data);
		return value;
	}

	public double f8()
	{
		if (available() < Double.BYTES)
		{
			throw new ClassFormatError("Not enough data available to read a double");
		}
		byte[] data = read(8);
		double value = convertToDouble(data);
		return value;
	}

	public int getPosition()
	{
		return length - in.available();
	}

	public byte[] read(int length)
	{
		if (available() < length)
		{
			throw new ClassFormatError("Not enough data available to read, needed " + length);
		}
		byte[] data = new byte[length];
		try
		{
			in.read(data);
		}
		catch (IOException e)
		{
			throw new ClassFormatError("Can't read byte array");
		}
		return data;
	}

	public int s1()
	{
		if (available() < 1)
		{
			throw new ClassFormatError("Not enough data available to read a byte");
		}
		int value = in.read();
		if (value >= 0x80)
		{
			value = value - 0x100;
		}
		return value;
	}

	public int s2()
	{
		if (available() < 2)
		{
			throw new ClassFormatError("Not enough data available to read a short");
		}
		int value = in.read() << 8 | in.read();
		if (value >= 0x8000)
		{
			value -= 0x10000;
		}
		return value;
	}

	public int s4()
	{
		if (available() < 4)
		{
			throw new ClassFormatError("Not enough data available to read an int");
		}
		return u4();
	}

	public int u1()
	{
		if (available() < 1)
		{
			throw new ClassFormatError("Not enough data available to read a byte");
		}
		return in.read();
	}

	public int u2()
	{
		if (available() < 2)
		{
			throw new ClassFormatError("Not enough data available to read an ushort");
		}
		int byte1 = in.read();
		int byte2 = in.read();
		return byte1 << 8 | byte2;
	}

	public int u4()
	{
		if (available() < 4)
		{
			throw new ClassFormatError("Not enough data available to read an uint");
		}
		int byte1 = in.read();
		int byte2 = in.read();
		int byte3 = in.read();
		int byte4 = in.read();
		return byte1 << 24 | byte2 << 16 | byte3 << 8 | byte4;
	}

	public long u8()
	{
		if (available() < 8)
		{
			throw new ClassFormatError("Not enough data available to read an ulong");
		}
		long byte1 = in.read();
		long byte2 = in.read();
		long byte3 = in.read();
		long byte4 = in.read();
		long byte5 = in.read();
		long byte6 = in.read();
		long byte7 = in.read();
		long byte8 = in.read();
		return byte1 << 56 | byte2 << 48 | byte3 << 40 | byte4 << 32 | byte5 << 24 | byte6 << 16 | byte7 << 8 | byte8;
	}

	public String utf8()
	{
		if (available() < 2)
		{
			throw new ClassFormatError("Not enough data available to read UTF8 length");
		}
		int length = u2();
		byte[] bytes = read(length);
		try
		{
			return new String(bytes, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			throw new ClassFormatError("UTF-8 exception");
		}
	}
}
