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

	public JavaTypeConverter(String javaType)
	{
		this.javaType = javaType;
		cppType = "";
		index = 0;
	}

	public String getCppType()
	{
		parse();
		return cppType;
	}

	private void parse()
	{
		switch (javaType.charAt(index))
		{
		case 'B':
			cppType = "byte";
			index++;
			break;
		case 'C':
			cppType = "char";
			index++;
			break;
		case 'D':
			cppType = "double";
			index++;
			break;
		case 'F':
			cppType = "float";
			index++;
			break;
		case 'I':
			cppType = "int";
			index++;
			break;
		case 'J':
			cppType = "long";
			index++;
			break;
		case 'S':
			cppType = "short";
			index++;
			break;
		case 'Z':
			cppType = "boolean";
			index++;
			break;
		case 'L':
			index++;
			parseClass();
			break;
		}
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
				parse();
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
}
