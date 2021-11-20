package org.borium.javarecompiler.classfile;

import java.io.*;

class ClassInputStream
{
	private DataInputStream in;

	protected void close() throws IOException
	{
		in.close();
	}

	protected void open(String fileName) throws IOException
	{
		in = new DataInputStream(new FileInputStream(fileName));
	}

	protected int u2() throws IOException
	{
		return in.readUnsignedShort();
	}

	protected int u4() throws IOException
	{
		return in.readInt();
	}
}
