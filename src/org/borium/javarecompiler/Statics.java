package org.borium.javarecompiler;

import org.borium.javarecompiler.classfile.*;
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

	public static void Check(IndentedOutputStream stream, boolean condition, String errorMessage)
	{
		if (!condition)
		{
			stream.iprintln("// " + errorMessage);
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

	public static String dotToNamespace(String dots)
	{
		return String.join("::", dots.split("[.]"));
	}

	/**
	 * Calculate number of parameters to the method that is part of this name and
	 * type info. 'This' is not assumed to be present.
	 *
	 * @param javaDescriptor Java method descriptor.
	 * @return Parameter count, excluding optional 'this'.
	 */
	public static int getParameterCount(String javaDescriptor)
	{
		if (!javaDescriptor.startsWith("("))
		{
			throw new RuntimeException("Get parameter count for non-method");
		}
		int[] data = { 1, 0 };
		while (javaDescriptor.charAt(data[0]) != ')')
		{
			parseSingleType(javaDescriptor, data);
		}
		return data[1];
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
		int dimensions = 0;
		while (type.charAt(0) == '[')
		{
			dimensions++;
			type = type.substring(1);
		}
		String returnType = "";
		switch (type.charAt(0))
		{
		case 'B':
			returnType = "byte";
			break;
		case 'C':
			returnType = "char";
			break;
		case 'D':
			returnType = "double";
			break;
		case 'F':
			returnType = "float";
			break;
		case 'I':
			returnType = "int";
			break;
		case 'J':
			returnType = "long";
			break;
		case 'S':
			returnType = "short";
			break;
		case 'Z':
			returnType = "bool";
			break;
		case 'L':
			returnType = new JavaTypeConverter(type, false).getCppType();
			break;
		case 'V':
			returnType = "void";
			break;
		default:
			Assert(false, "Unhandled type " + type);
		}
		while (dimensions > 0)
		{
			returnType += "[]";
			dimensions--;
		}
		return returnType;
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

	public static String starToPointer(String stars)
	{
		String[] parts = stars.split("[,][ ]");
		for (int i = 0; i < parts.length; i++)
		{
			String part = parts[i];
			String[] typeAndName = part.split("[ ]");
			if (typeAndName[0].endsWith("*"))
			{
				typeAndName[0] = "Pointer<" + typeAndName[0].substring(0, typeAndName[0].length() - 1) + ">";
			}
			else if (typeAndName.length > 1 && typeAndName[1].startsWith("*"))
			{
				typeAndName[0] = "Pointer<" + typeAndName[0] + ">";
				typeAndName[1] = typeAndName[1].substring(1);
			}
			part = typeAndName[0];
			if (typeAndName.length > 1)
			{
				part += " " + typeAndName[1];
			}
			parts[i] = part;
		}
		return String.join(", ", parts);
	}

	public static String starToPointerMethod(String stars)
	{
		String[] parts = stars.substring(1).split("[)]");
		String result = "(" + starToPointer(parts[0]) + ")" + starToPointer(parts[1]);
		return result;
	}

	private static void parseClass(String descriptor, int[] data)
	{
		while (descriptor.charAt(data[0]) != ';' && descriptor.charAt(data[0]) != '<')
		{
			data[0]++;
		}
		if (descriptor.charAt(data[0]) == '<')
		{
			throw new RuntimeException("Templates not supported");
//			data[0]++;
//			while (descriptor.charAt(data[0]) != '>')
//			{
//				int count = data[1];
//				parseSingleType(data);
//				data[1] = count;
//			}
//			data[0]++;
		}
		if (descriptor.charAt(data[0]) == ';')
		{
			data[0]++;
		}
	}

	private static void parseSingleType(String descriptor, int[] data)
	{
		while (descriptor.charAt(data[0]) == '[')
		{
			data[0]++;
		}
		switch (descriptor.charAt(data[0]))
		{
		case 'B':
		case 'C':
		case 'D':
		case 'F':
		case 'I':
		case 'J':
		case 'S':
		case 'Z':
			data[0]++;
			data[1]++;
			break;
		case 'L':
			data[0]++;
			data[1]++;
			parseClass(descriptor, data);
			break;
		case 'V':
			data[0]++;
			break;
		}
	}
}
