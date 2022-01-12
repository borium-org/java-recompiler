package org.borium.javarecompiler.cplusplus;

import org.borium.javarecompiler.classfile.*;

/**
 * C++-specific version of the execution context.
 */
public class CppExecutionContext extends ExecutionContext
{
	/** C++ equivalent of method type. */
	@SuppressWarnings("unused")
	private String cppType;

	protected CppExecutionContext(ClassMethod javaMethod)
	{
		super(javaMethod);
		cppType = new JavaTypeConverter(type).getCppType();
		// TODO Auto-generated constructor stub
	}
}
