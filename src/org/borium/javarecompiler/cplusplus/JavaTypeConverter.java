package org.borium.javarecompiler.cplusplus;

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

	public JavaTypeConverter(String javaType)
	{
		this.javaType = javaType;
		cppType = "";
		index = 0;
		dimensions = 0;
	}

	public String getCppType()
	{
		if (javaType.startsWith("("))
		{
			parseMethod();
		}
		else
		{
			parseSingleType();
		}
		return cppType;
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
				parseSingleType();
				count++;
			}
			cppType += '>';
			index++;
		}
		if (javaType.charAt(index) == ';')
		{
			cppType += '*';
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
		boolean first = true;
		while (javaType.charAt(index) != ')')
		{
			if (!first)
			{
				cppType += ", ";
			}
			first = false;
			parseSingleType();
		}
		cppType += ")";
		index++;
		parseSingleType();
	}

	private void parseSingleType()
	{
		while (javaType.charAt(index) == '[')
		{
			dimensions++;
			index++;
		}
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
			cppType += "boolean";
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
		}
		while (dimensions > 0)
		{
			cppType += "[]";
			dimensions--;
		}
	}
}
