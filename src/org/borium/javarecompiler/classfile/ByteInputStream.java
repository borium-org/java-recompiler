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

	public ByteInputStream(byte[] data)
	{
		in = new ByteArrayInputStream(data);
	}

	public int available()
	{
		return in.available();
	}

	public void close()
	{
		try
		{
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public double f4()
	{
		byte[] data = read(4);
		double value = convertToFloat(data);
		return value;
	}

	public double f8()
	{
		byte[] data = read(8);
		double value = convertToDouble(data);
		return value;
	}

	public byte[] read(int length)
	{
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
		int value = in.read();
		if (value >= 0x80)
		{
			value = value - 0x100;
		}
		return value;
	}

	public int s2()
	{
		int value = in.read() << 8 | in.read();
		if (value >= 0x8000)
		{
			value -= 0x10000;
		}
		return value;
	}

	public int s4()
	{
		return u4();
	}

	public int u1()
	{
		return in.read();
	}

	public int u2()
	{
		int byte1 = in.read();
		int byte2 = in.read();
		return byte1 << 8 | byte2;
	}

	public int u4()
	{
		int byte1 = in.read();
		int byte2 = in.read();
		int byte3 = in.read();
		int byte4 = in.read();
		return byte1 << 24 | byte2 << 16 | byte3 << 8 | byte4;
	}

	public long u8()
	{
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
