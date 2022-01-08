package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;

/**
 * Extend local variable index by additional bytes.
 */
public class InstructionWIDE extends Instruction
{
	private Instruction instruction;

	public InstructionWIDE(ByteInputStream in)
	{
		int opcode = in.u1();
		switch (opcode)
		{
		case ILOAD:
			instruction = new InstructionILOAD(in, true);
			break;
		case FLOAD:
			instruction = new InstructionFLOAD(in, true);
			break;
		case ALOAD:
			instruction = new InstructionALOAD(in, true);
			break;
		case LLOAD:
			instruction = new InstructionLLOAD(in, true);
			break;
		case DLOAD:
			instruction = new InstructionDLOAD(in, true);
			break;
		case ISTORE:
			instruction = new InstructionISTORE(in, true);
			break;
		case FSTORE:
			instruction = new InstructionFSTORE(in, true);
			break;
		case ASTORE:
			instruction = new InstructionASTORE(in, true);
			break;
		case LSTORE:
			instruction = new InstructionLSTORE(in, true);
			break;
		case DSTORE:
			instruction = new InstructionDSTORE(in, true);
			break;
		case RET:
			instruction = new InstructionRET(in, true);
			break;
		case IINC:
			instruction = new InstructionIINC(in, true);
			break;
		default:
			throw new ClassFormatError("WIDE opcode " + opcode + " not supported");
		}
	}

	@Override
	public int getStackDepthChange()
	{
		return instruction.getStackDepthChange();
	}

	@Override
	public int length()
	{
		return instruction.length() + 1;
	}
}
