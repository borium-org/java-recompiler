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

	public Statement(ArrayList<Instruction> instructions)
	{
		this.instructions.addAll(instructions);
	}

	public void generateComments(IndentedOutputStream source)
	{
		Stack<String> stack = new Stack<>();

		source.iprint("// L");
		source.printHex(instructions.get(0).getAddress(), 4);
		source.println(":");
		for (Instruction instruction : instructions)
		{
			source.iprint("// ");
			instruction.oneLineDump(source);
			source.indent(1);
			dumpStack(source, stack);
			source.indent(-1);
		}
		// TODO Auto-generated method stub
	}

	private void dumpStack(IndentedOutputStream source, Stack<String> stack)
	{
		for (int i = 0; i < stack.size(); i++)
		{
			source.iprintln("stack[" + i + "]=" + stack.elementAt(i));
		}
	}
}
