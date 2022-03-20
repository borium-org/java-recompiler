package org.borium.javarecompiler.classfile.attribute;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

public class AttributeStackMapTable extends ClassAttribute
{
	private static class StackMapFrame
	{
		/** Stack map entry tag (0...255). */
		private int frameType;
		@SuppressWarnings("unused")
		private int offsetDelta;
		private VerificationTypeInfo[] stack;
		private VerificationTypeInfo[] locals;

		public StackMapFrame(ByteInputStream in)
		{
			frameType = in.u1();
			if (frameType >= 0 && frameType <= 63) // SAME (0...63)
			{
				offsetDelta = frameType;
			}
			else if (frameType >= 64 && frameType <= 127)// SAME_LOCALS_1_STACK_ITEM (64...127)
			{
				offsetDelta = frameType - 64;
				stack = new VerificationTypeInfo[1];
				stack[0] = new VerificationTypeInfo(in);
			}
			else if (frameType == 247) // SAME_LOCALS_1_STACK_ITEM_EXTENDED (247)
			{
				offsetDelta = in.u2();
				stack = new VerificationTypeInfo[1];
				stack[0] = new VerificationTypeInfo(in);
			}
			else if (frameType >= 248 && frameType <= 250) // CHOP (248...250)
			{
				offsetDelta = in.u2();
			}
			else if (frameType == 251) // = SAME_FRAME_EXTENDED (251)
			{
				offsetDelta = in.u2();
			}
			else if (frameType >= 252 && frameType <= 254) // APPEND (252...254)
			{
				offsetDelta = in.u2();
				locals = new VerificationTypeInfo[frameType - 251];
				for (int i = 0; i < locals.length; i++)
				{
					locals[i] = new VerificationTypeInfo(in);
				}
			}
			else if (frameType == 255) // FULL_FRAME (255)
			{
				offsetDelta = in.u2();
				int numberOfLocals = in.u2();
				locals = new VerificationTypeInfo[numberOfLocals];
				for (int i = 0; i < numberOfLocals; i++)
				{
					locals[i] = new VerificationTypeInfo(in);
				}
				int numberOfStackItems = in.u2();
				stack = new VerificationTypeInfo[numberOfStackItems];
				for (int i = 0; i < numberOfStackItems; i++)
				{
					stack[i] = new VerificationTypeInfo(in);
				}
			}
			else
			{
				throw new ClassFormatError("Unsupported frame type " + frameType);
			}
		}
	}

	private static class VerificationTypeInfo
	{
		private static final int ITEM_Top = 0;
		private static final int ITEM_Integer = 1;
		private static final int ITEM_Float = 2;
		private static final int ITEM_Double = 3;
		private static final int ITEM_Long = 4;
		private static final int ITEM_Null = 5;
		private static final int ITEM_UninitializedThis = 6;
		private static final int ITEM_Object = 7;
		private static final int ITEM_Uninitialized = 8;

		/** Verification type tag (0...8). */
		private int tag;

		/** Constant pool index for ObjectVariableInfo entry. */
		@SuppressWarnings("unused")
		private int cpoolIndex;

		/**
		 * Code offset for UninitializedVariableInfo where variable is created witgh new
		 * operator.
		 */
		@SuppressWarnings("unused")
		private int offset;

		public VerificationTypeInfo(ByteInputStream in)
		{
			tag = in.u1();
			switch (tag)
			{
			case ITEM_Top:
				break;
			case ITEM_Integer:
				break;
			case ITEM_Float:
				break;
			case ITEM_Double:
				throw new ClassFormatError("Unhandled double");
			case ITEM_Long:
				throw new ClassFormatError("Unhandled long");
			case ITEM_Null:
				break;
			case ITEM_UninitializedThis:
				break;
			case ITEM_Object:
				cpoolIndex = in.u2();
				break;
			case ITEM_Uninitialized:
				offset = in.u2();
				break;
			default:
				throw new ClassFormatError("Unrecognized stack map entry tag " + tag);
			}
		}
	}

	private StackMapFrame[] stackMapEntries;

	public AttributeStackMapTable(ClassAttribute attribute, ConstantPool cp)
	{
		super(attribute);
		decode(cp);
	}

	private void decode(ConstantPool cp)
	{
		ByteInputStream in = new ByteInputStream(info);
		int numberOfEntries = in.u2();
		stackMapEntries = new StackMapFrame[numberOfEntries];
		for (int i = 0; i < numberOfEntries; i++)
		{
			stackMapEntries[i] = new StackMapFrame(in);
		}
		in.close();
	}
}
