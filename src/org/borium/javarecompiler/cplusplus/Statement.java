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

	public void dumpInstructions(IndentedOutputStream trace)
	{
		trace.println("// Statement " + hexString(getAddress(), 4));
		generateLabel(trace);
		for (Instruction instruction : instructions)
		{
			trace.iprint("//\t");
			instruction.oneLineDump(trace);
		}
	}

	public void generateLabel(IndentedOutputStream source)
	{
		if (executionContext.hasLabel(getAddress()))
		{
			source.indent(-1);
			source.iprintln("L" + hexString(getAddress(), 4) + ": //");
			source.indent(1);
		}
	}

	/**
	 * Generate C++ source code for the statement.
	 *
	 * @param source     Source output stream.
	 * @param allowLabel If false, label will be suppressed. This is necessary so
	 *                   that first statement in try block has its label generated
	 *                   separately outside of the try block.
	 */
	public void generateSource(IndentedOutputStream source, boolean allowLabel)
	{
		if (allowLabel)
		{
			generateLabel(source);
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

	public ArrayList<Instruction> getInstructions()
	{
		return instructions;
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
	 * Merge all instructions from next statement into this statement.
	 *
	 * @param nextStatement The statement to merge.
	 */
	public void merge(Statement nextStatement)
	{
		Assert(getAddress() + length() == nextStatement.getAddress(), "Merge: Statements not in sequence");
		instructions.addAll(nextStatement.instructions);
	}

	/**
	 * Create new instruction that has the last instruction of the current
	 * statement. Last instruction is removed from current statement. The method is
	 * useful for splitting GOTO/ASTORE instruction pair at the end of try block
	 * into separate statements so that catch block can start with its own
	 * statement. Instruction types are not verified here, caller must make sure
	 * that this method does what it needs to do.
	 *
	 * @return New statement containing last instruction of this statement.
	 */
	public Statement splitLastInstruction()
	{
		ArrayList<Instruction> newInstructions = new ArrayList<>();
		newInstructions.add(instructions.get(instructions.size() - 1));
		instructions.remove(instructions.size() - 1);
		Statement statement = new Statement(executionContext, newInstructions);
		return statement;
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
