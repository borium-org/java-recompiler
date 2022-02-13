package org.borium.javarecompiler.cplusplus;

import static org.borium.javarecompiler.Statics.*;

import java.util.*;

import org.borium.javarecompiler.*;
import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.instruction.*;

/**
 * C++ statement that consists of Java bytecode instructions. Stack starts at
 * depth 0 at the beginning of the statement, and depth should be back at 0 at
 * the end.
 */
class Statement
{
	private ArrayList<Instruction> instructions = new ArrayList<>();

	private CppExecutionContext executionContext;

	public Statement(CppExecutionContext executionContext, ArrayList<Instruction> instructions)
	{
		this.instructions.addAll(instructions);
		this.executionContext = executionContext;
	}

	/**
	 * Generate C++ source code for the statement.
	 *
	 * @param source Source output stream.
	 */
	public void generateSource(IndentedOutputStream source)
	{
		if (executionContext.hasLabel(getAddress()))
		{
			source.iprintln("L" + hexString(getAddress(), 4) + ":");
		}
		for (Instruction instruction : instructions)
		{
			if (Recompiler.instructionComments)
			{
				source.iprint("//");
				instruction.oneLineDump(source);
			}
			executionContext.generate(source, instruction);
			if (Recompiler.stackComments)
			{
				dumpStack(source, executionContext.getStack());
			}
		}
	}

	public int getAddress()
	{
		return instructions.get(0).address;
	}

	public int getInstructionCount()
	{
		return instructions.size();
	}

	public Instruction getLastInstruction()
	{
		return instructions.get(instructions.size() - 1);
	}

	public int length()
	{
		int length = 0;
		for (Instruction instruction : instructions)
		{
			length += instruction.length();
		}
		return length;
	}

	/**
	 * Dump stack contents. Comments start indented, and content has 4 tabs before
	 * the actual stack content.
	 *
	 * @param source Source file where to dump the stack.
	 * @param stack  Stack contents to dump.
	 */
	private void dumpStack(IndentedOutputStream source, Stack<String> stack)
	{
		if (stack.size() == 0)
		{
			source.iprintln("//\t\t\t\tstack: empty");
		}
		else
		{
			for (int i = 0; i < stack.size(); i++)
			{
				source.iprintln("//\t\t\t\tstack[" + i + "]: " + stack.elementAt(i));
			}
		}
	}
}
