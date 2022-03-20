package org.borium.javarecompiler.classfile.attribute;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

public class AttributeLocalVariableTypeTable extends ClassAttribute
{
	private static class LocalVariableType
	{
		/** Start PC where the variable is active. */
		@SuppressWarnings("unused")
		int startPc;
		/** Length of code starting from start PC where the variable is active. */
		@SuppressWarnings("unused")
		int length;
		/** Valid unqualified name of the local variable. */
		@SuppressWarnings("unused")
		int nameIndex;
		/** The signature of the local variable. */
		@SuppressWarnings("unused")
		int signatureIndex;
		/** Local variable array index of the local variable. */
		@SuppressWarnings("unused")
		int index;
		private String name;
		private String signature;

		public LocalVariableType(ByteInputStream in, ConstantPool cp)
		{
			startPc = in.u2();
			length = in.u2();
			nameIndex = in.u2();
			signatureIndex = in.u2();
			index = in.u2();

			name = cp.getString(nameIndex);
			signature = cp.getString(signatureIndex);
		}

		public void dump(IndentedOutputStream stream)
		{
			stream.println(name + ": " + signature);
			stream.indent(1);
			stream.iprint("Start PC: ");
			stream.printHex(startPc, 4);
			stream.print(", End PC: ");
			stream.printHex(startPc + length, 4);
			stream.print(", Index: " + index);
			stream.indent(-1);
		}
	}

	private LocalVariableType[] localVariableTypes;

	public AttributeLocalVariableTypeTable(ClassAttribute attribute, ConstantPool cp)
	{
		super(attribute);
		decode(cp);
	}

	@Override
	protected void detailedDump(IndentedOutputStream stream)
	{
		stream.iprintln("Local Variable Types: " + localVariableTypes.length);
		stream.indent(1);
		for (int i = 0; i < localVariableTypes.length; i++)
		{
			stream.iprint(i + ": ");
			localVariableTypes[i].dump(stream);
			stream.println();
		}
		stream.indent(-1);
	}

	private void decode(ConstantPool cp)
	{
		ByteInputStream in = new ByteInputStream(info);
		int numberOfEntries = in.u2();
		localVariableTypes = new LocalVariableType[numberOfEntries];
		for (int i = 0; i < numberOfEntries; i++)
		{
			localVariableTypes[i] = new LocalVariableType(in, cp);
		}
		in.close();
	}
}
