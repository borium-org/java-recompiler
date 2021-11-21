package org.borium.javarecompiler.classfile;

import java.io.*;

import org.borium.javarecompiler.classfile.constants.*;

public class ClassFile
{
	private ClassInputStream in = new ClassInputStream();
	private int majorVersion;
	private int minorVersion;
	private ConstantPool cp = new ConstantPool();

	public void read(String fileName) throws IOException, ClassFormatError
	{
		in.open(fileName);
		readID();
		readVersion();
		readConstants();
		readClassInfo();
		readInterfaces();
		readFields();
		readAttributes();
		in.close();
	}

	private void readAttributes()
	{
		// TODO Auto-generated method stub
		throw new ClassFormatError("Feature is not supported yet");
	}

	private void readClassInfo()
	{
		// TODO Auto-generated method stub
		throw new ClassFormatError("Feature is not supported yet");
	}

	private void readConstants() throws IOException
	{
		cp.read(in);
		cp.verify(majorVersion, minorVersion);
	}

	private void readFields()
	{
		// TODO Auto-generated method stub
		throw new ClassFormatError("Feature is not supported yet");
	}

	private void readID() throws IOException, ClassFormatError
	{
		int magic = in.u4();
		if (magic != 0xCAFEBABE)
		{
			throw new ClassFormatError("CAFEBABE not found");
		}
	}

	private void readInterfaces()
	{
		// TODO Auto-generated method stub
		throw new ClassFormatError("Feature is not supported yet");
	}

	private void readVersion() throws IOException, ClassFormatError
	{
		minorVersion = in.u2();
		majorVersion = in.u2();
		if (majorVersion != 60 || minorVersion != 0)
		{
			throw new ClassFormatError("Unsupported version " + majorVersion + ":" + minorVersion);
		}
	}
}
