package org.borium.javarecompiler.cplusplus;

import org.borium.javarecompiler.classfile.*;

public class CppClass
{
	/** Simple class name. */
	@SuppressWarnings("unused")
	private String className;

	/** Namespace where this class is declared. */
	@SuppressWarnings("unused")
	private String namespace;

	/**
	 * Create the C++ class file given the Java class vile.
	 *
	 * @param classFile Java class file.
	 */
	public CppClass(ClassFile classFile)
	{
		extractCppClassName(classFile);
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
	private void extractCppClassName(ClassFile classFile)
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
}
