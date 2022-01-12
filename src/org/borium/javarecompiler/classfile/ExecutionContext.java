package org.borium.javarecompiler.classfile;

import org.borium.javarecompiler.classfile.attribute.*;
import org.borium.javarecompiler.classfile.instruction.*;

/**
 * Java VM execution context for each method. It contains variables, stack and
 * other stuff necessary for emulating bytecode execution.
 */
public class ExecutionContext
{
	/** Java method name. */
	public String name;

	/** Java method type, in Java conventions. */
	public String type;

	/** All instructions in the method, with nulls after multi-byte instructions. */
	public Instruction[] instructions;

	/** Maximum number of locals and parameters. */
	@SuppressWarnings("unused")
	private int maxLocals;

	protected ExecutionContext(ClassMethod javaMethod)
	{
		name = javaMethod.getName();
		type = javaMethod.getDescriptor();
		AttributeCode code = javaMethod.getCode();
		instructions = code.getInstructions();
		maxLocals = code.getLocalsCount();
	}
}
