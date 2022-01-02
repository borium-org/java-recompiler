package org.borium.javarecompiler.cplusplus;

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
	private String className;

	/** Namespace where this class is declared. */
	private String namespace;

	private CppField[] fields;

	/** Full set of namespaces for all classes referenced from this class. */
	private TreeSet<String> namespaces = new TreeSet<>();

	/** Classes whose simple names are referenced from multiple namespaces. */
	private TreeSet<String> multipleClasses = new TreeSet<>();

	/**
	 * Create the C++ class file given the Java class file.
	 *
	 * @param classFile Java class file.
	 */
	public CppClass(ClassFile classFile)
	{
		this.classFile = classFile;
		extractCppClassName();
		extractNamespaces();
		extractFields();
		// TODO Auto-generated constructor stub
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

			header.close();
			source.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Generate header file. Method is protected only for grouping so that low level
	 * private generator methods are not all over the place.
	 */
	protected void generateHeader(IndentedOutputStream header)
	{
		header.println("#ifndef " + fileName.toUpperCase());
		header.println("#define " + fileName.toUpperCase());
		header.println();

		generateHeaderIncludesAndNamespaces(header);
		generateHeaderBeginThisClassNamespace(header);
		header.println();
		header.iprintln("class " + classFile.getClassSimpleName());
		header.iprintln("{");
		header.iprintln("public:");
		header.indent(1);

		generateHeaderFields(header);
		// TODO Auto-generated method stub

		header.indent(-1);
		header.iprintln("};");
		header.println();
		generateHeaderEndThisClassNamespace(header);

		header.println();
		header.println("#endif");
	}

	/**
	 * Parse Java class name and extract namespace and class name for C++ code.
	 *
	 * @param classFile Class file to parse.
	 */
	private void extractCppClassName()
	{
		// TODO Auto-generated method stub
		String[] split = classFile.getClassName().split("[.]");
		String cppName = String.join("::", split);
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

	/**
	 * Extract namespaces for each referenced class. All namespaces are recorded,
	 * including namespaces for all used core Java packages. At same time check if
	 * any simple-named class is referenced from multiple packages, and flag simple
	 * class names as such.
	 */
	private void extractNamespaces()
	{
		HashMap<String, String> classes = new HashMap<>();
		List<String> referencedClassNames = classFile.getReferencedClasses();
		for (String referencedClassName : referencedClassNames)
		{
			int pos = referencedClassName.lastIndexOf('.');
			String packageName = referencedClassName.substring(0, pos);
			String[] split = packageName.split("[.]");
			String namespace = String.join("::", split);
			namespaces.add(namespace);

			String className = referencedClassName.substring(pos + 1);
			if (classes.containsKey(className) && !classes.containsValue(referencedClassName))
			{
				multipleClasses.add(className);
			}
			classes.put(className, referencedClassName);
		}
		namespaces.remove(namespace);
	}

	private void generateHeaderBeginThisClassNamespace(IndentedOutputStream header)
	{
		String thisClassName = classFile.getClassName();
		int pos = thisClassName.lastIndexOf('.');
		String packageName = thisClassName.substring(0, pos);
		String[] split = packageName.split("[.]");
		String namespace = String.join("::", split);
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
	}

	/**
	 * Generate includes and namespaces for each referenced class.
	 */
	private void generateHeaderIncludesAndNamespaces(IndentedOutputStream header)
	{
		List<String> referencedClassNames = classFile.getReferencedClasses();
		TreeSet<String> classes = new TreeSet<>();
		classes.addAll(referencedClassNames);
		classes.remove(classFile.getClassName());
		for (String include : classes)
		{
			String[] split = include.split("[.]");
			header.println("#include \"" + String.join("__", split) + ".h\"");
		}
		header.println();

		for (String namespace : namespaces)
		{
			header.println("using namespace " + namespace + ";");
		}
		header.println();
	}

	/**
	 * Remove redundant namespaces from each component of the type if the simple
	 * type name is unique within the class referenced types list.
	 *
	 * @param fieldType Fully qualified type.
	 * @return Simplified type.
	 */
	private String simplifyType(String fieldType)
	{
		StringBuffer sb = new StringBuffer(fieldType.length());
		int index = 0;
		StringBuffer component = new StringBuffer(fieldType.length());
		while (index < fieldType.length())
		{
			if (Character.isJavaIdentifierPart(fieldType.charAt(index)) || fieldType.charAt(index) == ':')
			{
				component.append(fieldType.charAt(index));
			}
			else
			{
				switch (fieldType.charAt(index))
				{
				case '<':
				case '>':
				case ',':
				case ' ':
					sb.append(simplifyTypeComponent(component.toString()));
					component.setLength(0);
					sb.append(fieldType.charAt(index));
					break;
				default:
					throw new RuntimeException(
							"SimplifyType: unexpected '" + fieldType.charAt(index) + "' in " + fieldType);
				}
			}
			index++;
		}
		if (component.length() > 0)
		{
			sb.append(simplifyTypeComponent(component.toString()));
		}
		return sb.toString();
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
