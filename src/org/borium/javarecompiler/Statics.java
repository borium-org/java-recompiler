package org.borium.javarecompiler;

import org.borium.javarecompiler.cplusplus.*;

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

	public static String commaSeparatedList(String[] values)
	{
		String result = "";
		String separator = "";
		for (String param : values)
		{
			result += separator + param;
			separator = ", ";
		}
		return result;
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
	 * Parse Java method return type.
	 *
	 * @param javaMethodSignature Method signature.
	 * @return Return type or null for void.
	 */
	public static String parseJavaReturnType(String javaMethodSignature)
	{
		int pos = javaMethodSignature.indexOf(')');
		Assert(pos >= 1, "Method with no return type");
		String type = javaMethodSignature.substring(pos + 1);
		switch (type.charAt(0))
		{
		case 'B':
			return "byte";
		case 'C':
			return "char";
		case 'D':
			return "double";
		case 'F':
			return "float";
		case 'I':
			return "int";
		case 'J':
			return "long";
		case 'S':
			return "short";
		case 'Z':
			return "bool";
		case 'L':
			return new JavaTypeConverter(type, false).getCppType();
		case 'V':
			return "void";
		default:
			Assert(false, "Unhandled type " + type);
		}
		return null;
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
