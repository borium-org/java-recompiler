package org.borium.javarecompiler.classfile;

import static org.borium.javarecompiler.Statics.*;

import java.util.*;

/**
 * Special magic class to store referenced classes. It is iterable so loops over
 * it work just the same. All classes being added are parsed for template
 * parameters, so each template can potentially add multiple references.
 */
public class ReferencedClasses implements Iterable<String>
{
	private TreeSet<String> referencedClasses = new TreeSet<>();

	/**
	 * Add a type as a referenced type. Only class types are added, and template
	 * types are simplified or split into multiple classes where each template
	 * argument is added separately.
	 *
	 * @param type Java type or signature where types are not erased.
	 */
	public void add(String type)
	{
		while (type.length() > 0)
		{
			char prefix = type.charAt(0);
			type = type.substring(1);
			switch (prefix)
			{
			case 'B': // byte
			case 'C': // char
			case 'D': // double
			case 'F': // float
			case 'I': // int
			case 'J': // long
			case 'S': // short
			case 'Z': // bool
			case 'V': // void
			case '(': // (), ignore
			case ')': // (), ignore
			case '[': // array, ignore
				break;
			case 'L':
				type = addClass(type);
				break;
			default:
				throw new RuntimeException("Bad prefix '" + prefix + "'");
			}
		}
	}

	@Override
	public Iterator<String> iterator()
	{
		return referencedClasses.iterator();
	}

	public void removeClass(String className)
	{
		referencedClasses.remove(className.replace('.', '/'));
	}

	private String addClass(String type)
	{
		String className = "";
		while (type.charAt(0) != ';' && type.charAt(0) != '<')
		{
			className += type.charAt(0);
			type = type.substring(1);
		}
		if (type.charAt(0) == '<')
		{
			type = type.substring(1);
			int parameterCount = 0;
			while (type.charAt(0) == 'L')
			{
				type = type.substring(1);
				type = addClass(type);
				parameterCount++;
			}
			className += "<" + parameterCount + ">";
			insert(className);
			Assert(type.charAt(0) == '>', "Template terminator expected");
			type = type.substring(1);
			Assert(type.charAt(0) == ';', "Class terminator expected");
			type = type.substring(1);
		}
		else if (type.charAt(0) == ';')
		{
			type = type.substring(1);
			insert(className);
		}
		return type;
	}

	/**
	 * Insert new class, but check if name is duplicate first. Duplicates in this
	 * context are template classes that can be with or without template parameter
	 * counts. Template classes with parameters are inserted into referenced classes
	 * collection when header is generated, due to signatures being available.
	 * Type-erased template classes are inserted into referenced classes collection
	 * at the time when source is generated, and there signatures are not always
	 * available.
	 *
	 * @param className Class name to check and insert.
	 */
	private void insert(String className)
	{
		// Exact match? Say we already have it
		if (referencedClasses.contains(className))
		{
			return;
		}
		// Check if new class is template and old one isn't
		if (className.contains("<"))
		{
			String rawTemplate = className.substring(0, className.indexOf('<'));
			referencedClasses.remove(rawTemplate);
			referencedClasses.add(className);
			return;
		}
		// New class may be a raw version of the template class that we already have
		String template = className + "<";
		for (String clazz : referencedClasses)
		{
			if (clazz.startsWith(template))
			{
				return;
			}
		}
		// Not a match
		referencedClasses.add(className);
	}
}
