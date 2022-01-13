package org.borium.javarecompiler.cplusplus;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.instruction.*;

/**
 * C++-specific version of the execution context.
 */
public class CppExecutionContext extends ExecutionContext
{
	/** C++ equivalent of method type. */
	String cppType;

	protected CppExecutionContext(ClassMethod javaMethod)
	{
		super(javaMethod);
		cppType = new JavaTypeConverter(type).getCppType();
		// TODO Auto-generated constructor stub
	}

	public void execute(Instruction instruction)
	{
		String opcode = instruction.getClass().getSimpleName().substring(11);
		switch (opcode)
		{
		case "NOP":
			executeNOP((InstructionNOP) instruction);
			break;
		case "ACONST_NULL":
			executeACONST_NULL((InstructionACONST_NULL) instruction);
			break;
		case "ICONST":
			executeICONST((InstructionICONST) instruction);
			break;
		case "LCONST":
			executeLCONST((InstructionLCONST) instruction);
			break;
		case "FCONST":
			executeFCONST((InstructionFCONST) instruction);
			break;
		case "DCONST":
			executeDCONST((InstructionDCONST) instruction);
			break;
		case "BIPUSH":
			executeBIPUSH((InstructionBIPUSH) instruction);
			break;
		case "SIPUSH":
			executeSIPUSH((InstructionSIPUSH) instruction);
			break;
		case "LDC":
			executeLDC((InstructionLDC) instruction);
			break;
		case "LDC_W":
			executeLDC_W((InstructionLDC_W) instruction);
			break;
		case "LDC2_W":
			executeLDC2_W((InstructionLDC2_W) instruction);
			break;
		case "ILOAD":
			executeILOAD((InstructionILOAD) instruction);
			break;
		case "LLOAD":
			executeLLOAD((InstructionLLOAD) instruction);
			break;
		case "FLOAD":
			executeFLOAD((InstructionFLOAD) instruction);
			break;
		case "DLOAD":
			executeDLOAD((InstructionDLOAD) instruction);
			break;
		case "ALOAD":
			executeALOAD((InstructionALOAD) instruction);
			break;
		case "IALOAD":
			executeIALOAD((InstructionIALOAD) instruction);
			break;
		case "LALOAD":
			executeLALOAD((InstructionLALOAD) instruction);
			break;
		case "FALOAD":
			executeFALOAD((InstructionFALOAD) instruction);
			break;
		case "DALOAD":
			executeDALOAD((InstructionDALOAD) instruction);
			break;
		case "AALOAD":
			executeAALOAD((InstructionAALOAD) instruction);
			break;
		case "BALOAD":
			executeBALOAD((InstructionBALOAD) instruction);
			break;
		case "CALOAD":
			executeCALOAD((InstructionCALOAD) instruction);
			break;
		case "SALOAD":
			executeSALOAD((InstructionSALOAD) instruction);
			break;
		case "ISTORE":
			executeISTORE((InstructionISTORE) instruction);
			break;
		case "LSTORE":
			executeLSTORE((InstructionLSTORE) instruction);
			break;
		case "FSTORE":
			executeFSTORE((InstructionFSTORE) instruction);
			break;
		case "DSTORE":
			executeDSTORE((InstructionDSTORE) instruction);
			break;
		case "ASTORE":
			executeASTORE((InstructionASTORE) instruction);
			break;
		case "IASTORE":
			executeIASTORE((InstructionIASTORE) instruction);
			break;
		case "LASTORE":
			executeLASTORE((InstructionLASTORE) instruction);
			break;
		case "FASTORE":
			executeFASTORE((InstructionFASTORE) instruction);
			break;
		case "DASTORE":
			executeDASTORE((InstructionDASTORE) instruction);
			break;
		case "AASTORE":
			executeAASTORE((InstructionAASTORE) instruction);
			break;
		case "BASTORE":
			executeBASTORE((InstructionBASTORE) instruction);
			break;
		case "CASTORE":
			executeCASTORE((InstructionCASTORE) instruction);
			break;
		case "SASTORE":
			executeSASTORE((InstructionSASTORE) instruction);
			break;
		case "POP":
			executePOP((InstructionPOP) instruction);
			break;
		case "POP2":
			executePOP2((InstructionPOP2) instruction);
			break;
		case "DUP":
			executeDUP((InstructionDUP) instruction);
			break;
		case "DUP_X1":
			executeDUP_X1((InstructionDUP_X1) instruction);
			break;
		case "DUP_X2":
			executeDUP_X2((InstructionDUP_X2) instruction);
			break;
		case "DUP2":
			executeDUP2((InstructionDUP2) instruction);
			break;
		case "DUP2_X1":
			executeDUP2_X1((InstructionDUP2_X1) instruction);
			break;
		case "DUP2_X2":
			executeDUP2_X2((InstructionDUP2_X2) instruction);
			break;
		case "SWAP":
			executeSWAP((InstructionSWAP) instruction);
			break;
		case "IADD":
			executeIADD((InstructionIADD) instruction);
			break;
		case "LADD":
			executeLADD((InstructionLADD) instruction);
			break;
		case "FADD":
			executeFADD((InstructionFADD) instruction);
			break;
		case "DADD":
			executeDADD((InstructionDADD) instruction);
			break;
		case "ISUB":
			executeISUB((InstructionISUB) instruction);
			break;
		case "LSUB":
			executeLSUB((InstructionLSUB) instruction);
			break;
		case "FSUB":
			executeFSUB((InstructionFSUB) instruction);
			break;
		case "DSUB":
			executeDSUB((InstructionDSUB) instruction);
			break;
		case "IMUL":
			executeIMUL((InstructionIMUL) instruction);
			break;
		case "LMUL":
			executeLMUL((InstructionLMUL) instruction);
			break;
		case "FMUL":
			executeFMUL((InstructionFMUL) instruction);
			break;
		case "DMUL":
			executeDMUL((InstructionDMUL) instruction);
			break;
		case "IDIV":
			executeIDIV((InstructionIDIV) instruction);
			break;
		case "LDIV":
			executeLDIV((InstructionLDIV) instruction);
			break;
		case "FDIV":
			executeFDIV((InstructionFDIV) instruction);
			break;
		case "DDIV":
			executeDDIV((InstructionDDIV) instruction);
			break;
		case "IREM":
			executeIREM((InstructionIREM) instruction);
			break;
		case "LREM":
			executeLREM((InstructionLREM) instruction);
			break;
		case "FREM":
			executeFREM((InstructionFREM) instruction);
			break;
		case "DREM":
			executeDREM((InstructionDREM) instruction);
			break;
		case "INEG":
			executeINEG((InstructionINEG) instruction);
			break;
		case "LNEG":
			executeLNEG((InstructionLNEG) instruction);
			break;
		case "FNEG":
			executeFNEG((InstructionFNEG) instruction);
			break;
		case "DNEG":
			executeDNEG((InstructionDNEG) instruction);
			break;
		case "ISHL":
			executeISHL((InstructionISHL) instruction);
			break;
		case "LSHL":
			executeLSHL((InstructionLSHL) instruction);
			break;
		case "ISHR":
			executeISHR((InstructionISHR) instruction);
			break;
		case "LSHR":
			executeLSHR((InstructionLSHR) instruction);
			break;
		case "IUSHR":
			executeIUSHR((InstructionIUSHR) instruction);
			break;
		case "LUSHR":
			executeLUSHR((InstructionLUSHR) instruction);
			break;
		case "IAND":
			executeIAND((InstructionIAND) instruction);
			break;
		case "LAND":
			executeLAND((InstructionLAND) instruction);
			break;
		case "IOR":
			executeIOR((InstructionIOR) instruction);
			break;
		case "LOR":
			executeLOR((InstructionLOR) instruction);
			break;
		case "IXOR":
			executeIXOR((InstructionIXOR) instruction);
			break;
		case "LXOR":
			executeLXOR((InstructionLXOR) instruction);
			break;
		case "IINC":
			executeIINC((InstructionIINC) instruction);
			break;
		case "I2L":
			executeI2L((InstructionI2L) instruction);
			break;
		case "I2F":
			executeI2F((InstructionI2F) instruction);
			break;
		case "I2D":
			executeI2D((InstructionI2D) instruction);
			break;
		case "L2I":
			executeL2I((InstructionL2I) instruction);
			break;
		case "L2F":
			executeL2F((InstructionL2F) instruction);
			break;
		case "L2D":
			executeL2D((InstructionL2D) instruction);
			break;
		case "F2I":
			executeF2I((InstructionF2I) instruction);
			break;
		case "F2L":
			executeF2L((InstructionF2L) instruction);
			break;
		case "F2D":
			executeF2D((InstructionF2D) instruction);
			break;
		case "D2I":
			executeD2I((InstructionD2I) instruction);
			break;
		case "D2L":
			executeD2L((InstructionD2L) instruction);
			break;
		case "D2F":
			executeD2F((InstructionD2F) instruction);
			break;
		case "I2B":
			executeI2B((InstructionI2B) instruction);
			break;
		case "I2C":
			executeI2C((InstructionI2C) instruction);
			break;
		case "I2S":
			executeI2S((InstructionI2S) instruction);
			break;
		case "LCMP":
			executeLCMP((InstructionLCMP) instruction);
			break;
		case "FCMPL":
			executeFCMPL((InstructionFCMPL) instruction);
			break;
		case "FCMPG":
			executeFCMPG((InstructionFCMPG) instruction);
			break;
		case "DCMPL":
			executeDCMPL((InstructionDCMPL) instruction);
			break;
		case "DCMPG":
			executeDCMPG((InstructionDCMPG) instruction);
			break;
		case "IFEQ":
			executeIFEQ((InstructionIFEQ) instruction);
			break;
		case "IFNE":
			executeIFNE((InstructionIFNE) instruction);
			break;
		case "IFLT":
			executeIFLT((InstructionIFLT) instruction);
			break;
		case "IFGE":
			executeIFGE((InstructionIFGE) instruction);
			break;
		case "IFGT":
			executeIFGT((InstructionIFGT) instruction);
			break;
		case "IFLE":
			executeIFLE((InstructionIFLE) instruction);
			break;
		case "IF_ICMPEQ":
			executeIF_ICMPEQ((InstructionIF_ICMPEQ) instruction);
			break;
		case "IF_ICMPNE":
			executeIF_ICMPNE((InstructionIF_ICMPNE) instruction);
			break;
		case "IF_ICMPLT":
			executeIF_ICMPLT((InstructionIF_ICMPLT) instruction);
			break;
		case "IF_ICMPGE":
			executeIF_ICMPGE((InstructionIF_ICMPGE) instruction);
			break;
		case "IF_ICMPGT":
			executeIF_ICMPGT((InstructionIF_ICMPGT) instruction);
			break;
		case "IF_ICMPLE":
			executeIF_ICMPLE((InstructionIF_ICMPLE) instruction);
			break;
		case "IF_ACMPEQ":
			executeIF_ACMPEQ((InstructionIF_ACMPEQ) instruction);
			break;
		case "IF_ACMPNE":
			executeIF_ACMPNE((InstructionIF_ACMPNE) instruction);
			break;
		case "GOTO":
			executeGOTO((InstructionGOTO) instruction);
			break;
		case "JSR":
			executeJSR((InstructionJSR) instruction);
			break;
		case "RET":
			executeRET((InstructionRET) instruction);
			break;
		case "TABLESWITCH":
			executeTABLESWITCH((InstructionTABLESWITCH) instruction);
			break;
		case "LOOKUPSWITCH":
			executeLOOKUPSWITCH((InstructionLOOKUPSWITCH) instruction);
			break;
		case "IRETURN":
			executeIRETURN((InstructionIRETURN) instruction);
			break;
		case "LRETURN":
			executeLRETURN((InstructionLRETURN) instruction);
			break;
		case "FRETURN":
			executeFRETURN((InstructionFRETURN) instruction);
			break;
		case "DRETURN":
			executeDRETURN((InstructionDRETURN) instruction);
			break;
		case "ARETURN":
			executeARETURN((InstructionARETURN) instruction);
			break;
		case "RETURN":
			executeRETURN((InstructionRETURN) instruction);
			break;
		case "GETSTATIC":
			executeGETSTATIC((InstructionGETSTATIC) instruction);
			break;
		case "PUTSTATIC":
			executePUTSTATIC((InstructionPUTSTATIC) instruction);
			break;
		case "GETFIELD":
			executeGETFIELD((InstructionGETFIELD) instruction);
			break;
		case "PUTFIELD":
			executePUTFIELD((InstructionPUTFIELD) instruction);
			break;
		case "INVOKEVIRTUAL":
			executeINVOKEVIRTUAL((InstructionINVOKEVIRTUAL) instruction);
			break;
		case "INVOKESPECIAL":
			executeINVOKESPECIAL((InstructionINVOKESPECIAL) instruction);
			break;
		case "INVOKESTATIC":
			executeINVOKESTATIC((InstructionINVOKESTATIC) instruction);
			break;
		case "INVOKEINTERFACE":
			executeINVOKEINTERFACE((InstructionINVOKEINTERFACE) instruction);
			break;
		case "INVOKEDYNAMIC":
			executeINVOKEDYNAMIC((InstructionINVOKEDYNAMIC) instruction);
			break;
		case "NEW":
			executeNEW((InstructionNEW) instruction);
			break;
		case "NEWARRAY":
			executeNEWARRAY((InstructionNEWARRAY) instruction);
			break;
		case "ANEWARRAY":
			executeANEWARRAY((InstructionANEWARRAY) instruction);
			break;
		case "ARRAYLENGTH":
			executeARRAYLENGTH((InstructionARRAYLENGTH) instruction);
			break;
		case "ATHROW":
			executeATHROW((InstructionATHROW) instruction);
			break;
		case "CHECKCAST":
			executeCHECKCAST((InstructionCHECKCAST) instruction);
			break;
		case "INSTANCEOF":
			executeINSTANCEOF((InstructionINSTANCEOF) instruction);
			break;
		case "MONITORENTER":
			executeMONITORENTER((InstructionMONITORENTER) instruction);
			break;
		case "MONITOREXIT":
			executeMONITOREXIT((InstructionMONITOREXIT) instruction);
			break;
		case "WIDE":
			executeWIDE((InstructionWIDE) instruction);
			break;
		case "MULTIANEWARRAY":
			executeMULTIANEWARRAY((InstructionMULTIANEWARRAY) instruction);
			break;
		case "IFNULL":
			executeIFNULL((InstructionIFNULL) instruction);
			break;
		case "IFNONNULL":
			executeIFNONNULL((InstructionIFNONNULL) instruction);
			break;
		case "GOTO_W":
			executeGOTO_W((InstructionGOTO_W) instruction);
			break;
		case "JSR_W":
			executeJSR_W((InstructionJSR_W) instruction);
			break;
		default:
			System.out.println("Instruction " + opcode + " execution not supported");
		}
	}

	private void executeAALOAD(InstructionAALOAD instruction)
	{
		notSupported(instruction);
	}

	private void executeAASTORE(InstructionAASTORE instruction)
	{
		notSupported(instruction);
	}

	private void executeACONST_NULL(InstructionACONST_NULL instruction)
	{
		notSupported(instruction);
	}

	private void executeALOAD(InstructionALOAD instruction)
	{
		notSupported(instruction);
	}

	private void executeANEWARRAY(InstructionANEWARRAY instruction)
	{
		notSupported(instruction);
	}

	private void executeARETURN(InstructionARETURN instruction)
	{
		notSupported(instruction);
	}

	private void executeARRAYLENGTH(InstructionARRAYLENGTH instruction)
	{
		notSupported(instruction);
	}

	private void executeASTORE(InstructionASTORE instruction)
	{
		notSupported(instruction);
	}

	private void executeATHROW(InstructionATHROW instruction)
	{
		notSupported(instruction);
	}

	private void executeBALOAD(InstructionBALOAD instruction)
	{
		notSupported(instruction);
	}

	private void executeBASTORE(InstructionBASTORE instruction)
	{
		notSupported(instruction);
	}

	private void executeBIPUSH(InstructionBIPUSH instruction)
	{
		notSupported(instruction);
	}

	private void executeCALOAD(InstructionCALOAD instruction)
	{
		notSupported(instruction);
	}

	private void executeCASTORE(InstructionCASTORE instruction)
	{
		notSupported(instruction);
	}

	private void executeCHECKCAST(InstructionCHECKCAST instruction)
	{
		notSupported(instruction);
	}

	private void executeD2F(InstructionD2F instruction)
	{
		notSupported(instruction);
	}

	private void executeD2I(InstructionD2I instruction)
	{
		notSupported(instruction);
	}

	private void executeD2L(InstructionD2L instruction)
	{
		notSupported(instruction);
	}

	private void executeDADD(InstructionDADD instruction)
	{
		notSupported(instruction);
	}

	private void executeDALOAD(InstructionDALOAD instruction)
	{
		notSupported(instruction);
	}

	private void executeDASTORE(InstructionDASTORE instruction)
	{
		notSupported(instruction);
	}

	private void executeDCMPG(InstructionDCMPG instruction)
	{
		notSupported(instruction);
	}

	private void executeDCMPL(InstructionDCMPL instruction)
	{
		notSupported(instruction);
	}

	private void executeDCONST(InstructionDCONST instruction)
	{
		notSupported(instruction);
	}

	private void executeDDIV(InstructionDDIV instruction)
	{
		notSupported(instruction);
	}

	private void executeDLOAD(InstructionDLOAD instruction)
	{
		notSupported(instruction);
	}

	private void executeDMUL(InstructionDMUL instruction)
	{
		notSupported(instruction);
	}

	private void executeDNEG(InstructionDNEG instruction)
	{
		notSupported(instruction);
	}

	private void executeDREM(InstructionDREM instruction)
	{
		notSupported(instruction);
	}

	private void executeDRETURN(InstructionDRETURN instruction)
	{
		notSupported(instruction);
	}

	private void executeDSTORE(InstructionDSTORE instruction)
	{
		notSupported(instruction);
	}

	private void executeDSUB(InstructionDSUB instruction)
	{
		notSupported(instruction);
	}

	private void executeDUP(InstructionDUP instruction)
	{
		notSupported(instruction);
	}

	private void executeDUP_X1(InstructionDUP_X1 instruction)
	{
		notSupported(instruction);
	}

	private void executeDUP_X2(InstructionDUP_X2 instruction)
	{
		notSupported(instruction);
	}

	private void executeDUP2(InstructionDUP2 instruction)
	{
		notSupported(instruction);
	}

	private void executeDUP2_X1(InstructionDUP2_X1 instruction)
	{
		notSupported(instruction);
	}

	private void executeDUP2_X2(InstructionDUP2_X2 instruction)
	{
		notSupported(instruction);
	}

	private void executeF2D(InstructionF2D instruction)
	{
		notSupported(instruction);
	}

	private void executeF2I(InstructionF2I instruction)
	{
		notSupported(instruction);
	}

	private void executeF2L(InstructionF2L instruction)
	{
		notSupported(instruction);
	}

	private void executeFADD(InstructionFADD instruction)
	{
		notSupported(instruction);
	}

	private void executeFALOAD(InstructionFALOAD instruction)
	{
		notSupported(instruction);
	}

	private void executeFASTORE(InstructionFASTORE instruction)
	{
		notSupported(instruction);
	}

	private void executeFCMPG(InstructionFCMPG instruction)
	{
		notSupported(instruction);
	}

	private void executeFCMPL(InstructionFCMPL instruction)
	{
		notSupported(instruction);
	}

	private void executeFCONST(InstructionFCONST instruction)
	{
		notSupported(instruction);
	}

	private void executeFDIV(InstructionFDIV instruction)
	{
		notSupported(instruction);
	}

	private void executeFLOAD(InstructionFLOAD instruction)
	{
		notSupported(instruction);
	}

	private void executeFMUL(InstructionFMUL instruction)
	{
		notSupported(instruction);
	}

	private void executeFNEG(InstructionFNEG instruction)
	{
		notSupported(instruction);
	}

	private void executeFREM(InstructionFREM instruction)
	{
		notSupported(instruction);
	}

	private void executeFRETURN(InstructionFRETURN instruction)
	{
		notSupported(instruction);
	}

	private void executeFSTORE(InstructionFSTORE instruction)
	{
		notSupported(instruction);
	}

	private void executeFSUB(InstructionFSUB instruction)
	{
		notSupported(instruction);
	}

	private void executeGETFIELD(InstructionGETFIELD instruction)
	{
		notSupported(instruction);
	}

	private void executeGETSTATIC(InstructionGETSTATIC instruction)
	{
		notSupported(instruction);
	}

	private void executeGOTO(InstructionGOTO instruction)
	{
		notSupported(instruction);
	}

	private void executeGOTO_W(InstructionGOTO_W instruction)
	{
		notSupported(instruction);
	}

	private void executeI2B(InstructionI2B instruction)
	{
		notSupported(instruction);
	}

	private void executeI2C(InstructionI2C instruction)
	{
		notSupported(instruction);
	}

	private void executeI2D(InstructionI2D instruction)
	{
		notSupported(instruction);
	}

	private void executeI2F(InstructionI2F instruction)
	{
		notSupported(instruction);
	}

	private void executeI2L(InstructionI2L instruction)
	{
		notSupported(instruction);
	}

	private void executeI2S(InstructionI2S instruction)
	{
		notSupported(instruction);
	}

	private void executeIADD(InstructionIADD instruction)
	{
		notSupported(instruction);
	}

	private void executeIALOAD(InstructionIALOAD instruction)
	{
		notSupported(instruction);
	}

	private void executeIAND(InstructionIAND instruction)
	{
		notSupported(instruction);
	}

	private void executeIASTORE(InstructionIASTORE instruction)
	{
		notSupported(instruction);
	}

	private void executeICONST(InstructionICONST instruction)
	{
		notSupported(instruction);
	}

	private void executeIDIV(InstructionIDIV instruction)
	{
		notSupported(instruction);
	}

	private void executeIF_ACMPEQ(InstructionIF_ACMPEQ instruction)
	{
		notSupported(instruction);
	}

	private void executeIF_ACMPNE(InstructionIF_ACMPNE instruction)
	{
		notSupported(instruction);
	}

	private void executeIF_ICMPEQ(InstructionIF_ICMPEQ instruction)
	{
		notSupported(instruction);
	}

	private void executeIF_ICMPGE(InstructionIF_ICMPGE instruction)
	{
		notSupported(instruction);
	}

	private void executeIF_ICMPGT(InstructionIF_ICMPGT instruction)
	{
		notSupported(instruction);
	}

	private void executeIF_ICMPLE(InstructionIF_ICMPLE instruction)
	{
		notSupported(instruction);
	}

	private void executeIF_ICMPLT(InstructionIF_ICMPLT instruction)
	{
		notSupported(instruction);
	}

	private void executeIF_ICMPNE(InstructionIF_ICMPNE instruction)
	{
		notSupported(instruction);
	}

	private void executeIFEQ(InstructionIFEQ instruction)
	{
		notSupported(instruction);
	}

	private void executeIFGE(InstructionIFGE instruction)
	{
		notSupported(instruction);
	}

	private void executeIFGT(InstructionIFGT instruction)
	{
		notSupported(instruction);
	}

	private void executeIFLE(InstructionIFLE instruction)
	{
		notSupported(instruction);
	}

	private void executeIFLT(InstructionIFLT instruction)
	{
		notSupported(instruction);
	}

	private void executeIFNE(InstructionIFNE instruction)
	{
		notSupported(instruction);
	}

	private void executeIFNONNULL(InstructionIFNONNULL instruction)
	{
		notSupported(instruction);
	}

	private void executeIFNULL(InstructionIFNULL instruction)
	{
		notSupported(instruction);
	}

	private void executeIINC(InstructionIINC instruction)
	{
		notSupported(instruction);
	}

	private void executeILOAD(InstructionILOAD instruction)
	{
		notSupported(instruction);
	}

	private void executeIMUL(InstructionIMUL instruction)
	{
		notSupported(instruction);
	}

	private void executeINEG(InstructionINEG instruction)
	{
		notSupported(instruction);
	}

	private void executeINSTANCEOF(InstructionINSTANCEOF instruction)
	{
		notSupported(instruction);
	}

	private void executeINVOKEDYNAMIC(InstructionINVOKEDYNAMIC instruction)
	{
		notSupported(instruction);
	}

	private void executeINVOKEINTERFACE(InstructionINVOKEINTERFACE instruction)
	{
		notSupported(instruction);
	}

	private void executeINVOKESPECIAL(InstructionINVOKESPECIAL instruction)
	{
		notSupported(instruction);
	}

	private void executeINVOKESTATIC(InstructionINVOKESTATIC instruction)
	{
		notSupported(instruction);
	}

	private void executeINVOKEVIRTUAL(InstructionINVOKEVIRTUAL instruction)
	{
		notSupported(instruction);
	}

	private void executeIOR(InstructionIOR instruction)
	{
		notSupported(instruction);
	}

	private void executeIREM(InstructionIREM instruction)
	{
		notSupported(instruction);
	}

	private void executeIRETURN(InstructionIRETURN instruction)
	{
		notSupported(instruction);
	}

	private void executeISHL(InstructionISHL instruction)
	{
		notSupported(instruction);
	}

	private void executeISHR(InstructionISHR instruction)
	{
		notSupported(instruction);
	}

	private void executeISTORE(InstructionISTORE instruction)
	{
		notSupported(instruction);
	}

	private void executeISUB(InstructionISUB instruction)
	{
		notSupported(instruction);
	}

	private void executeIUSHR(InstructionIUSHR instruction)
	{
		notSupported(instruction);
	}

	private void executeIXOR(InstructionIXOR instruction)
	{
		notSupported(instruction);
	}

	private void executeJSR(InstructionJSR instruction)
	{
		notSupported(instruction);
	}

	private void executeJSR_W(InstructionJSR_W instruction)
	{
		notSupported(instruction);
	}

	private void executeL2D(InstructionL2D instruction)
	{
		notSupported(instruction);
	}

	private void executeL2F(InstructionL2F instruction)
	{
		notSupported(instruction);
	}

	private void executeL2I(InstructionL2I instruction)
	{
		notSupported(instruction);
	}

	private void executeLADD(InstructionLADD instruction)
	{
		notSupported(instruction);
	}

	private void executeLALOAD(InstructionLALOAD instruction)
	{
		notSupported(instruction);
	}

	private void executeLAND(InstructionLAND instruction)
	{
		notSupported(instruction);
	}

	private void executeLASTORE(InstructionLASTORE instruction)
	{
		notSupported(instruction);
	}

	private void executeLCMP(InstructionLCMP instruction)
	{
		notSupported(instruction);
	}

	private void executeLCONST(InstructionLCONST instruction)
	{
		notSupported(instruction);
	}

	private void executeLDC(InstructionLDC instruction)
	{
		notSupported(instruction);
	}

	private void executeLDC_W(InstructionLDC_W instruction)
	{
		notSupported(instruction);
	}

	private void executeLDC2_W(InstructionLDC2_W instruction)
	{
		notSupported(instruction);
	}

	private void executeLDIV(InstructionLDIV instruction)
	{
		notSupported(instruction);
	}

	private void executeLLOAD(InstructionLLOAD instruction)
	{
		notSupported(instruction);
	}

	private void executeLMUL(InstructionLMUL instruction)
	{
		notSupported(instruction);
	}

	private void executeLNEG(InstructionLNEG instruction)
	{
		notSupported(instruction);
	}

	private void executeLOOKUPSWITCH(InstructionLOOKUPSWITCH instruction)
	{
		notSupported(instruction);
	}

	private void executeLOR(InstructionLOR instruction)
	{
		notSupported(instruction);
	}

	private void executeLREM(InstructionLREM instruction)
	{
		notSupported(instruction);
	}

	private void executeLRETURN(InstructionLRETURN instruction)
	{
		notSupported(instruction);
	}

	private void executeLSHL(InstructionLSHL instruction)
	{
		notSupported(instruction);
	}

	private void executeLSHR(InstructionLSHR instruction)
	{
		notSupported(instruction);
	}

	private void executeLSTORE(InstructionLSTORE instruction)
	{
		notSupported(instruction);
	}

	private void executeLSUB(InstructionLSUB instruction)
	{
		notSupported(instruction);
	}

	private void executeLUSHR(InstructionLUSHR instruction)
	{
		notSupported(instruction);
	}

	private void executeLXOR(InstructionLXOR instruction)
	{
		notSupported(instruction);
	}

	private void executeMONITORENTER(InstructionMONITORENTER instruction)
	{
		notSupported(instruction);
	}

	private void executeMONITOREXIT(InstructionMONITOREXIT instruction)
	{
		notSupported(instruction);
	}

	private void executeMULTIANEWARRAY(InstructionMULTIANEWARRAY instruction)
	{
		notSupported(instruction);
	}

	private void executeNEW(InstructionNEW instruction)
	{
		notSupported(instruction);
	}

	private void executeNEWARRAY(InstructionNEWARRAY instruction)
	{
		notSupported(instruction);
	}

	private void executeNOP(InstructionNOP instruction)
	{
		notSupported(instruction);
	}

	private void executePOP(InstructionPOP instruction)
	{
		notSupported(instruction);
	}

	private void executePOP2(InstructionPOP2 instruction)
	{
		notSupported(instruction);
	}

	private void executePUTFIELD(InstructionPUTFIELD instruction)
	{
		notSupported(instruction);
	}

	private void executePUTSTATIC(InstructionPUTSTATIC instruction)
	{
		notSupported(instruction);
	}

	private void executeRET(InstructionRET instruction)
	{
		notSupported(instruction);
	}

	private void executeRETURN(InstructionRETURN instruction)
	{
		notSupported(instruction);
	}

	private void executeSALOAD(InstructionSALOAD instruction)
	{
		notSupported(instruction);
	}

	private void executeSASTORE(InstructionSASTORE instruction)
	{
		notSupported(instruction);
	}

	private void executeSIPUSH(InstructionSIPUSH instruction)
	{
		notSupported(instruction);
	}

	private void executeSWAP(InstructionSWAP instruction)
	{
		notSupported(instruction);
	}

	private void executeTABLESWITCH(InstructionTABLESWITCH instruction)
	{
		notSupported(instruction);
	}

	private void executeWIDE(InstructionWIDE instruction)
	{
		notSupported(instruction);
	}

	private void notSupported(Instruction instruction)
	{
		String opcode = instruction.getClass().getSimpleName().substring(11);
		System.out.println("Instruction " + opcode + " execution not supported");
	}
}
