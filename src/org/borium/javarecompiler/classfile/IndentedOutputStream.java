package org.borium.javarecompiler.classfile;

import java.io.*;

/**
 * Print stream with indentation capability.
 */
public class IndentedOutputStream
{
	/** The print stream where all the stuff goes. */
	private PrintStream stream;

	/** Indentation level for iprint() and iprintln(). */
	private int indentLevel = 0;

	public IndentedOutputStream(String fileName) throws IOException
	{
		stream = new PrintStream(fileName);
	}

	public void indent(int i)
	{
		indentLevel += i;
	}

	public void iprint(String string)
	{
		for (int i = 0; i < indentLevel; i++)
		{
			stream.print("\t");
		}
		stream.print(string);
	}

	public void print(String string)
	{
		stream.print(string);
	}

	public void printHex(int value, int length)
	{
		String hex = Integer.toHexString(value);
		while (hex.length() < length)
		{
			hex = "0" + hex;
		}
		hex = hex.substring(0, length).toUpperCase();
		stream.print(hex);
	}

	public void println()
	{
		stream.println();
	}

	public void println(String string)
	{
		stream.println(string);
	}
}
