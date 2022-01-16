package org.borium.javarecompiler.classfile;

import static org.borium.javarecompiler.Statics.*;

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
	 * Parameter and local variable structure.
	 * <p>
	 * Variables of multiple types could occupy same slot. Optimizer may want to
	 * save local variable space and store multiple variables with different active
	 * usage ranges into same spot, who knows. Therefore, instead of simple
	 * type/name pair we are using a map <type, name>. Type is extracted from
	 * constant pool information, and then name is extracted from the map of this
	 * instruction. Usage range for each variable in the table is assumed to be
	 * non-overlapping, Java compiler is expected to produce valid bytecode for us
	 * to work with.
	 */
	protected static class LocalVariable
	{
		/** Map to associate type and name. */
		private HashMap<String, String> local = new HashMap<>();

		public void set(String cppType, String variable)
		{
			Assert(local.size() == 0, "set(): Locals map must be empty");
			local.put(cppType, variable);
		}
	}

	/** Java method name. */
	public String name;

	/** Java method type, in Java conventions. */
	public String type;

	/** All instructions in the method, with nulls after multi-byte instructions. */
	public Instruction[] instructions;

	/** Maximum number of locals and parameters. */
	private int maxLocals;

	protected LocalVariable[] locals;

	protected ExecutionContext(ClassMethod javaMethod)
	{
		name = javaMethod.getName();
		type = javaMethod.getDescriptor();
		AttributeCode code = javaMethod.getCode();
		instructions = code.getInstructions();
		maxLocals = code.getLocalsCount();
		locals = new LocalVariable[maxLocals];
	}
}
