package org.borium.javarecompiler.classfile.instruction;

/**
 * Duplicate the top one or two operand stack values and insert two, three, or
 * four values down.
 */
public class InstructionDUP2_X2 extends Instruction
{
	public InstructionDUP2_X2()
	{
	}

	@Override
	public int getStackDepthChange()
	{
		String name = getClass().getSimpleName().substring(11);
		throw new RuntimeException(name + " stack depth change not implemented");
	}
}
