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

	/**
	 * Indented print of binary data, 16 bytes with delimiters per line.
	 *
	 * @param info
	 */
	public void iprintln(byte[] info)
	{
		for (int start = 0; start < info.length; start += 16)
		{
			iprint("");
			for (int offset = 0; offset < 16; offset++)
			{
				if (start + offset >= info.length)
				{
					break;
				}
				printHex(info[start + offset], 2);
				if (offset == 7)
				{
					print(" | ");
				}
				else if (offset != 15)
				{
					print(" ");
				}
			}
			println();
		}
	}

	public void iprintln(byte[] info, int start, int length)
	{
		iprint("");
		println(info, start, length);
	}

	public void iprintln(String string)
	{
		iprint(string);
		println();
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
		hex = hex.substring(hex.length() - length).toUpperCase();
		stream.print(hex);
	}

	public void println()
	{
		stream.println();
	}

	public void println(byte[] info, int start, int length)
	{
		for (int offset = start; offset < start + length; offset++)
		{
			printHex(info[offset], 2);
			print(" ");
		}
		println();
	}

	public void println(String string)
	{
		stream.println(string);
	}
}
