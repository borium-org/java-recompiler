package org.borium.javarecompiler.classfile;

import java.util.*;

import org.borium.javarecompiler.classfile.attribute.*;
import org.borium.javarecompiler.classfile.instruction.*;

/**
 * Java VM execution context for each method. It contains variables, stack and
 * other stuff necessary for emulating bytecode execution.
 */
public class ExecutionContext
{
	/**
	 * Separator between stack entry type and value. The character must not be
	 * present in the expression for the value of the stack entry. '=' is a bad
	 * choice because it can be present in the condition part of a ternary operator.
	 */
	protected static final String StackEntrySeparator = "#";

	/**
	 * Since String.split() expects a regular expression, this separator is the
	 * version to use for splitting stack entries.
	 */
	protected static final String SplitStackEntrySeparator = "[" + StackEntrySeparator + "]";

//	/**
//	 * Separator for multiple locals of different types in same slot. Using '&' as
//	 * in 'and there is more...'
//	 */
//	private static final String localSeparator = "&";

	/** Java method name. */
	public String name;

	/** Java method type, in Java conventions. */
	public String type;

	/** All instructions in the method, with nulls after multi-byte instructions. */
	public Instruction[] instructions;

	/** Maximum number of locals and parameters. */
	protected int maxLocals;

	protected Stack<String> stack = new Stack<>();

	/** True for each instruction that is referenced by a goto of some kind. */
	protected boolean[] labels;

	protected ExecutionContext(ClassMethod javaMethod)
	{
		name = javaMethod.getName();
		type = javaMethod.getDescriptor();
		AttributeCode code = javaMethod.getCode();
		if (!javaMethod.isAbstract())
		{
			instructions = code.getInstructions();
			maxLocals = code.getLocalsCount();
			labels = code.getLabels();
		}
	}

	public Stack<String> getStack()
	{
		return stack;
	}
}
