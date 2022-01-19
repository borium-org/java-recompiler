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
}
