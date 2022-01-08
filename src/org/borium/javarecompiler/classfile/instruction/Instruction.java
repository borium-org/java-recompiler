package org.borium.javarecompiler.classfile.instruction;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;

public abstract class Instruction
{
	public static final int NOP = 0;
	public static final int ACONST_NULL = 1;
	public static final int ICONST_M1 = 2;
	public static final int ICONST_0 = 3;
	public static final int ICONST_1 = 4;
	public static final int ICONST_2 = 5;
	public static final int ICONST_3 = 6;
	public static final int ICONST_4 = 7;
	public static final int ICONST_5 = 8;
	public static final int LCONST_0 = 9;
	public static final int LCONST_1 = 10;
	public static final int FCONST_0 = 11;
	public static final int FCONST_1 = 12;
	public static final int FCONST_2 = 13;
	public static final int DCONST_0 = 14;
	public static final int DCONST_1 = 15;
	public static final int BIPUSH = 16;
	public static final int SIPUSH = 17;
	public static final int LDC = 18;
	public static final int LDC_W = 19;
	public static final int LDC2_W = 20;
	public static final int ILOAD = 21;
	public static final int LLOAD = 22;
	public static final int FLOAD = 23;
	public static final int DLOAD = 24;
	public static final int ALOAD = 25;
	public static final int ILOAD_0 = 26;
	public static final int ILOAD_1 = 27;
	public static final int ILOAD_2 = 28;
	public static final int ILOAD_3 = 29;
	public static final int LLOAD_0 = 30;
	public static final int LLOAD_1 = 31;
	public static final int LLOAD_2 = 32;
	public static final int LLOAD_3 = 33;
	public static final int FLOAD_0 = 34;
	public static final int FLOAD_1 = 35;
	public static final int FLOAD_2 = 36;
	public static final int FLOAD_3 = 37;
	public static final int DLOAD_0 = 38;
	public static final int DLOAD_1 = 39;
	public static final int DLOAD_2 = 40;
	public static final int DLOAD_3 = 41;
	public static final int ALOAD_0 = 42;
	public static final int ALOAD_1 = 43;
	public static final int ALOAD_2 = 44;
	public static final int ALOAD_3 = 45;
	public static final int IALOAD = 46;
	public static final int LALOAD = 47;
	public static final int FALOAD = 48;
	public static final int DALOAD = 49;
	public static final int AALOAD = 50;
	public static final int BALOAD = 51;
	public static final int CALOAD = 52;
	public static final int SALOAD = 53;
	public static final int ISTORE = 54;
	public static final int LSTORE = 55;
	public static final int FSTORE = 56;
	public static final int DSTORE = 57;
	public static final int ASTORE = 58;
	public static final int ISTORE_0 = 59;
	public static final int ISTORE_1 = 60;
	public static final int ISTORE_2 = 61;
	public static final int ISTORE_3 = 62;
	public static final int LSTORE_0 = 63;
	public static final int LSTORE_1 = 64;
	public static final int LSTORE_2 = 65;
	public static final int LSTORE_3 = 66;
	public static final int FSTORE_0 = 67;
	public static final int FSTORE_1 = 68;
	public static final int FSTORE_2 = 69;
	public static final int FSTORE_3 = 70;
	public static final int DSTORE_0 = 71;
	public static final int DSTORE_1 = 72;
	public static final int DSTORE_2 = 73;
	public static final int DSTORE_3 = 74;
	public static final int ASTORE_0 = 75;
	public static final int ASTORE_1 = 76;
	public static final int ASTORE_2 = 77;
	public static final int ASTORE_3 = 78;
	public static final int IASTORE = 79;
	public static final int LASTORE = 80;
	public static final int FASTORE = 81;
	public static final int DASTORE = 82;
	public static final int AASTORE = 83;
	public static final int BASTORE = 84;
	public static final int CASTORE = 85;
	public static final int SASTORE = 86;
	public static final int POP = 87;
	public static final int POP2 = 88;
	public static final int DUP = 89;
	public static final int DUP_X1 = 90;
	public static final int DUP_X2 = 91;
	public static final int DUP2 = 92;
	public static final int DUP2_X1 = 93;
	public static final int DUP2_X2 = 94;
	public static final int SWAP = 95;
	public static final int IADD = 96;
	public static final int LADD = 97;
	public static final int FADD = 98;
	public static final int DADD = 99;
	public static final int ISUB = 100;
	public static final int LSUB = 101;
	public static final int FSUB = 102;
	public static final int DSUB = 103;
	public static final int IMUL = 104;
	public static final int LMUL = 105;
	public static final int FMUL = 106;
	public static final int DMUL = 107;
	public static final int IDIV = 108;
	public static final int LDIV = 109;
	public static final int FDIV = 110;
	public static final int DDIV = 111;
	public static final int IREM = 112;
	public static final int LREM = 113;
	public static final int FREM = 114;
	public static final int DREM = 115;
	public static final int INEG = 116;
	public static final int LNEG = 117;
	public static final int FNEG = 118;
	public static final int DNEG = 119;
	public static final int ISHL = 120;
	public static final int LSHL = 121;
	public static final int ISHR = 122;
	public static final int LSHR = 123;
	public static final int IUSHR = 124;
	public static final int LUSHR = 125;
	public static final int IAND = 126;
	public static final int LAND = 127;
	public static final int IOR = 128;
	public static final int LOR = 129;
	public static final int IXOR = 130;
	public static final int LXOR = 131;
	public static final int IINC = 132;
	public static final int I2L = 133;
	public static final int I2F = 134;
	public static final int I2D = 135;
	public static final int L2I = 136;
	public static final int L2F = 137;
	public static final int L2D = 138;
	public static final int F2I = 139;
	public static final int F2L = 140;
	public static final int F2D = 141;
	public static final int D2I = 142;
	public static final int D2L = 143;
	public static final int D2F = 144;
	public static final int I2B = 145;
	public static final int I2C = 146;
	public static final int I2S = 147;
	public static final int LCMP = 148;
	public static final int FCMPL = 149;
	public static final int FCMPG = 150;
	public static final int DCMPL = 151;
	public static final int DCMPG = 152;
	public static final int IFEQ = 153;
	public static final int IFNE = 154;
	public static final int IFLT = 155;
	public static final int IFGE = 156;
	public static final int IFGT = 157;
	public static final int IFLE = 158;
	public static final int IF_ICMPEQ = 159;
	public static final int IF_ICMPNE = 160;
	public static final int IF_ICMPLT = 161;
	public static final int IF_ICMPGE = 162;
	public static final int IF_ICMPGT = 163;
	public static final int IF_ICMPLE = 164;
	public static final int IF_ACMPEQ = 165;
	public static final int IF_ACMPNE = 166;
	public static final int GOTO = 167;
	public static final int JSR = 168;
	public static final int RET = 169;
	public static final int TABLESWITCH = 170;
	public static final int LOOKUPSWITCH = 171;
	public static final int IRETURN = 172;
	public static final int LRETURN = 173;
	public static final int FRETURN = 174;
	public static final int DRETURN = 175;
	public static final int ARETURN = 176;
	public static final int RETURN = 177;
	public static final int GETSTATIC = 178;
	public static final int PUTSTATIC = 179;
	public static final int GETFIELD = 180;
	public static final int PUTFIELD = 181;
	public static final int INVOKEVIRTUAL = 182;
	public static final int INVOKESPECIAL = 183;
	public static final int INVOKESTATIC = 184;
	public static final int INVOKEINTERFACE = 185;
	public static final int INVOKEDYNAMIC = 186;
	public static final int NEW = 187;
	public static final int NEWARRAY = 188;
	public static final int ANEWARRAY = 189;
	public static final int ARRAYLENGTH = 190;
	public static final int ATHROW = 191;
	public static final int CHECKCAST = 192;
	public static final int INSTANCEOF = 193;
	public static final int MONITORENTER = 194;
	public static final int MONITOREXIT = 195;
	public static final int WIDE = 196;
	public static final int MULTIANEWARRAY = 197;
	public static final int IFNULL = 198;
	public static final int IFNONNULL = 199;
	public static final int GOTO_W = 200;
	public static final int JSR_W = 201;
	public static final int BREAKPOINT = 202;
	public static final int IMPDEP1 = 254;
	public static final int IMPDEP2 = 255;

	public static Instruction read(ByteInputStream in, ConstantPool cp)
	{
		int code = in.u1();
		if (code == -1)
		{
			throw new ClassFormatError("Read error");
		}
		switch (code)
		{
		case NOP:
			return new InstructionNOP();
		case ACONST_NULL:
			return new InstructionACONST_NULL();
		case ICONST_M1:
			return new InstructionICONST(-1);
		case ICONST_0:
			return new InstructionICONST(0);
		case ICONST_1:
			return new InstructionICONST(1);
		case ICONST_2:
			return new InstructionICONST(2);
		case ICONST_3:
			return new InstructionICONST(3);
		case ICONST_4:
			return new InstructionICONST(4);
		case ICONST_5:
			return new InstructionICONST(5);
		case LCONST_0:
			return new InstructionLCONST(0);
		case LCONST_1:
			return new InstructionLCONST(1);
		case FCONST_0:
			return new InstructionFCONST(0);
		case FCONST_1:
			return new InstructionFCONST(1);
		case FCONST_2:
			return new InstructionFCONST(2);
		case DCONST_0:
			return new InstructionDCONST(0);
		case DCONST_1:
			return new InstructionDCONST(1);
		case BIPUSH:
			return new InstructionBIPUSH(in);
		case SIPUSH:
			return new InstructionSIPUSH(in);
		case LDC:
			return new InstructionLDC(in, cp);
		case LDC_W:
			return new InstructionLDC_W(in, cp);
		case LDC2_W:
			return new InstructionLDC2_W(in, cp);
		case ILOAD:
			return new InstructionILOAD(in, false);
		case LLOAD:
			return new InstructionLLOAD(in, false);
		case FLOAD:
			return new InstructionFLOAD(in, false);
		case DLOAD:
			return new InstructionDLOAD(in, false);
		case ALOAD:
			return new InstructionALOAD(in, false);
		case ILOAD_0:
			return new InstructionILOAD(0);
		case ILOAD_1:
			return new InstructionILOAD(1);
		case ILOAD_2:
			return new InstructionILOAD(2);
		case ILOAD_3:
			return new InstructionILOAD(3);
		case LLOAD_0:
			return new InstructionLLOAD(0);
		case LLOAD_1:
			return new InstructionLLOAD(1);
		case LLOAD_2:
			return new InstructionLLOAD(2);
		case LLOAD_3:
			return new InstructionLLOAD(3);
		case FLOAD_0:
			return new InstructionFLOAD(0);
		case FLOAD_1:
			return new InstructionFLOAD(1);
		case FLOAD_2:
			return new InstructionFLOAD(2);
		case FLOAD_3:
			return new InstructionFLOAD(3);
		case DLOAD_0:
			return new InstructionDLOAD(0);
		case DLOAD_1:
			return new InstructionDLOAD(1);
		case DLOAD_2:
			return new InstructionDLOAD(2);
		case DLOAD_3:
			return new InstructionDLOAD(3);
		case ALOAD_0:
			return new InstructionALOAD(0);
		case ALOAD_1:
			return new InstructionALOAD(1);
		case ALOAD_2:
			return new InstructionALOAD(2);
		case ALOAD_3:
			return new InstructionALOAD(3);
		case IALOAD:
			return new InstructionIALOAD();
		case LALOAD:
			return new InstructionLALOAD();
		case FALOAD:
			return new InstructionFALOAD();
		case DALOAD:
			return new InstructionDALOAD();
		case AALOAD:
			return new InstructionAALOAD();
		case BALOAD:
			return new InstructionBALOAD();
		case CALOAD:
			return new InstructionCALOAD();
		case SALOAD:
			return new InstructionSALOAD();
		case ISTORE:
			return new InstructionISTORE(in, false);
		case LSTORE:
			return new InstructionLSTORE(in, false);
		case FSTORE:
			return new InstructionFSTORE(in, false);
		case DSTORE:
			return new InstructionDSTORE(in, false);
		case ASTORE:
			return new InstructionASTORE(in, false);
		case ISTORE_0:
			return new InstructionISTORE(0);
		case ISTORE_1:
			return new InstructionISTORE(1);
		case ISTORE_2:
			return new InstructionISTORE(2);
		case ISTORE_3:
			return new InstructionISTORE(3);
		case LSTORE_0:
			return new InstructionLSTORE(0);
		case LSTORE_1:
			return new InstructionLSTORE(1);
		case LSTORE_2:
			return new InstructionLSTORE(2);
		case LSTORE_3:
			return new InstructionLSTORE(3);
		case FSTORE_0:
			return new InstructionFSTORE(0);
		case FSTORE_1:
			return new InstructionFSTORE(1);
		case FSTORE_2:
			return new InstructionFSTORE(2);
		case FSTORE_3:
			return new InstructionFSTORE(3);
		case DSTORE_0:
			return new InstructionDSTORE(0);
		case DSTORE_1:
			return new InstructionDSTORE(1);
		case DSTORE_2:
			return new InstructionDSTORE(2);
		case DSTORE_3:
			return new InstructionDSTORE(3);
		case ASTORE_0:
			return new InstructionASTORE(0);
		case ASTORE_1:
			return new InstructionASTORE(1);
		case ASTORE_2:
			return new InstructionASTORE(2);
		case ASTORE_3:
			return new InstructionASTORE(3);
		case IASTORE:
			return new InstructionIASTORE();
		case LASTORE:
			return new InstructionLASTORE();
		case FASTORE:
			return new InstructionFASTORE();
		case DASTORE:
			return new InstructionDASTORE();
		case AASTORE:
			return new InstructionAASTORE();
		case BASTORE:
			return new InstructionBASTORE();
		case CASTORE:
			return new InstructionCASTORE();
		case SASTORE:
			return new InstructionSASTORE();
		case POP:
			return new InstructionPOP();
		case POP2:
			return new InstructionPOP2();
		case DUP:
			return new InstructionDUP();
		case DUP_X1:
			return new InstructionDUP_X1();
		case DUP_X2:
			return new InstructionDUP_X2();
		case DUP2:
			return new InstructionDUP2();
		case DUP2_X1:
			return new InstructionDUP2_X1();
		case DUP2_X2:
			return new InstructionDUP2_X2();
		case SWAP:
			return new InstructionSWAP();
		case IADD:
			return new InstructionIADD();
		case LADD:
			return new InstructionLADD();
		case FADD:
			return new InstructionFADD();
		case DADD:
			return new InstructionDADD();
		case ISUB:
			return new InstructionISUB();
		case LSUB:
			return new InstructionLSUB();
		case FSUB:
			return new InstructionFSUB();
		case DSUB:
			return new InstructionDSUB();
		case IMUL:
			return new InstructionIMUL();
		case LMUL:
			return new InstructionLMUL();
		case FMUL:
			return new InstructionFMUL();
		case DMUL:
			return new InstructionDMUL();
		case IDIV:
			return new InstructionIDIV();
		case LDIV:
			return new InstructionLDIV();
		case FDIV:
			return new InstructionFDIV();
		case DDIV:
			return new InstructionDDIV();
		case IREM:
			return new InstructionIREM();
		case LREM:
			return new InstructionLREM();
		case FREM:
			return new InstructionFREM();
		case DREM:
			return new InstructionDREM();
		case INEG:
			return new InstructionINEG();
		case LNEG:
			return new InstructionLNEG();
		case FNEG:
			return new InstructionFNEG();
		case DNEG:
			return new InstructionDNEG();
		case ISHL:
			return new InstructionISHL();
		case LSHL:
			return new InstructionLSHL();
		case ISHR:
			return new InstructionISHR();
		case LSHR:
			return new InstructionLSHR();
		case IUSHR:
			return new InstructionIUSHR();
		case LUSHR:
			return new InstructionLUSHR();
		case IAND:
			return new InstructionIAND();
		case LAND:
			return new InstructionLAND();
		case IOR:
			return new InstructionIOR();
		case LOR:
			return new InstructionLOR();
		case IXOR:
			return new InstructionIXOR();
		case LXOR:
			return new InstructionLXOR();
		case IINC:
			return new InstructionIINC(in, false);
		case I2L:
			return new InstructionI2L();
		case I2F:
			return new InstructionI2F();
		case I2D:
			return new InstructionI2D();
		case L2I:
			return new InstructionL2I();
		case L2F:
			return new InstructionL2F();
		case L2D:
			return new InstructionL2D();
		case F2I:
			return new InstructionF2I();
		case F2L:
			return new InstructionF2L();
		case F2D:
			return new InstructionF2D();
		case D2I:
			return new InstructionD2I();
		case D2L:
			return new InstructionD2L();
		case D2F:
			return new InstructionD2F();
		case I2B:
			return new InstructionI2B();
		case I2C:
			return new InstructionI2C();
		case I2S:
			return new InstructionI2S();
		case LCMP:
			return new InstructionLCMP();
		case FCMPL:
			return new InstructionFCMPL();
		case FCMPG:
			return new InstructionFCMPG();
		case DCMPL:
			return new InstructionDCMPL();
		case DCMPG:
			return new InstructionDCMPG();
		case IFEQ:
			return new InstructionIFEQ(in);
		case IFNE:
			return new InstructionIFNE(in);
		case IFLT:
			return new InstructionIFLT(in);
		case IFGE:
			return new InstructionIFGE(in);
		case IFGT:
			return new InstructionIFGT(in);
		case IFLE:
			return new InstructionIFLE(in);
		case IF_ICMPEQ:
			return new InstructionIF_ICMPEQ(in);
		case IF_ICMPNE:
			return new InstructionIF_ICMPNE(in);
		case IF_ICMPLT:
			return new InstructionIF_ICMPLT(in);
		case IF_ICMPGE:
			return new InstructionIF_ICMPGE(in);
		case IF_ICMPGT:
			return new InstructionIF_ICMPGT(in);
		case IF_ICMPLE:
			return new InstructionIF_ICMPLE(in);
		case IF_ACMPEQ:
			return new InstructionIF_ACMPEQ(in);
		case IF_ACMPNE:
			return new InstructionIF_ACMPNE(in);
		case GOTO:
			return new InstructionGOTO(in);
		case JSR:
			return new InstructionJSR(in);
		case RET:
			return new InstructionRET(in, false);
		case TABLESWITCH:
			return new InstructionTABLESWITCH(in);
		case LOOKUPSWITCH:
			return new InstructionLOOKUPSWITCH(in);
		case IRETURN:
			return new InstructionIRETURN();
		case LRETURN:
			return new InstructionLRETURN();
		case FRETURN:
			return new InstructionFRETURN();
		case DRETURN:
			return new InstructionDRETURN();
		case ARETURN:
			return new InstructionARETURN();
		case RETURN:
			return new InstructionRETURN();
		case GETSTATIC:
			return new InstructionGETSTATIC(in, cp);
		case PUTSTATIC:
			return new InstructionPUTSTATIC(in, cp);
		case GETFIELD:
			return new InstructionGETFIELD(in, cp);
		case PUTFIELD:
			return new InstructionPUTFIELD(in, cp);
		case INVOKEVIRTUAL:
			return new InstructionINVOKEVIRTUAL(in, cp);
		case INVOKESPECIAL:
			return new InstructionINVOKESPECIAL(in, cp);
		case INVOKESTATIC:
			return new InstructionINVOKESTATIC(in, cp);
		case INVOKEINTERFACE:
			return new InstructionINVOKEINTERFACE(in, cp);
		case INVOKEDYNAMIC:
			return new InstructionINVOKEDYNAMIC(in);
		case NEW:
			return new InstructionNEW(in, cp);
		case NEWARRAY:
			return new InstructionNEWARRAY(in);
		case ANEWARRAY:
			return new InstructionANEWARRAY(in, cp);
		case ARRAYLENGTH:
			return new InstructionARRAYLENGTH();
		case ATHROW:
			return new InstructionATHROW();
		case CHECKCAST:
			return new InstructionCHECKCAST(in, cp);
		case INSTANCEOF:
			return new InstructionINSTANCEOF(in, cp);
		case MONITORENTER:
			return new InstructionMONITORENTER();
		case MONITOREXIT:
			return new InstructionMONITOREXIT();
		case WIDE:
			return new InstructionWIDE(in);
		case MULTIANEWARRAY:
			return new InstructionMULTIANEWARRAY(in);
		case IFNULL:
			return new InstructionIFNULL(in);
		case IFNONNULL:
			return new InstructionIFNONNULL(in);
		case GOTO_W:
			return new InstructionGOTO_W(in);
		case JSR_W:
			return new InstructionJSR_W(in);
//		case BREAKPOINT:
//			return new InstructionBREAKPOINT(in);
//		case IMPDEP1:
//			return new InstructionIMPDEP1(in);
//		case IMPDEP2:
//			return new InstructionIMPDEP2(in);
		}
		return null;
	}

	/** Address of this instruction in the Code attribute. */
	public int address;

	/**
	 * Add a label flag into the labels array if instruction has a target to
	 * transfer control to. Default implementation does not add a label.
	 *
	 * @param address Address of this instruction.
	 * @param labels  Labels array to modify if instruction jumps to a label.
	 */
	public void addLabel(int address, boolean[] labels)
	{
	}

	public void detailedDump(IndentedOutputStream stream)
	{
		String className = getClass().getSimpleName().substring("Instruction".length()).toLowerCase();
		stream.iprintln(className);
	}

	public int getAddress()
	{
		return address;
	}

	/**
	 * Calculate the stack depth change that is caused by executing this current
	 * instruction. Total stack depth after executing this instruction should not be
	 * negative, but here we cannot verify if this is the case.
	 *
	 * @return Stack depth change, positive or negative as appropriate.
	 */
	public abstract int getStackDepthChange();

	public int length()
	{
		return 1;
	}
}
