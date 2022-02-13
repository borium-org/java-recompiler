package org.borium.javarecompiler.cplusplus;

import static org.borium.javarecompiler.Statics.*;

import java.util.*;

import org.borium.javarecompiler.classfile.attribute.AttributeLocalVariableTable.*;
import org.borium.javarecompiler.classfile.instruction.*;

class LocalVariables
{
	static class LocalVariable
	{
		/**
		 * Start PC defines where in the code the variable becomes active. StartPc is
		 * adjusted for the length of xStore instruction if necessary.
		 */
		private int startPc;

		/** End PC defines where in the code the variable becomes inactive. */
		private int endPc;

		/** Variable name. */
		private String name;

		/** Variable type. */
		private String type;

		/**
		 * Index in the local variable table where this variable is stored. Same slot
		 * can be occupied by different variables if their PC ranges do not overlap.
		 */
		private int index;

		public LocalVariable(int index, String type, int address)
		{
			this.index = index;
			this.type = type;
			startPc = address;
			endPc = 0xFFFF;
			name = "local_" + hexString(address, 4);
		}

		/**
		 * Create new local variable entry. Most fields are copied from the local
		 * variable table entry, except for constant pool indices. Type is converted to
		 * C++ and simplified.
		 *
		 * @param entry    LocalVariableTable entry.
		 * @param cppClass
		 */
		private LocalVariable(VariableTableEntry entry, CppClass cppClass)
		{
			startPc = entry.startPc;
			endPc = startPc + entry.length;
			name = entry.name;
			type = entry.descriptor;
			type = new JavaTypeConverter(type, true).getCppType();
			type = cppClass.simplifyType(type);
			index = entry.index;
		}

		public String getName()
		{
			return name;
		}

		public String getType()
		{
			return type;
		}
	}

	/** Local variables table as seen in the Code's LocalVariableTable attribute. */
	private ArrayList<LocalVariable> localVariableTable = new ArrayList<>();

	LocalVariables(VariableTableEntry[] localVariableTable, CppClass cppClass)
	{
		for (VariableTableEntry entry : localVariableTable)
		{
			this.localVariableTable.add(new LocalVariable(entry, cppClass));
		}
	}

	/**
	 * Get a local variable at specified index and PC location.
	 *
	 * @param index       Local variable index in the locals table.
	 * @param instruction Instruction which is used to determine the variable scope.
	 *                    Instruction is used instead of its address to keep integer
	 *                    index parameter apart from integer address.
	 * @return LocalVariable structure with all the variable info, or null if not
	 *         found.
	 */
	public LocalVariable get(int index, Instruction instruction)
	{
		int address = instruction != null ? instruction.address : 0;
		// Somewhat counter-intuitive: xSTORE assigns the variable but the validity
		// range starts with an instruction past that xSTORE. We need to adjust start PC
		// accordingly. End PC does not have to change.
		if (instruction != null && instruction.isXStore())
		{
			address += instruction.length();
		}
		for (LocalVariable entry : localVariableTable)
		{
			if (entry.index == index && address >= entry.startPc && address < entry.endPc)
			{
				return entry;
			}
		}
		return null;
	}

	/**
	 * Get a variable at specific index and start PC. This variable is an exception
	 * parameter to the catch block.
	 *
	 * @param index   Variable index in local variable table.
	 * @param address Address of the catch handler where the variable is activated.
	 * @return Local variable entry or null if not found.
	 */
	public LocalVariable get(int index, int address)
	{
		for (LocalVariable entry : localVariableTable)
		{
			if (entry.index == index && address >= entry.startPc && address < entry.endPc)
			{
				return entry;
			}
		}
		return null;
	}

	public LocalVariable set(int index, String type, int address)
	{
		LocalVariable local = new LocalVariable(index, type, address);
		localVariableTable.add(local);
		return local;
	}

//	/**
//	 * Create a compiler-generated temporary local variable that is not listed in
//	 * LocalVariableTable attribute.
//	 *
//	 * @param index Local variable index.
//	 * @param local Local variable type and name.
//	 */
//	public void set(int index, String[] local)
//	{
//		VariableTableEntry entry = new VariableTableEntry(index, local[1], local[0]);
//		localVariableTable.add(entry);
//	}
}
