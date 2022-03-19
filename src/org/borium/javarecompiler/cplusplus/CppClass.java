package org.borium.javarecompiler.cplusplus;

import static org.borium.javarecompiler.Statics.*;

import java.io.*;
import java.util.*;

import org.borium.javarecompiler.classfile.*;

public class CppClass
{
	/** Java class file. */
	private ClassFile classFile;

	/** C++ file name for the class. */
	private String fileName;

	/** Simple class name. */
	String className;

	/** Namespace where this class is declared. */
	String namespace;

	/** This class fields. */
	private CppField[] fields;

	/** Full set of namespaces for all classes referenced from this class. */
	private TreeSet<String> namespaces = new TreeSet<>();

	/** Classes whose simple names are referenced from multiple namespaces. */
	private TreeSet<String> multipleClasses = new TreeSet<>();

	/** This class methods. */
	private CppMethod[] methods;

	/**
	 * Parent class name for this class. All converted classes have a parent class.
	 * Object is the only class that does not have a parent class, but Object will
	 * be provided as part of C++ library and not converted from Java to C++.
	 */
	String parentClassName;

	/**
	 * Create the C++ class file given the Java class file.
	 *
	 * @param classFile Java class file.
	 */
	public CppClass(ClassFile classFile)
	{
		this.classFile = classFile;
		extractCppClassName();
		extractParentClassName();
		extractNamespaces();
		extractFields();
		extractMethods();
	}

	public CppField getField(String fieldName)
	{
		for (CppField field : fields)
		{
			if (field.getName().equals(fieldName))
			{
				return field;
			}
		}
		Assert(false, "Field " + fieldName + " not found");
		return null;
	}

	public String getFullClassName()
	{
		return namespace + "::" + className;
	}

	/**
	 * Determine if the source type is assignable to the destination type.
	 *
	 * @param source      The type which we want to assign to destination.
	 * @param destination The type to which source is assigned.
	 * @return true if destination=source assignment is valid. Types must be equal,
	 *         or destination is Object, or source must be derived from destination.
	 */
	public boolean isAssignable(String source, String destination)
	{
		// Some types may be simplified, so simplify them both
		source = simplifyType(source);
		destination = simplifyType(destination);
		// In some cases types may have a space between type and '*'
		if (source.endsWith(" *"))
		{
			source = source.substring(0, source.length() - 2) + "*";
		}
		if (destination.endsWith(" *"))
		{
			destination = destination.substring(0, destination.length() - 2) + "*";
		}
		if (source.equals(destination))
		{
			return true;
		}
		if (destination.equals("Object*"))
		{
			return true;
		}
		if (source.equals("nullptr") && destination.endsWith("*"))
		{
			return true;
		}
		String assign = source + "=" + destination;
		switch (assign)
		{
		case "RuntimeException*=Exception*":
		case "ClassFormatError*=Exception*":
		case "ArrayList*=List*":
		case "int=char":
		case "class=Class*":
			return true;
		}
		return false;
	}

	/**
	 * Write header and source files for the C++ class. All headers and sources are
	 * in same output path, all namespaces in one folder.
	 *
	 * @param outputPath Output path where to write the files.
	 */
	public void writeClass(String outputPath)
	{
		try
		{
			fileName = namespace.replace(':', '_') + "__" + className;
			IndentedOutputStream header = new IndentedOutputStream(outputPath + '/' + fileName + ".h");
			IndentedOutputStream source = new IndentedOutputStream(outputPath + '/' + fileName + ".cpp");

			generateHeader(header);
			generateSource(source);

			header.close();
			source.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Remove redundant namespaces from each component of the type if the simple
	 * type name is unique within the class referenced types list.
	 * <p>
	 * Each field of class type is converted into a class pointer.
	 *
	 * @param className Fully qualified type.
	 * @return Simplified type.
	 */
	String simplifyType(String className)
	{
		StringBuffer sb = new StringBuffer(className.length());
		int index = 0;
		StringBuffer component = new StringBuffer(className.length());
		while (index < className.length())
		{
			if (Character.isJavaIdentifierPart(className.charAt(index)) || className.charAt(index) == ':')
			{
				component.append(className.charAt(index));
			}
			else
			{
				switch (className.charAt(index))
				{
				case '(':
				case ')':
				case '[':
				case ']':
				case '<':
				case '>':
				case ',':
				case ' ':
				case '*':
					String componentType = component.toString();
					sb.append(simplifyTypeComponent(componentType));
					component.setLength(0);
					sb.append(className.charAt(index));
					break;
				default:
					throw new RuntimeException(
							"SimplifyType: unexpected '" + className.charAt(index) + "' in " + className);
				}
			}
			index++;
		}
		if (component.length() > 0)
		{
			String componentType = component.toString();
			sb.append(simplifyTypeComponent(componentType));
		}
		return sb.toString();
	}

	/**
	 * Generate header file. Method is protected only for grouping so that low level
	 * private generator methods are not all over the place.
	 */
	protected void generateHeader(IndentedOutputStream header)
	{
		header.println("#pragma once");
		header.println();

		generateHeaderIncludesAndNamespaces(header);
		generateHeaderBeginThisClassNamespace(header);
		header.println();
		header.iprintln("class " + classFile.getClassSimpleName() + " : public " + simplifyType(parentClassName));
		header.iprintln("{");
		header.iprintln("public:");
		header.indent(1);

//		generateHeaderFields(header);
//		generateHeaderMethods(header);

		header.indent(-1);
		header.iprintln("};");
		header.println();
		generateHeaderEndThisClassNamespace(header);
	}

	protected void generateSource(IndentedOutputStream source)
	{
		source.println("#include \"stdafx.h\"");
		source.println("#include \"" + namespace.replace(':', '_') + "__" + className + ".h\"");
		source.println();
		source.println("namespace " + namespace);
		source.println("{");
		source.println();

		source.indent(1);
		generateSourceStaticFields(source);
		generateSourceMethods(source);
		source.indent(-1);

		source.println("}");
	}

	/**
	 * Parse Java class name and extract namespace and class name for C++ code.
	 *
	 * @param classFile Class file to parse.
	 */
	private void extractCppClassName()
	{
		String cppName = dotToNamespace(classFile.getClassName());
		int pos = cppName.lastIndexOf(':');
		if (pos == -1)
		{
			throw new RuntimeException("Not a fully qualified name: '" + cppName + "'");
		}
		className = cppName.substring(pos + 1);
		namespace = cppName.substring(0, pos - 1);
	}

	private void extractFields()
	{
		ClassField[] javaFields = classFile.getFields();
		fields = new CppField[javaFields.length];
		for (int i = 0; i < javaFields.length; i++)
		{
			ClassField javaField = javaFields[i];
			CppField field = new CppField(javaField);
			fields[i] = field;
		}
	}

	private void extractMethods()
	{
		ClassMethod[] javaMethods = classFile.getMethods();
		methods = new CppMethod[javaMethods.length];
		for (int i = 0; i < javaMethods.length; i++)
		{
			ClassMethod javaMethod = javaMethods[i];
			CppMethod method = new CppMethod(this, javaMethod);
			methods[i] = method;
		}
	}

	/**
	 * Extract namespaces for each referenced class. All namespaces are recorded,
	 * including namespaces for all used core Java packages. At same time check if
	 * any simple-named class is referenced from multiple packages, and flag simple
	 * class names as such.
	 */
	private void extractNamespaces()
	{
		HashMap<String, String> classes = new HashMap<>();
		ReferencedClasses referencedClassNames = classFile.getReferencedClasses();
		for (String referencedClassName : referencedClassNames)
		{
			String cppClassName = referencedClassName.replace('/', '.');
			int pos = cppClassName.lastIndexOf('.');
			String packageName = cppClassName.substring(0, pos);
			String namespace = dotToNamespace(packageName);
			namespaces.add(namespace);

			String className = cppClassName.substring(pos + 1);
			if (classes.containsKey(className) && !classes.containsValue(referencedClassName))
			{
				multipleClasses.add(className);
			}
			classes.put(className, cppClassName);
		}
		namespaces.remove(namespace);
	}

	private void extractParentClassName()
	{
		parentClassName = dotToNamespace(classFile.getParentClassName());
	}

	private void generateHeaderBeginThisClassNamespace(IndentedOutputStream header)
	{
		header.println("namespace " + namespace);
		header.println("{");
		header.indent(1);
	}

	private void generateHeaderEndThisClassNamespace(IndentedOutputStream header)
	{
		header.indent(-1);
		header.println("}");
	}

	/**
	 * Generate all class fields in the header. Enum, synthetic and transient are
	 * not supported at this time. All fields are public. Default, protected and
	 * private access modes are ignored.
	 */
	private void generateHeaderFields(IndentedOutputStream header)
	{
		for (CppField field : fields)
		{
			String fieldType = field.getType();
			String newType = simplifyType(fieldType);
			field.generateHeader(header, newType);
		}
		header.println();
	}

	/**
	 * Generate includes and namespaces for each referenced class.
	 */
	private void generateHeaderIncludesAndNamespaces(IndentedOutputStream header)
	{
		String superClassName = classFile.getParentClassName();
		header.println("#include \"" + dotToNamespace(superClassName).replace(':', '_') + ".h\"");
		header.println();

		ReferencedClasses referencedClassNames = classFile.getReferencedClasses();
		for (String reference : referencedClassNames)
		{
			String template = "";
			reference = dotToNamespace(reference.replace('/', '.'));
			int pos = reference.indexOf('<');
			if (pos >= 0)
			{
				int templateCount = Integer.parseInt(reference.substring(pos + 1, reference.length() - 1));
				reference = reference.substring(0, pos);
				template = "template <";
				for (int i = 0; i < templateCount; i++)
				{
					template += i > 0 ? ", " : "";
					template += "class " + (char) ('A' + i);
				}
				template += "> ";
			}
			header.println(template + "class " + reference + ";");
		}
		header.println();

		for (String packageName : namespaces)
		{
			header.println("using namespace " + packageName + ";");
		}
		header.println();
	}

	private void generateHeaderMethods(IndentedOutputStream header)
	{
		for (CppMethod method : methods)
		{
			String methodType = method.getType();
			String newType = simplifyType(methodType);
			String methodName = method.getName();
			if (methodName.equals("<init>"))
			{
				methodName = className;
				newType = newType.substring(0, newType.length() - 4);
			}
			else if (methodName.equals("<clinit>"))
			{
				methodName = "ClassInit";
			}
			method.generateHeader(header, methodName, newType);
		}
	}

	private void generateSourceMethods(IndentedOutputStream source)
	{
		for (CppMethod method : methods)
		{
			String methodType = method.getType();
			methodType = simplifyType(methodType);
			String methodName = method.getName();
			if (methodName.equals("<init>"))
			{
				methodName = className;
				methodType = methodType.substring(0, methodType.indexOf(')') + 1);
			}
			else if (methodName.equals("<clinit>"))
			{
				methodName = "ClassInit";
			}
			method.generateSource(source, className + "::" + methodName, methodType, fields);
		}
	}

	private void generateSourceStaticFields(IndentedOutputStream source)
	{
		for (CppField field : fields)
		{
			if (field.isStatic() && !field.isFinal())
			{
				// TODO initializers if any
				source.iprintln(field.getType() + " " + className + "::" + field.getName() + ";");
				source.println();
			}
		}
	}

	/**
	 * Simplify a fully qualified component of the type. Templates and other complex
	 * types can have multiple components, so this method is invoked for each
	 * component separately.
	 *
	 * @param fullyQualifiedType Fully qualified type component. This can also be a
	 *                           string corresponding to primitive types.
	 * @return Simplified type, or the fully qualified type if the simple type in
	 *         that component is not unique.
	 */
	private String simplifyTypeComponent(String fullyQualifiedType)
	{
		int pos = fullyQualifiedType.lastIndexOf(':');
		if (pos == -1)
		{
			return fullyQualifiedType;
		}
		String simpleType = fullyQualifiedType.substring(pos + 1);
		if (!multipleClasses.contains(simpleType))
		{
			return simpleType;
		}
		return fullyQualifiedType;
	}
}
