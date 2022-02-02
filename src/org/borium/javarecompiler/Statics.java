package org.borium.javarecompiler;

/**
 * Helper class with various static methods to reduce clutter.
 */
public class Statics
{
	public static void Assert(boolean condition, String errorMessage)
	{
		if (!condition)
		{
			throw new RuntimeException(errorMessage);
		}
	}

	/**
	 * Generate upper-cased hex string. String will have leading zeros if necessary.
	 *
	 * @param value  Value to fill the string with.
	 * @param length Length of output string.
	 * @return hex string of value, with specified length.
	 */
	public static String hexString(int value, int length)
	{
		String result = "00000000" + Integer.toHexString(value);
		result = result.substring(result.length() - length);
		return result.toUpperCase();
	}

	/**
	 * Convert simple non-template non-pointer Java class name to C++ class name by
	 * replacing all '.' with '::'.
	 *
	 * @param javaClassName Java class name.
	 * @return C++ class name.
	 */
	public static String javaToCppClass(String javaClassName)
	{
		String[] split = javaClassName.split("[.]");
		return String.join("::", split);
	}

	/**
	 * Convenience method to remove trailing star from a pointer type.
	 *
	 * @param typeWithStar Pointer type with trailing star.
	 * @return Pointed-to type, without that trailing star.
	 */
	public static String removeStar(String typeWithStar)
	{
		return typeWithStar.substring(0, typeWithStar.length() - 1);
	}
}
