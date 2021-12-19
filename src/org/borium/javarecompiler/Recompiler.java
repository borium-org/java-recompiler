package org.borium.javarecompiler;

import java.io.*;

import org.borium.javarecompiler.classfile.*;

public class Recompiler
{
	public static void main(String[] args)
	{
		ClassFile classFile = new ClassFile();
		try
		{
			classFile.read("bin/org/borium/javarecompiler/classfile/ClassFile.class");
			IndentedOutputStream stream = new IndentedOutputStream("ClassFile.txt");
			classFile.dump(stream);
		}
		catch (ClassFormatError | IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Done.");
	}
}
