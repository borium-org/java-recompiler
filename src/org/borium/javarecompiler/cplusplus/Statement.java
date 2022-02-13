package org.borium.javarecompiler.cplusplus;

import java.util.*;

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
	 * @param source   Source output stream.
	 * @param addLabel True if need to add label, false if statement is in this
	 *                 class constructor to invoke base class constructor.
	 */
	public void generateSource(IndentedOutputStream source, boolean addLabel)
	{
		source.iprint(addLabel ? "" : "// ");
		source.print("L");
		source.printHex(getAddress(), 4);
		source.println(":");
		for (Instruction instruction : instructions)
		{
			source.iprint("//");
			instruction.oneLineDump(source);
			executionContext.generate(source, instruction);
			dumpStack(source, executionContext.getStack());
		}
		// TODO Auto-generated method stub
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
