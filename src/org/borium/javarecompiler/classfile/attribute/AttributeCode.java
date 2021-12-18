package org.borium.javarecompiler.classfile.attribute;

import java.util.*;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;
import org.borium.javarecompiler.classfile.instruction.*;

public class AttributeCode extends ClassAttribute
{
	private static class ExceptionTable
	{
		/**
		 * The values of the two items start_pc and end_pc indicate the ranges in the
		 * code array at which the exception handler is active. The value of start_pc
		 * must be a valid index into the code array of the opcode of an instruction.
		 * The value of end_pc either must be a valid index into the code array of the
		 * opcode of an instruction or must be equal to code_length, the length of the
		 * code array. The value of start_pc must be less than the value of end_pc.
		 */
		@SuppressWarnings("unused")
		int startPc;

		/**
		 * The values of the two items start_pc and end_pc indicate the ranges in the
		 * code array at which the exception handler is active. The value of start_pc
		 * must be a valid index into the code array of the opcode of an instruction.
		 * The value of end_pc either must be a valid index into the code array of the
		 * opcode of an instruction or must be equal to code_length, the length of the
		 * code array. The value of start_pc must be less than the value of end_pc.
		 */
		@SuppressWarnings("unused")
		int endPc;

		/**
		 * The value of the handler_pc item indicates the start of the exception
		 * handler. The value of the item must be a valid index into the code array and
		 * must be the index of the opcode of an instruction.
		 */
		@SuppressWarnings("unused")
		int handlerPc;

		/**
		 * If the value of the catch_type item is nonzero, it must be a valid index into
		 * the constant_pool table. The constant_pool entry at that index must be a
		 * CONSTANT_Class_info structure (4.4.1) representing a class of exceptions that
		 * this exception handler is designated to catch. The exception handler will be
		 * called only if the thrown exception is an instance of the given class or one
		 * of its subclasses.
		 */
		@SuppressWarnings("unused")
		int catchType;

		public ExceptionTable(ByteInputStream in)
		{
			startPc = in.u2();
			endPc = in.u2();
			handlerPc = in.u2();
			catchType = in.u2();
		}
	}

	private Instruction[] instructions;

	/**
	 * The value of the max_stack item gives the maximum depth of the operand stack
	 * of this method (2.6.2) at any point during execution of the method.
	 */
	@SuppressWarnings("unused")
	private int maxStack;

	/**
	 * The value of the max_locals item gives the number of local variables in the
	 * local variable array allocated upon invocation of this method (2.6.1),
	 * including the local variables used to pass parameters to the method on its
	 * invocation.
	 * <p>
	 * The greatest local variable index for a value of type long or double is
	 * max_locals - 2. The greatest local variable index for a value of any other
	 * type is max_locals - 1.
	 */
	@SuppressWarnings("unused")
	private int maxLocals;

	/**
	 * The value of the code_length item gives the number of bytes in the code array
	 * for this method.
	 * <p>
	 * The value of code_length must be greater than zero (as the code array must
	 * not be empty) and less than 65536.
	 */
	private int codeLength;

	/**
	 * The code array gives the actual bytes of Java Virtual Machine code that
	 * implement the method.
	 */
	private byte[] code;

	/**
	 * Each entry in the exception_table array describes one exception handler in
	 * the code array. The order of the handlers in the exception_table array is
	 * significant (2.10).
	 */
	private ExceptionTable[] exceptionTable;

	private HashMap<String, ClassAttribute> attributes = new HashMap<>();

	public AttributeCode(ClassAttribute attribute, ConstantPool cp)
	{
		super(attribute);
		decode(cp);
	}

	@Override
	protected void detailedDump(IndentedOutputStream stream, ConstantPool cp)
	{
		stream.indent(1);
		boolean[] labels = new boolean[code.length];
		// First instruction - create a label for start of the method
		labels[0] = true;
		for (int address = 0; address < code.length; address++)
		{
			Instruction insn = instructions[address];
			if (insn != null)
			{
				insn.addLabel(address, labels);
			}
		}
		for (int address = 0; address < code.length; address++)
		{
			if (labels[address])
			{
				stream.iprintln("L" + address + ":");
			}
			if (instructions[address] != null)
			{
				stream.indent(1);
				instructions[address].detailedDump(stream, address);
				stream.indent(-1);
			}
		}
		stream.indent(-1);
	}

	private void decode(ConstantPool cp)
	{
		ByteInputStream in = new ByteInputStream(info);
		maxStack = in.u2();
		maxLocals = in.u2();
		codeLength = in.u4();
		code = in.read(codeLength);
		int exceptionTableLength = in.u2();
		exceptionTable = new ExceptionTable[exceptionTableLength];
		for (int i = 0; i < exceptionTableLength; i++)
		{
			exceptionTable[i] = new ExceptionTable(in);
		}
		int attributeCount = in.u2();
		for (int i = 0; i < attributeCount; i++)
		{
			ClassAttribute attribute = ClassAttribute.readAttribute(in, cp);
			attributes.put(attribute.getName(), attribute);
		}
		in.close();

		instructions = new Instruction[code.length];
		ByteInputStream inCode = new ByteInputStream(code);
		int index = 0;
		while (inCode.available() > 0)
		{
			instructions[index] = Instruction.read(inCode);
//			System.out.println(instructions[index].getClass().getSimpleName());
			index += instructions[index].length();
		}
		inCode.close();
	}
}
