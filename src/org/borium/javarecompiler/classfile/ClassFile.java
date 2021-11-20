package org.borium.javarecompiler.classfile;

import java.io.*;

public class ClassFile
{
	private ClassInputStream in = new ClassInputStream();

	public void load(String fileName) throws IOException, ClassFormatError
	{
		in.open(fileName);
		readID();
		readVersion();
		in.close();
	}

	private void readID() throws IOException, ClassFormatError
	{
		int magic = in.u4();
		if (magic != 0xCAFEBABE)
		{
			throw new ClassFormatError("CAFEBABE not found");
		}
	}

	private void readVersion() throws IOException, ClassFormatError
	{
		int minor = in.u2();
		int major = in.u2();
		if (major != 60 || minor != 0)
		{
			throw new ClassFormatError("Unsupported version " + major + ":" + minor);
		}
	}
}
