package org.borium.javarecompiler.classfile;

import static org.borium.javarecompiler.Statics.*;

import java.util.*;
import java.util.Map.*;

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

		/**
		 * Make a single string for the entry that contains one or more local variable
		 * options.
		 *
		 * @return String that has at least one option for the parameter or local
		 *         variable.
		 */
		public String getEntry()
		{
			String entryString = "";
			String separator = "";
			for (Entry<String, String> entry : local.entrySet())
			{
				entryString += entry.getKey() + typeAndNameSeparator + entry.getValue() + separator;
				separator = localSeparator;
			}
			Assert(entryString.length() > 0, "No local variable definition");
			return entryString;
		}

		/**
		 * Push this local (or multiple locals) to the execution stack. Multiple locals
		 * are pushed into same stack slot, separated by '&'. The instruction that uses
		 * the stack entry has an idea of which type the entry should be, so it can
		 * access the right one by separating options and finding the match.
		 *
		 * @param stack Operand stack, multiple locals can be pushed into single slot.
		 */
		public void push(Stack<String> stack)
		{
			stack.push(getEntry());
		}

		public void set(String cppType, String variable)
		{
			Assert(local.size() == 0, "set(): Locals map must be empty");
			local.put(cppType, variable);
		}
	}

	/**
	 * Type and name separator in the stack entry. It must be different from any
	 * character that could appear in the type, including commas, templates,
	 * pointers, etc. We're using '-' here.
	 */
	private static final String typeAndNameSeparator = "-";

	/**
	 * Separator for multiple locals of different types in same slot. Using '&' as
	 * in 'and there is more...'
	 */
	private static final String localSeparator = "&";

	/** Java method name. */
	public String name;

	/** Java method type, in Java conventions. */
	public String type;

	/** All instructions in the method, with nulls after multi-byte instructions. */
	public Instruction[] instructions;

	/** Maximum number of locals and parameters. */
	protected int maxLocals;

	protected LocalVariable[] locals;

	protected Stack<String> stack = new Stack<>();

	protected ExecutionContext(ClassMethod javaMethod)
	{
		name = javaMethod.getName();
		type = javaMethod.getDescriptor();
		AttributeCode code = javaMethod.getCode();
		instructions = code.getInstructions();
		maxLocals = code.getLocalsCount();
		locals = new LocalVariable[maxLocals];
		for (int i = 0; i < maxLocals; i++)
		{
			locals[i] = new LocalVariable();
		}
	}

	public Stack<String> getStack()
	{
		return stack;
	}
}
