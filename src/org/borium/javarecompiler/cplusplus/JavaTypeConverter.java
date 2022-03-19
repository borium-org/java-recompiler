package org.borium.javarecompiler.cplusplus;

import static org.borium.javarecompiler.Statics.*;

import java.util.*;

/**
 * Class to convert from Java types to C++ types. All C++ classes are fully
 * qualified in here, and they will have to be converted to simple class names
 * based on 'using namespace' directives elsewhere.
 */
public class JavaTypeConverter
{
	/** Input Java type. */
	String javaType;

	/** Output C++ type. */
	String cppType;

	/** String index in Java type for parsing. */
	int index;

	int dimensions;

	private int parameterIndex;

	private LocalVariables locals;

	public JavaTypeConverter(String javaType, boolean isStatic)
	{
		this(javaType, isStatic, null);
	}

	public JavaTypeConverter(String javaType, boolean isStatic, LocalVariables locals)
	{
		this.javaType = javaType;
		cppType = "";
		index = 0;
		dimensions = 0;
		parameterIndex = isStatic ? 0 : 1;
		this.locals = locals;
	}

	public String getCppType()
	{
		if (javaType.startsWith("("))
		{
			parseMethod();
		}
		else
		{
			parseSingleType(false);
		}
		return cppType;
	}

	public String[] parseParameterTypes()
	{
		Assert(javaType.startsWith("("), "Need a method signature");
		ArrayList<String> parameterTypes = new ArrayList<>();
		index++;

		while (javaType.charAt(index) != ')')
		{
			cppType = "";
			parseSingleType(false);
			parameterTypes.add(cppType);
		}
		return parameterTypes.toArray(new String[parameterTypes.size()]);
	}

	private void parseClass()
	{
		while (javaType.charAt(index) != ';' && javaType.charAt(index) != '<')
		{
			cppType += javaType.charAt(index) == '/' ? "::" : javaType.charAt(index);
			index++;
		}
		if (javaType.charAt(index) == '<')
		{
			cppType += '<';
			index++;
			int count = 0;
			while (javaType.charAt(index) != '>')
			{
				if (count > 0)
				{
					cppType += ", ";
				}
				parseSingleType(false);
				count++;
			}
			cppType += '>';
			index++;
		}
		if (javaType.charAt(index) == ';')
		{
			index++;
		}
	}

	/**
	 * Parse a method type. It is already verified that Java type starts with
	 * opening parenthesis. Parenthesis is not extracted yet.
	 */
	private void parseMethod()
	{
		cppType = "(";
		index++;
		String separator = "";
		while (javaType.charAt(index) != ')')
		{
			cppType += separator;
			separator = ", ";
			parseSingleType(true);
		}
		cppType += ")";
		index++;
		parseSingleType(false);
	}

	/**
	 * Parse a single type starting with javaType[index]. The type (and parameter if
	 * allowed) is appended to cppType field.
	 *
	 * @param addParameter If true, the 'paramX' string is added to the type, where
	 *                     'X' is the parameter number in the method signature. X
	 *                     starts with 1 for regular methods and with 0 for statics.
	 */
	private void parseSingleType(boolean addParameter)
	{
		while (javaType.charAt(index) == '[')
		{
			dimensions++;
			index++;
		}
		int oldPos = cppType.length();
		switch (javaType.charAt(index))
		{
		case 'B':
			cppType += "byte";
			index++;
			break;
		case 'C':
			cppType += "char";
			index++;
			break;
		case 'D':
			cppType += "double";
			index++;
			break;
		case 'F':
			cppType += "float";
			index++;
			break;
		case 'I':
			cppType += "int";
			index++;
			break;
		case 'J':
			cppType += "long";
			index++;
			break;
		case 'S':
			cppType += "short";
			index++;
			break;
		case 'Z':
			cppType += "bool";
			index++;
			break;
		case 'L':
			index++;
			parseClass();
			break;
		case 'V':
			cppType += "void";
			index++;
			break;
		default:
			Assert(false, "Unhandled type " + javaType.charAt(index));
		}
		while (dimensions > 0)
		{
			cppType = cppType.substring(0, oldPos) + "JavaArray<" + cppType.substring(oldPos) + ">*";
			dimensions--;
		}
		if (addParameter)
		{
			if (cppType.endsWith("*"))
			{
				cppType = cppType.substring(0, cppType.length() - 1);
				cppType += " *";
			}
			else
			{
				cppType += " ";
			}
			cppType += locals != null ? locals.get(parameterIndex, null).getName() : "param" + parameterIndex;
			parameterIndex++;
		}
	}
}
