package org.borium.javarecompiler.classfile;

import org.borium.javarecompiler.classfile.attribute.*;
import org.borium.javarecompiler.classfile.instruction.*;
import org.borium.javarecompiler.cplusplus.*;

/**
 * Java VM execution context for each method. It contains variables, stack and
 * other stuff necessary for emulating bytecode execution.
 */
public class ExecutionContext
{
	public String name;

	public String type;

	public Instruction[] instructions;

	public ExecutionContext(ClassMethod javaMethod)
	{
		name = javaMethod.getName();
		type = new JavaTypeConverter(javaMethod.getDescriptor()).getCppType();
		AttributeCode code = javaMethod.getCode();
		instructions = code.getInstructions();
		// TODO Auto-generated constructor stub
	}
}
