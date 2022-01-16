package org.borium.javarecompiler;

/**
 * Helper class with various static methods to reduce clutter.
 */
public class Statics
{
	public static void Assert(boolean condition, String message)
	{
		if (!condition)
		{
			throw new RuntimeException(message);
		}
	}
}
