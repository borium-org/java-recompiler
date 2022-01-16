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

	public void generateSource(IndentedOutputStream source)
	{
		source.iprint("// L");
		source.printHex(instructions.get(0).getAddress(), 4);
		source.println(":");
		for (Instruction instruction : instructions)
		{
			source.iprint("// ");
			instruction.oneLineDump(source);
			executionContext.execute(instruction);
			dumpStack(source, executionContext.getStack());
		}
		// TODO Auto-generated method stub
	}

	private void dumpStack(IndentedOutputStream source, Stack<String> stack)
	{
		source.indent(1);
		for (int i = 0; i < stack.size(); i++)
		{
			source.iprintln("// stack[" + i + "]=" + stack.elementAt(i));
		}
		source.indent(-1);
	}
}
