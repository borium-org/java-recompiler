package org.borium.javarecompiler.classfile;

import java.io.*;

public class ClassInputStream
{
	private DataInputStream in;

	public double f4() throws IOException
	{
		return in.readFloat();
	}

	public double f8() throws IOException
	{
		return in.readDouble();
	}

	public String readUtf8() throws IOException
	{
		return in.readUTF();
	}

	public int u1() throws IOException
	{
		return in.readUnsignedByte();
	}

	public int u2() throws IOException
	{
		return in.readUnsignedShort();
	}

	public int u4() throws IOException
	{
		return in.readInt();
	}

	public long u8() throws IOException
	{
		return in.readLong();
	}

	protected void close() throws IOException
	{
		in.close();
	}

	protected void open(String fileName) throws IOException
	{
		in = new DataInputStream(new FileInputStream(fileName));
	}
}
