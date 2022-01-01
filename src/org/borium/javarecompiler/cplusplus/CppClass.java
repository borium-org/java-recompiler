package org.borium.javarecompiler.cplusplus;

import java.util.*;

import org.borium.javarecompiler.classfile.*;

public class CppClass
{
	/** Java class file. */
	private ClassFile classFile;

	/** Simple class name. */
	@SuppressWarnings("unused")
	private String className;

	/** Namespace where this class is declared. */
	@SuppressWarnings("unused")
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
		// TODO Auto-generated method stub
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
}
