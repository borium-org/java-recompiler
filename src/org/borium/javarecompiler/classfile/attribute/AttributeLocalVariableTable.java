package org.borium.javarecompiler.classfile.attribute;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

public class AttributeLocalVariableTable extends ClassAttribute
{
	public static class VariableTableEntry
	{
		/** Start PC defines where in the code the variable becomes active. */
		public int startPc;

		/**
		 * Length defines the offset from startPc where the variable is no longer
		 * active.
		 */
		public int length;

		/** Name index in constant pool. */
		private int nameIndex;

		/** Descriptor index in the constant pool. */
		private int descriptorIndex;

		/**
		 * Index in the local variable table where this variable is stored. Same slot
		 * can be occupied by different variables if their PC ranges do not overlap.
		 */
		public int index;

		/** Variable name. */
		public String name;

		/** Variable descriptor. */
		public String descriptor;

		public VariableTableEntry(ByteInputStream in, ConstantPool cp)
		{
			startPc = in.u2();
			length = in.u2();
			nameIndex = in.u2();
			name = cp.getString(nameIndex);
			descriptorIndex = in.u2();
			descriptor = cp.getString(descriptorIndex);
			index = in.u2();
		}

		public void dump(IndentedOutputStream stream)
		{
			stream.println(name + ": " + descriptor);
			stream.indent(1);
			stream.iprint("Start PC: ");
			stream.printHex(startPc, 4);
			stream.print(", End PC: ");
			stream.printHex(startPc + length, 4);
			stream.print(", Index: " + index);
			stream.indent(-1);
		}
	}

	/** All the variables in this table. */
	private VariableTableEntry[] variableTable;

	public AttributeLocalVariableTable(ClassAttribute attribute, ConstantPool cp)
	{
		super(attribute);
		decode(cp);
	}

	public VariableTableEntry[] getVariableTable()
	{
		return variableTable;
	}

	@Override
	protected void detailedDump(IndentedOutputStream stream)
	{
		stream.iprintln("Local Variables: " + variableTable.length);
		stream.indent(1);
		for (int i = 0; i < variableTable.length; i++)
		{
			stream.iprint(i + ": ");
			variableTable[i].dump(stream);
			stream.println();
		}
		stream.indent(-1);
	}

	private void decode(ConstantPool cp)
	{
		ByteInputStream in = new ByteInputStream(info);
		int variableTableLength = in.u2();
		variableTable = new VariableTableEntry[variableTableLength];
		for (int i = 0; i < variableTableLength; i++)
		{
			variableTable[i] = new VariableTableEntry(in, cp);
		}
		in.close();
	}
}
