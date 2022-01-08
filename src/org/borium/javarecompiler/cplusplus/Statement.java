package org.borium.javarecompiler.cplusplus;

import java.util.*;

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
}
