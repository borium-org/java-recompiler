package org.borium.javarecompiler.classfile.instruction;

/**
 * Duplicate the top one or two operand stack values and insert two or three
 * values down.
 */
public class InstructionDUP2_X1 extends Instruction
{
	public InstructionDUP2_X1()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		String name = getClass().getSimpleName().substring(11);
		throw new RuntimeException(name + " stack depth change not implemented");
	}
}
