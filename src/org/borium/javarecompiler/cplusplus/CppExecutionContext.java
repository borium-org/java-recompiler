package org.borium.javarecompiler.cplusplus;

import static org.borium.javarecompiler.Statics.*;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.classfile.constants.*;
import org.borium.javarecompiler.classfile.instruction.*;

/**
 * C++-specific version of the execution context.
 */
public class CppExecutionContext extends ExecutionContext
{
	/** C++ equivalent of method type. */
	String cppType;

	/** C++ equivalent of class type. */
	String classType;

	/**
	 * C++ object that is implementing the class containing the method that is using
	 * this execution context.
	 */
	private CppClass cppClass;

	/** C++ method that this using this execution context. */
	@SuppressWarnings("unused")
	private CppMethod cppMethod;

	/** True for special handling of initialized string array construction. */
	private boolean isStringArray = false;

	protected CppExecutionContext(CppMethod cppMethod, CppClass cppClass, ClassMethod javaMethod)
	{
		super(javaMethod);
		this.cppClass = cppClass;
		this.cppMethod = cppMethod;
		cppType = new JavaTypeConverter(type, javaMethod.isStatic()).getCppType();
		classType = cppClass.namespace + "::" + cppClass.className;
		if (!javaMethod.isStatic())
		{
			locals[0].set(classType, "this");
		}
		parseParameters();
	}

	public void generate(IndentedOutputStream source, Instruction instruction)
	{
		String opcode = instruction.getClass().getSimpleName().substring(11);
		switch (opcode)
		{
		case "NOP":
			generateNOP(source, (InstructionNOP) instruction);
			break;
		case "ACONST_NULL":
			generateACONST_NULL(source, (InstructionACONST_NULL) instruction);
			break;
		case "ICONST":
			generateICONST(source, (InstructionICONST) instruction);
			break;
		case "LCONST":
			generateLCONST(source, (InstructionLCONST) instruction);
			break;
		case "FCONST":
			generateFCONST(source, (InstructionFCONST) instruction);
			break;
		case "DCONST":
			generateDCONST(source, (InstructionDCONST) instruction);
			break;
		case "BIPUSH":
			generateBIPUSH(source, (InstructionBIPUSH) instruction);
			break;
		case "SIPUSH":
			generateSIPUSH(source, (InstructionSIPUSH) instruction);
			break;
		case "LDC":
			generateLDC(source, (InstructionLDC) instruction);
			break;
		case "LDC_W":
			generateLDC_W(source, (InstructionLDC_W) instruction);
			break;
		case "LDC2_W":
			generateLDC2_W(source, (InstructionLDC2_W) instruction);
			break;
		case "ILOAD":
			generateILOAD(source, (InstructionILOAD) instruction);
			break;
		case "LLOAD":
			generateLLOAD(source, (InstructionLLOAD) instruction);
			break;
		case "FLOAD":
			generateFLOAD(source, (InstructionFLOAD) instruction);
			break;
		case "DLOAD":
			generateDLOAD(source, (InstructionDLOAD) instruction);
			break;
		case "ALOAD":
			generateALOAD(source, (InstructionALOAD) instruction);
			break;
		case "IALOAD":
			generateIALOAD(source, (InstructionIALOAD) instruction);
			break;
		case "LALOAD":
			generateLALOAD(source, (InstructionLALOAD) instruction);
			break;
		case "FALOAD":
			generateFALOAD(source, (InstructionFALOAD) instruction);
			break;
		case "DALOAD":
			generateDALOAD(source, (InstructionDALOAD) instruction);
			break;
		case "AALOAD":
			generateAALOAD(source, (InstructionAALOAD) instruction);
			break;
		case "BALOAD":
			generateBALOAD(source, (InstructionBALOAD) instruction);
			break;
		case "CALOAD":
			generateCALOAD(source, (InstructionCALOAD) instruction);
			break;
		case "SALOAD":
			generateSALOAD(source, (InstructionSALOAD) instruction);
			break;
		case "ISTORE":
			generateISTORE(source, (InstructionISTORE) instruction);
			break;
		case "LSTORE":
			generateLSTORE(source, (InstructionLSTORE) instruction);
			break;
		case "FSTORE":
			generateFSTORE(source, (InstructionFSTORE) instruction);
			break;
		case "DSTORE":
			generateDSTORE(source, (InstructionDSTORE) instruction);
			break;
		case "ASTORE":
			generateASTORE(source, (InstructionASTORE) instruction);
			break;
		case "IASTORE":
			generateIASTORE(source, (InstructionIASTORE) instruction);
			break;
		case "LASTORE":
			generateLASTORE(source, (InstructionLASTORE) instruction);
			break;
		case "FASTORE":
			generateFASTORE(source, (InstructionFASTORE) instruction);
			break;
		case "DASTORE":
			generateDASTORE(source, (InstructionDASTORE) instruction);
			break;
		case "AASTORE":
			generateAASTORE(source, (InstructionAASTORE) instruction);
			break;
		case "BASTORE":
			generateBASTORE(source, (InstructionBASTORE) instruction);
			break;
		case "CASTORE":
			generateCASTORE(source, (InstructionCASTORE) instruction);
			break;
		case "SASTORE":
			generateSASTORE(source, (InstructionSASTORE) instruction);
			break;
		case "POP":
			generatePOP(source, (InstructionPOP) instruction);
			break;
		case "POP2":
			generatePOP2(source, (InstructionPOP2) instruction);
			break;
		case "DUP":
			generateDUP(source, (InstructionDUP) instruction);
			break;
		case "DUP_X1":
			generateDUP_X1(source, (InstructionDUP_X1) instruction);
			break;
		case "DUP_X2":
			generateDUP_X2(source, (InstructionDUP_X2) instruction);
			break;
		case "DUP2":
			generateDUP2(source, (InstructionDUP2) instruction);
			break;
		case "DUP2_X1":
			generateDUP2_X1(source, (InstructionDUP2_X1) instruction);
			break;
		case "DUP2_X2":
			generateDUP2_X2(source, (InstructionDUP2_X2) instruction);
			break;
		case "SWAP":
			generateSWAP(source, (InstructionSWAP) instruction);
			break;
		case "IADD":
			generateIADD(source, (InstructionIADD) instruction);
			break;
		case "LADD":
			generateLADD(source, (InstructionLADD) instruction);
			break;
		case "FADD":
			generateFADD(source, (InstructionFADD) instruction);
			break;
		case "DADD":
			generateDADD(source, (InstructionDADD) instruction);
			break;
		case "ISUB":
			generateISUB(source, (InstructionISUB) instruction);
			break;
		case "LSUB":
			generateLSUB(source, (InstructionLSUB) instruction);
			break;
		case "FSUB":
			generateFSUB(source, (InstructionFSUB) instruction);
			break;
		case "DSUB":
			generateDSUB(source, (InstructionDSUB) instruction);
			break;
		case "IMUL":
			generateIMUL(source, (InstructionIMUL) instruction);
			break;
		case "LMUL":
			generateLMUL(source, (InstructionLMUL) instruction);
			break;
		case "FMUL":
			generateFMUL(source, (InstructionFMUL) instruction);
			break;
		case "DMUL":
			generateDMUL(source, (InstructionDMUL) instruction);
			break;
		case "IDIV":
			generateIDIV(source, (InstructionIDIV) instruction);
			break;
		case "LDIV":
			generateLDIV(source, (InstructionLDIV) instruction);
			break;
		case "FDIV":
			generateFDIV(source, (InstructionFDIV) instruction);
			break;
		case "DDIV":
			generateDDIV(source, (InstructionDDIV) instruction);
			break;
		case "IREM":
			generateIREM(source, (InstructionIREM) instruction);
			break;
		case "LREM":
			generateLREM(source, (InstructionLREM) instruction);
			break;
		case "FREM":
			generateFREM(source, (InstructionFREM) instruction);
			break;
		case "DREM":
			generateDREM(source, (InstructionDREM) instruction);
			break;
		case "INEG":
			generateINEG(source, (InstructionINEG) instruction);
			break;
		case "LNEG":
			generateLNEG(source, (InstructionLNEG) instruction);
			break;
		case "FNEG":
			generateFNEG(source, (InstructionFNEG) instruction);
			break;
		case "DNEG":
			generateDNEG(source, (InstructionDNEG) instruction);
			break;
		case "ISHL":
			generateISHL(source, (InstructionISHL) instruction);
			break;
		case "LSHL":
			generateLSHL(source, (InstructionLSHL) instruction);
			break;
		case "ISHR":
			generateISHR(source, (InstructionISHR) instruction);
			break;
		case "LSHR":
			generateLSHR(source, (InstructionLSHR) instruction);
			break;
		case "IUSHR":
			generateIUSHR(source, (InstructionIUSHR) instruction);
			break;
		case "LUSHR":
			generateLUSHR(source, (InstructionLUSHR) instruction);
			break;
		case "IAND":
			generateIAND(source, (InstructionIAND) instruction);
			break;
		case "LAND":
			generateLAND(source, (InstructionLAND) instruction);
			break;
		case "IOR":
			generateIOR(source, (InstructionIOR) instruction);
			break;
		case "LOR":
			generateLOR(source, (InstructionLOR) instruction);
			break;
		case "IXOR":
			generateIXOR(source, (InstructionIXOR) instruction);
			break;
		case "LXOR":
			generateLXOR(source, (InstructionLXOR) instruction);
			break;
		case "IINC":
			generateIINC(source, (InstructionIINC) instruction);
			break;
		case "I2L":
			generateI2L(source, (InstructionI2L) instruction);
			break;
		case "I2F":
			generateI2F(source, (InstructionI2F) instruction);
			break;
		case "I2D":
			generateI2D(source, (InstructionI2D) instruction);
			break;
		case "L2I":
			generateL2I(source, (InstructionL2I) instruction);
			break;
		case "L2F":
			generateL2F(source, (InstructionL2F) instruction);
			break;
		case "L2D":
			generateL2D(source, (InstructionL2D) instruction);
			break;
		case "F2I":
			generateF2I(source, (InstructionF2I) instruction);
			break;
		case "F2L":
			generateF2L(source, (InstructionF2L) instruction);
			break;
		case "F2D":
			generateF2D(source, (InstructionF2D) instruction);
			break;
		case "D2I":
			generateD2I(source, (InstructionD2I) instruction);
			break;
		case "D2L":
			generateD2L(source, (InstructionD2L) instruction);
			break;
		case "D2F":
			generateD2F(source, (InstructionD2F) instruction);
			break;
		case "I2B":
			generateI2B(source, (InstructionI2B) instruction);
			break;
		case "I2C":
			generateI2C(source, (InstructionI2C) instruction);
			break;
		case "I2S":
			generateI2S(source, (InstructionI2S) instruction);
			break;
		case "LCMP":
			generateLCMP(source, (InstructionLCMP) instruction);
			break;
		case "FCMPL":
			generateFCMPL(source, (InstructionFCMPL) instruction);
			break;
		case "FCMPG":
			generateFCMPG(source, (InstructionFCMPG) instruction);
			break;
		case "DCMPL":
			generateDCMPL(source, (InstructionDCMPL) instruction);
			break;
		case "DCMPG":
			generateDCMPG(source, (InstructionDCMPG) instruction);
			break;
		case "IFEQ":
			generateIFEQ(source, (InstructionIFEQ) instruction);
			break;
		case "IFNE":
			generateIFNE(source, (InstructionIFNE) instruction);
			break;
		case "IFLT":
			generateIFLT(source, (InstructionIFLT) instruction);
			break;
		case "IFGE":
			generateIFGE(source, (InstructionIFGE) instruction);
			break;
		case "IFGT":
			generateIFGT(source, (InstructionIFGT) instruction);
			break;
		case "IFLE":
			generateIFLE(source, (InstructionIFLE) instruction);
			break;
		case "IF_ICMPEQ":
			generateIF_ICMPEQ(source, (InstructionIF_ICMPEQ) instruction);
			break;
		case "IF_ICMPNE":
			generateIF_ICMPNE(source, (InstructionIF_ICMPNE) instruction);
			break;
		case "IF_ICMPLT":
			generateIF_ICMPLT(source, (InstructionIF_ICMPLT) instruction);
			break;
		case "IF_ICMPGE":
			generateIF_ICMPGE(source, (InstructionIF_ICMPGE) instruction);
			break;
		case "IF_ICMPGT":
			generateIF_ICMPGT(source, (InstructionIF_ICMPGT) instruction);
			break;
		case "IF_ICMPLE":
			generateIF_ICMPLE(source, (InstructionIF_ICMPLE) instruction);
			break;
		case "IF_ACMPEQ":
			generateIF_ACMPEQ(source, (InstructionIF_ACMPEQ) instruction);
			break;
		case "IF_ACMPNE":
			generateIF_ACMPNE(source, (InstructionIF_ACMPNE) instruction);
			break;
		case "GOTO":
			generateGOTO(source, (InstructionGOTO) instruction);
			break;
		case "JSR":
			generateJSR(source, (InstructionJSR) instruction);
			break;
		case "RET":
			generateRET(source, (InstructionRET) instruction);
			break;
		case "TABLESWITCH":
			generateTABLESWITCH(source, (InstructionTABLESWITCH) instruction);
			break;
		case "LOOKUPSWITCH":
			generateLOOKUPSWITCH(source, (InstructionLOOKUPSWITCH) instruction);
			break;
		case "IRETURN":
			generateIRETURN(source, (InstructionIRETURN) instruction);
			break;
		case "LRETURN":
			generateLRETURN(source, (InstructionLRETURN) instruction);
			break;
		case "FRETURN":
			generateFRETURN(source, (InstructionFRETURN) instruction);
			break;
		case "DRETURN":
			generateDRETURN(source, (InstructionDRETURN) instruction);
			break;
		case "ARETURN":
			generateARETURN(source, (InstructionARETURN) instruction);
			break;
		case "RETURN":
			generateRETURN(source, (InstructionRETURN) instruction);
			break;
		case "GETSTATIC":
			generateGETSTATIC(source, (InstructionGETSTATIC) instruction);
			break;
		case "PUTSTATIC":
			generatePUTSTATIC(source, (InstructionPUTSTATIC) instruction);
			break;
		case "GETFIELD":
			generateGETFIELD(source, (InstructionGETFIELD) instruction);
			break;
		case "PUTFIELD":
			generatePUTFIELD(source, (InstructionPUTFIELD) instruction);
			break;
		case "INVOKEVIRTUAL":
			generateINVOKEVIRTUAL(source, (InstructionINVOKEVIRTUAL) instruction);
			break;
		case "INVOKESPECIAL":
			generateINVOKESPECIAL(source, (InstructionINVOKESPECIAL) instruction);
			break;
		case "INVOKESTATIC":
			generateINVOKESTATIC(source, (InstructionINVOKESTATIC) instruction);
			break;
		case "INVOKEINTERFACE":
			generateINVOKEINTERFACE(source, (InstructionINVOKEINTERFACE) instruction);
			break;
		case "INVOKEDYNAMIC":
			generateINVOKEDYNAMIC(source, (InstructionINVOKEDYNAMIC) instruction);
			break;
		case "NEW":
			generateNEW(source, (InstructionNEW) instruction);
			break;
		case "NEWARRAY":
			generateNEWARRAY(source, (InstructionNEWARRAY) instruction);
			break;
		case "ANEWARRAY":
			generateANEWARRAY(source, (InstructionANEWARRAY) instruction);
			break;
		case "ARRAYLENGTH":
			generateARRAYLENGTH(source, (InstructionARRAYLENGTH) instruction);
			break;
		case "ATHROW":
			generateATHROW(source, (InstructionATHROW) instruction);
			break;
		case "CHECKCAST":
			generateCHECKCAST(source, (InstructionCHECKCAST) instruction);
			break;
		case "INSTANCEOF":
			generateINSTANCEOF(source, (InstructionINSTANCEOF) instruction);
			break;
		case "MONITORENTER":
			generateMONITORENTER(source, (InstructionMONITORENTER) instruction);
			break;
		case "MONITOREXIT":
			generateMONITOREXIT(source, (InstructionMONITOREXIT) instruction);
			break;
		case "WIDE":
			generateWIDE(source, (InstructionWIDE) instruction);
			break;
		case "MULTIANEWARRAY":
			generateMULTIANEWARRAY(source, (InstructionMULTIANEWARRAY) instruction);
			break;
		case "IFNULL":
			generateIFNULL(source, (InstructionIFNULL) instruction);
			break;
		case "IFNONNULL":
			generateIFNONNULL(source, (InstructionIFNONNULL) instruction);
			break;
		case "GOTO_W":
			generateGOTO_W(source, (InstructionGOTO_W) instruction);
			break;
		case "JSR_W":
			generateJSR_W(source, (InstructionJSR_W) instruction);
			break;
		default:
			System.out.println("Instruction " + opcode + " execution not supported");
		}
	}

	private void generateAALOAD(IndentedOutputStream source, InstructionAALOAD instruction)
	{
		notSupported(instruction);
	}

	private void generateAASTORE(IndentedOutputStream source, InstructionAASTORE instruction)
	{
		String[] value = stack.pop().split(SplitStackEntrySeparator);
		String[] index = stack.pop().split(SplitStackEntrySeparator);
		String[] array = stack.pop().split(SplitStackEntrySeparator);
		Assert(array[0].startsWith("JavaArray<"), "ANEWARRAY: Array expected");
		Assert(index[0].equals("int"), "ANEWARRAY: Integer index expected");
		String arrayElementType = array[0].substring(10);
		int pos = arrayElementType.indexOf('*');
		Assert(pos > 0, "JavaArray element is not a pointer");
		arrayElementType = arrayElementType.substring(0, pos) + " *";
		Assert(value[0].equals(arrayElementType), "ANEWARRAY: Value does not match JavaArray type");
		source.iprintln("temp->assignString(" + index[1] + ", " + value[1] + ");");
	}

	private void generateACONST_NULL(IndentedOutputStream source, InstructionACONST_NULL instruction)
	{
		notSupported(instruction);
	}

	private void generateALOAD(IndentedOutputStream source, InstructionALOAD instruction)
	{
		int index = instruction.getIndex();
		Assert(index >= 0 && index < maxLocals, "ALOAD index out of range");
		Assert(locals[index] != null, "Local at " + index + " is null");
		Assert(locals[index].getEntry().length() > 0, "Local at " + index + " is not available");
		locals[index].push(getStack());
	}

	private void generateANEWARRAY(IndentedOutputStream source, InstructionANEWARRAY instruction)
	{
		String[] topOfStack = stack.pop().split(SplitStackEntrySeparator);
		Assert(topOfStack[0].equals("int"), "ANEWARRAY: Integer operand expected");
		String length = topOfStack[1];
		String type = instruction.getClassName();
		type = javaToCppClass(type);
		String simpleType = cppClass.simplifyType(type);
		isStringArray = type.equals("java::lang::String");
		if (isStringArray)
		{
			source.iprintln("{");
			source.indent(1);
			source.iprintln("JavaArray<String*> *temp = new JavaArray<" + simpleType + "*>(" + length + ");");
			String newEntry = "JavaArray<" + simpleType + "*>*" + StackEntrySeparator + "temp";
			stack.push(newEntry);
		}
		else
		{
			String newEntry = "JavaArray<" + simpleType + "*>*" + StackEntrySeparator + //
					"new JavaArray<" + simpleType + "*>(" + length + ")";
			stack.push(newEntry);
		}
	}

	private void generateARETURN(IndentedOutputStream source, InstructionARETURN instruction)
	{
		notSupported(instruction);
	}

	private void generateARRAYLENGTH(IndentedOutputStream source, InstructionARRAYLENGTH instruction)
	{
		String[] topOfStack = stack.pop().split(SplitStackEntrySeparator);
		// check if top[0] is an array
		Assert(topOfStack[0].startsWith("JavaArray<"), "Top of stack is not an array");
		stack.push("int" + StackEntrySeparator + topOfStack[1] + "->length");
	}

	private void generateASTORE(IndentedOutputStream source, InstructionASTORE instruction)
	{
		int index = instruction.getIndex();
		Assert(index >= 0 && index < maxLocals, "Local index out of range");
		String[] local = locals[index].getEntry().split(SplitStackEntrySeparator);
		String[] topOfStack = stack.pop().split(SplitStackEntrySeparator);
		if (local.length == 2)
		{
			Assert(local[0].equals(topOfStack[0]), "ASTORE: Type mismatch");
			source.iprintln(local[1] + " = " + topOfStack[1] + ";");
		}
		else
		{
			locals[index].set(topOfStack[0], "local" + index);
			String localType = topOfStack[0];
			Assert(localType.endsWith("*"), "ASTORE: Local pointer expected");
			source.iprintln(localType + " local" + index + " = " + topOfStack[1] + ";");
		}
		if (isStringArray)
		{
			source.indent(-1);
			source.iprintln("}");
		}
		isStringArray = false;
	}

	private void generateATHROW(IndentedOutputStream source, InstructionATHROW instruction)
	{
		notSupported(instruction);
	}

	private void generateBALOAD(IndentedOutputStream source, InstructionBALOAD instruction)
	{
		notSupported(instruction);
	}

	private void generateBASTORE(IndentedOutputStream source, InstructionBASTORE instruction)
	{
		notSupported(instruction);
	}

	private void generateBIPUSH(IndentedOutputStream source, InstructionBIPUSH instruction)
	{
		String stackEntry = "int" + StackEntrySeparator + instruction.getValue();
		stack.push(stackEntry);
	}

	private void generateCALOAD(IndentedOutputStream source, InstructionCALOAD instruction)
	{
		notSupported(instruction);
	}

	private void generateCASTORE(IndentedOutputStream source, InstructionCASTORE instruction)
	{
		notSupported(instruction);
	}

	private void generateCHECKCAST(IndentedOutputStream source, InstructionCHECKCAST instruction)
	{
		notSupported(instruction);
	}

	private void generateD2F(IndentedOutputStream source, InstructionD2F instruction)
	{
		notSupported(instruction);
	}

	private void generateD2I(IndentedOutputStream source, InstructionD2I instruction)
	{
		notSupported(instruction);
	}

	private void generateD2L(IndentedOutputStream source, InstructionD2L instruction)
	{
		notSupported(instruction);
	}

	private void generateDADD(IndentedOutputStream source, InstructionDADD instruction)
	{
		notSupported(instruction);
	}

	private void generateDALOAD(IndentedOutputStream source, InstructionDALOAD instruction)
	{
		notSupported(instruction);
	}

	private void generateDASTORE(IndentedOutputStream source, InstructionDASTORE instruction)
	{
		notSupported(instruction);
	}

	private void generateDCMPG(IndentedOutputStream source, InstructionDCMPG instruction)
	{
		notSupported(instruction);
	}

	private void generateDCMPL(IndentedOutputStream source, InstructionDCMPL instruction)
	{
		notSupported(instruction);
	}

	private void generateDCONST(IndentedOutputStream source, InstructionDCONST instruction)
	{
		notSupported(instruction);
	}

	private void generateDDIV(IndentedOutputStream source, InstructionDDIV instruction)
	{
		notSupported(instruction);
	}

	private void generateDLOAD(IndentedOutputStream source, InstructionDLOAD instruction)
	{
		notSupported(instruction);
	}

	private void generateDMUL(IndentedOutputStream source, InstructionDMUL instruction)
	{
		notSupported(instruction);
	}

	private void generateDNEG(IndentedOutputStream source, InstructionDNEG instruction)
	{
		notSupported(instruction);
	}

	private void generateDREM(IndentedOutputStream source, InstructionDREM instruction)
	{
		notSupported(instruction);
	}

	private void generateDRETURN(IndentedOutputStream source, InstructionDRETURN instruction)
	{
		notSupported(instruction);
	}

	private void generateDSTORE(IndentedOutputStream source, InstructionDSTORE instruction)
	{
		notSupported(instruction);
	}

	private void generateDSUB(IndentedOutputStream source, InstructionDSUB instruction)
	{
		notSupported(instruction);
	}

	private void generateDUP(IndentedOutputStream source, InstructionDUP instruction)
	{
		String top = stack.pop();
		stack.push(top);
		stack.push(top);
	}

	private void generateDUP_X1(IndentedOutputStream source, InstructionDUP_X1 instruction)
	{
		notSupported(instruction);
	}

	private void generateDUP_X2(IndentedOutputStream source, InstructionDUP_X2 instruction)
	{
		notSupported(instruction);
	}

	private void generateDUP2(IndentedOutputStream source, InstructionDUP2 instruction)
	{
		notSupported(instruction);
	}

	private void generateDUP2_X1(IndentedOutputStream source, InstructionDUP2_X1 instruction)
	{
		notSupported(instruction);
	}

	private void generateDUP2_X2(IndentedOutputStream source, InstructionDUP2_X2 instruction)
	{
		notSupported(instruction);
	}

	private void generateF2D(IndentedOutputStream source, InstructionF2D instruction)
	{
		notSupported(instruction);
	}

	private void generateF2I(IndentedOutputStream source, InstructionF2I instruction)
	{
		notSupported(instruction);
	}

	private void generateF2L(IndentedOutputStream source, InstructionF2L instruction)
	{
		notSupported(instruction);
	}

	private void generateFADD(IndentedOutputStream source, InstructionFADD instruction)
	{
		notSupported(instruction);
	}

	private void generateFALOAD(IndentedOutputStream source, InstructionFALOAD instruction)
	{
		notSupported(instruction);
	}

	private void generateFASTORE(IndentedOutputStream source, InstructionFASTORE instruction)
	{
		notSupported(instruction);
	}

	private void generateFCMPG(IndentedOutputStream source, InstructionFCMPG instruction)
	{
		notSupported(instruction);
	}

	private void generateFCMPL(IndentedOutputStream source, InstructionFCMPL instruction)
	{
		notSupported(instruction);
	}

	private void generateFCONST(IndentedOutputStream source, InstructionFCONST instruction)
	{
		notSupported(instruction);
	}

	private void generateFDIV(IndentedOutputStream source, InstructionFDIV instruction)
	{
		notSupported(instruction);
	}

	private void generateFLOAD(IndentedOutputStream source, InstructionFLOAD instruction)
	{
		notSupported(instruction);
	}

	private void generateFMUL(IndentedOutputStream source, InstructionFMUL instruction)
	{
		notSupported(instruction);
	}

	private void generateFNEG(IndentedOutputStream source, InstructionFNEG instruction)
	{
		notSupported(instruction);
	}

	private void generateFREM(IndentedOutputStream source, InstructionFREM instruction)
	{
		notSupported(instruction);
	}

	private void generateFRETURN(IndentedOutputStream source, InstructionFRETURN instruction)
	{
		notSupported(instruction);
	}

	private void generateFSTORE(IndentedOutputStream source, InstructionFSTORE instruction)
	{
		notSupported(instruction);
	}

	private void generateFSUB(IndentedOutputStream source, InstructionFSUB instruction)
	{
		notSupported(instruction);
	}

	private void generateGETFIELD(IndentedOutputStream source, InstructionGETFIELD instruction)
	{
		notSupported(instruction);
	}

	private void generateGETSTATIC(IndentedOutputStream source, InstructionGETSTATIC instruction)
	{
		notSupported(instruction);
	}

	private void generateGOTO(IndentedOutputStream source, InstructionGOTO instruction)
	{
		notSupported(instruction);
	}

	private void generateGOTO_W(IndentedOutputStream source, InstructionGOTO_W instruction)
	{
		notSupported(instruction);
	}

	private void generateI2B(IndentedOutputStream source, InstructionI2B instruction)
	{
		notSupported(instruction);
	}

	private void generateI2C(IndentedOutputStream source, InstructionI2C instruction)
	{
		notSupported(instruction);
	}

	private void generateI2D(IndentedOutputStream source, InstructionI2D instruction)
	{
		notSupported(instruction);
	}

	private void generateI2F(IndentedOutputStream source, InstructionI2F instruction)
	{
		notSupported(instruction);
	}

	private void generateI2L(IndentedOutputStream source, InstructionI2L instruction)
	{
		notSupported(instruction);
	}

	private void generateI2S(IndentedOutputStream source, InstructionI2S instruction)
	{
		notSupported(instruction);
	}

	private void generateIADD(IndentedOutputStream source, InstructionIADD instruction)
	{
		notSupported(instruction);
	}

	private void generateIALOAD(IndentedOutputStream source, InstructionIALOAD instruction)
	{
		notSupported(instruction);
	}

	private void generateIAND(IndentedOutputStream source, InstructionIAND instruction)
	{
		notSupported(instruction);
	}

	private void generateIASTORE(IndentedOutputStream source, InstructionIASTORE instruction)
	{
		notSupported(instruction);
	}

	private void generateICONST(IndentedOutputStream source, InstructionICONST instruction)
	{
		String newEntry = "int" + StackEntrySeparator + instruction.getValue();
		stack.push(newEntry);
	}

	private void generateIDIV(IndentedOutputStream source, InstructionIDIV instruction)
	{
		notSupported(instruction);
	}

	private void generateIF_ACMPEQ(IndentedOutputStream source, InstructionIF_ACMPEQ instruction)
	{
		notSupported(instruction);
	}

	private void generateIF_ACMPNE(IndentedOutputStream source, InstructionIF_ACMPNE instruction)
	{
		notSupported(instruction);
	}

	private void generateIF_ICMPEQ(IndentedOutputStream source, InstructionIF_ICMPEQ instruction)
	{
		notSupported(instruction);
	}

	private void generateIF_ICMPGE(IndentedOutputStream source, InstructionIF_ICMPGE instruction)
	{
		notSupported(instruction);
	}

	private void generateIF_ICMPGT(IndentedOutputStream source, InstructionIF_ICMPGT instruction)
	{
		notSupported(instruction);
	}

	private void generateIF_ICMPLE(IndentedOutputStream source, InstructionIF_ICMPLE instruction)
	{
		notSupported(instruction);
	}

	private void generateIF_ICMPLT(IndentedOutputStream source, InstructionIF_ICMPLT instruction)
	{
		notSupported(instruction);
	}

	private void generateIF_ICMPNE(IndentedOutputStream source, InstructionIF_ICMPNE instruction)
	{
		notSupported(instruction);
	}

	private void generateIFEQ(IndentedOutputStream source, InstructionIFEQ instruction)
	{
		notSupported(instruction);
	}

	private void generateIFGE(IndentedOutputStream source, InstructionIFGE instruction)
	{
		notSupported(instruction);
	}

	private void generateIFGT(IndentedOutputStream source, InstructionIFGT instruction)
	{
		notSupported(instruction);
	}

	private void generateIFLE(IndentedOutputStream source, InstructionIFLE instruction)
	{
		notSupported(instruction);
	}

	private void generateIFLT(IndentedOutputStream source, InstructionIFLT instruction)
	{
		notSupported(instruction);
	}

	private void generateIFNE(IndentedOutputStream source, InstructionIFNE instruction)
	{
		String[] topOfStack = stack.pop().split(SplitStackEntrySeparator);
		Assert(topOfStack[0].equals("int"), "IFNE: Integer operand expected");
		source.iprintln("if ((" + topOfStack[1] + ") != 0)");
		source.iprintln("\tgoto " + instruction.getLabel() + ";");
	}

	private void generateIFNONNULL(IndentedOutputStream source, InstructionIFNONNULL instruction)
	{
		notSupported(instruction);
	}

	private void generateIFNULL(IndentedOutputStream source, InstructionIFNULL instruction)
	{
		notSupported(instruction);
	}

	private void generateIINC(IndentedOutputStream source, InstructionIINC instruction)
	{
		notSupported(instruction);
	}

	private void generateILOAD(IndentedOutputStream source, InstructionILOAD instruction)
	{
		notSupported(instruction);
	}

	private void generateIMUL(IndentedOutputStream source, InstructionIMUL instruction)
	{
		notSupported(instruction);
	}

	private void generateINEG(IndentedOutputStream source, InstructionINEG instruction)
	{
		notSupported(instruction);
	}

	private void generateINSTANCEOF(IndentedOutputStream source, InstructionINSTANCEOF instruction)
	{
		notSupported(instruction);
	}

	private void generateINVOKEDYNAMIC(IndentedOutputStream source, InstructionINVOKEDYNAMIC instruction)
	{
		notSupported(instruction);
	}

	private void generateINVOKEINTERFACE(IndentedOutputStream source, InstructionINVOKEINTERFACE instruction)
	{
		notSupported(instruction);
	}

	/**
	 * Invoke a special method: special method is a class constructor or a TBD
	 * method.
	 */
	private void generateINVOKESPECIAL(IndentedOutputStream source, InstructionINVOKESPECIAL instruction)
	{
		String methodClassName = javaToCppClass(instruction.getMethodClassName());
		String methodName = instruction.getMethodName();
		String[] topOfStack = stack.pop().split(SplitStackEntrySeparator);
		// 1. We are invoking the constructor of the base class from constructor of
		// derived class? This statement is generated from special context of declaring
		// a constructor for the derived class.
		if (methodName.equals("<init>") && topOfStack[0].equals(classType) && topOfStack[1].equals("this")
				&& methodClassName.equals(cppClass.parentClassName))
		{
			String simpleBaseClassName = cppClass.simplifyType(methodClassName);
			String descriptor = instruction.getMethodDescriptor();
			Assert(descriptor.startsWith("()"), "Super constructor parameter list not supported");
			source.iprintln(simpleBaseClassName + "()");
		}
		else
		{
			String newClassName = cppClass.simplifyType(javaToCppClass(instruction.getMethodClassName()));
			Assert(topOfStack[0].equals(newClassName + "*") && topOfStack[1].equals("new"), "Bad stack top");
			Assert(instruction.getMethodDescriptor().equals("()V"), "Constructor with parameters not supported");
			String[] dupInStack = stack.pop().split(SplitStackEntrySeparator);
			Assert(dupInStack[0].equals(newClassName + "*") && dupInStack[1].equals("new"), "Bad stack DUP");
			String newStackTop = dupInStack[0] + StackEntrySeparator + "new "
					+ dupInStack[0].substring(0, dupInStack[0].length() - 1) + "()";
			stack.push(newStackTop);
		}
	}

	private void generateINVOKESTATIC(IndentedOutputStream source, InstructionINVOKESTATIC instruction)
	{
		notSupported(instruction);
	}

	private void generateINVOKEVIRTUAL(IndentedOutputStream source, InstructionINVOKEVIRTUAL instruction)
	{
		notSupported(instruction);
	}

	private void generateIOR(IndentedOutputStream source, InstructionIOR instruction)
	{
		notSupported(instruction);
	}

	private void generateIREM(IndentedOutputStream source, InstructionIREM instruction)
	{
		notSupported(instruction);
	}

	private void generateIRETURN(IndentedOutputStream source, InstructionIRETURN instruction)
	{
		notSupported(instruction);
	}

	private void generateISHL(IndentedOutputStream source, InstructionISHL instruction)
	{
		notSupported(instruction);
	}

	private void generateISHR(IndentedOutputStream source, InstructionISHR instruction)
	{
		notSupported(instruction);
	}

	private void generateISTORE(IndentedOutputStream source, InstructionISTORE instruction)
	{
		notSupported(instruction);
	}

	private void generateISUB(IndentedOutputStream source, InstructionISUB instruction)
	{
		notSupported(instruction);
	}

	private void generateIUSHR(IndentedOutputStream source, InstructionIUSHR instruction)
	{
		notSupported(instruction);
	}

	private void generateIXOR(IndentedOutputStream source, InstructionIXOR instruction)
	{
		notSupported(instruction);
	}

	private void generateJSR(IndentedOutputStream source, InstructionJSR instruction)
	{
		notSupported(instruction);
	}

	private void generateJSR_W(IndentedOutputStream source, InstructionJSR_W instruction)
	{
		notSupported(instruction);
	}

	private void generateL2D(IndentedOutputStream source, InstructionL2D instruction)
	{
		notSupported(instruction);
	}

	private void generateL2F(IndentedOutputStream source, InstructionL2F instruction)
	{
		notSupported(instruction);
	}

	private void generateL2I(IndentedOutputStream source, InstructionL2I instruction)
	{
		notSupported(instruction);
	}

	private void generateLADD(IndentedOutputStream source, InstructionLADD instruction)
	{
		notSupported(instruction);
	}

	private void generateLALOAD(IndentedOutputStream source, InstructionLALOAD instruction)
	{
		notSupported(instruction);
	}

	private void generateLAND(IndentedOutputStream source, InstructionLAND instruction)
	{
		notSupported(instruction);
	}

	private void generateLASTORE(IndentedOutputStream source, InstructionLASTORE instruction)
	{
		notSupported(instruction);
	}

	private void generateLCMP(IndentedOutputStream source, InstructionLCMP instruction)
	{
		notSupported(instruction);
	}

	private void generateLCONST(IndentedOutputStream source, InstructionLCONST instruction)
	{
		notSupported(instruction);
	}

	private void generateLDC(IndentedOutputStream source, InstructionLDC instruction)
	{
		Constant constant = instruction.getConstant();
		String newEntry = "";
		if (constant instanceof ConstantStringInfo stringValue)
		{
			String type = cppClass.simplifyType("java::lang::String");
			newEntry = type + " *" + StackEntrySeparator + "\"" + stringValue.getString() + "\"";
		}
		else if (constant instanceof ConstantInteger intValue)
		{
			newEntry = type + " *" + StackEntrySeparator + intValue.getValue();
		}
		else if (constant instanceof ConstantFloat floatValue)
		{
			newEntry = type + " *" + StackEntrySeparator + floatValue.getValue();
		}
		else
		{
			throw new RuntimeException(
					"LDC: Constant type " + constant.getClass().getSimpleName().substring(8) + " not implemented");
		}
		Assert(newEntry.length() > 0, "LDC: Empty constant");
		stack.push(newEntry);
	}

	private void generateLDC_W(IndentedOutputStream source, InstructionLDC_W instruction)
	{
		notSupported(instruction);
	}

	private void generateLDC2_W(IndentedOutputStream source, InstructionLDC2_W instruction)
	{
		notSupported(instruction);
	}

	private void generateLDIV(IndentedOutputStream source, InstructionLDIV instruction)
	{
		notSupported(instruction);
	}

	private void generateLLOAD(IndentedOutputStream source, InstructionLLOAD instruction)
	{
		notSupported(instruction);
	}

	private void generateLMUL(IndentedOutputStream source, InstructionLMUL instruction)
	{
		notSupported(instruction);
	}

	private void generateLNEG(IndentedOutputStream source, InstructionLNEG instruction)
	{
		notSupported(instruction);
	}

	private void generateLOOKUPSWITCH(IndentedOutputStream source, InstructionLOOKUPSWITCH instruction)
	{
		notSupported(instruction);
	}

	private void generateLOR(IndentedOutputStream source, InstructionLOR instruction)
	{
		notSupported(instruction);
	}

	private void generateLREM(IndentedOutputStream source, InstructionLREM instruction)
	{
		notSupported(instruction);
	}

	private void generateLRETURN(IndentedOutputStream source, InstructionLRETURN instruction)
	{
		notSupported(instruction);
	}

	private void generateLSHL(IndentedOutputStream source, InstructionLSHL instruction)
	{
		notSupported(instruction);
	}

	private void generateLSHR(IndentedOutputStream source, InstructionLSHR instruction)
	{
		notSupported(instruction);
	}

	private void generateLSTORE(IndentedOutputStream source, InstructionLSTORE instruction)
	{
		notSupported(instruction);
	}

	private void generateLSUB(IndentedOutputStream source, InstructionLSUB instruction)
	{
		notSupported(instruction);
	}

	private void generateLUSHR(IndentedOutputStream source, InstructionLUSHR instruction)
	{
		notSupported(instruction);
	}

	private void generateLXOR(IndentedOutputStream source, InstructionLXOR instruction)
	{
		notSupported(instruction);
	}

	private void generateMONITORENTER(IndentedOutputStream source, InstructionMONITORENTER instruction)
	{
		notSupported(instruction);
	}

	private void generateMONITOREXIT(IndentedOutputStream source, InstructionMONITOREXIT instruction)
	{
		notSupported(instruction);
	}

	private void generateMULTIANEWARRAY(IndentedOutputStream source, InstructionMULTIANEWARRAY instruction)
	{
		notSupported(instruction);
	}

	private void generateNEW(IndentedOutputStream source, InstructionNEW instruction)
	{
		String className = javaToCppClass(instruction.getClassName());
		String simpleClassName = cppClass.simplifyType(className);
		stack.push(simpleClassName + "*" + StackEntrySeparator + "new");
	}

	private void generateNEWARRAY(IndentedOutputStream source, InstructionNEWARRAY instruction)
	{
		notSupported(instruction);
	}

	private void generateNOP(IndentedOutputStream source, InstructionNOP instruction)
	{
		notSupported(instruction);
	}

	private void generatePOP(IndentedOutputStream source, InstructionPOP instruction)
	{
		notSupported(instruction);
	}

	private void generatePOP2(IndentedOutputStream source, InstructionPOP2 instruction)
	{
		notSupported(instruction);
	}

	private void generatePUTFIELD(IndentedOutputStream source, InstructionPUTFIELD instruction)
	{
		String[] value = stack.pop().split(SplitStackEntrySeparator);
		String[] object = stack.pop().split(SplitStackEntrySeparator);
		Assert(object[0].equals(cppClass.getFullClassName()) && object[1].equals("this"),
				"Assigning to non-this class " + object[0] + " field " + instruction.getFieldName());
		CppField field = cppClass.getField(instruction.getFieldName());
		String fieldType = new JavaTypeConverter(instruction.getFieldType(), false).getCppType();
		String actualType = field.getType();
		String baseType = removeStar(fieldType);
		if (actualType.startsWith(baseType + "<") && actualType.endsWith(">*"))
		{
			if (value[1].startsWith("new " + removeStar(value[0]) + "("))
			{
				value[1] = "new " + cppClass.simplifyType(removeStar(actualType))
						+ value[1].substring(value[1].indexOf("("));
			}
			else
			{
				Assert(false, "Don't know what is going on yet");
			}
		}
		source.iprint("");
		source.print(object[1] + "->");
		source.println(field.getName() + " = " + value[1] + ";");
	}

	private void generatePUTSTATIC(IndentedOutputStream source, InstructionPUTSTATIC instruction)
	{
		notSupported(instruction);
	}

	private void generateRET(IndentedOutputStream source, InstructionRET instruction)
	{
		notSupported(instruction);
	}

	private void generateRETURN(IndentedOutputStream source, InstructionRETURN instruction)
	{
		source.iprintln("return;");
	}

	private void generateSALOAD(IndentedOutputStream source, InstructionSALOAD instruction)
	{
		notSupported(instruction);
	}

	private void generateSASTORE(IndentedOutputStream source, InstructionSASTORE instruction)
	{
		notSupported(instruction);
	}

	private void generateSIPUSH(IndentedOutputStream source, InstructionSIPUSH instruction)
	{
		notSupported(instruction);
	}

	private void generateSWAP(IndentedOutputStream source, InstructionSWAP instruction)
	{
		notSupported(instruction);
	}

	private void generateTABLESWITCH(IndentedOutputStream source, InstructionTABLESWITCH instruction)
	{
		notSupported(instruction);
	}

	private void generateWIDE(IndentedOutputStream source, InstructionWIDE instruction)
	{
		notSupported(instruction);
	}

	private void notSupported(Instruction instruction)
	{
		String opcode = instruction.getClass().getSimpleName().substring(11);
//		System.out.println("Instruction " + opcode + " execution not supported");
		Assert(false, "Instruction " + opcode + " execution not supported");
	}

	private void parseParameters()
	{
		// Skip starting '(', we're in the method
		String parameterList = cppType.substring(1);
		for (int i = 0; i < maxLocals; i++)
		{
			int parameterPos = parameterList.indexOf("param" + i);
			if (parameterPos != -1)
			{
				String parameterType = parameterList.substring(0, parameterPos);
				parameterType = cppClass.simplifyType(parameterType);
				if (parameterType.endsWith("*"))
				{
					parameterType = parameterType.substring(0, parameterType.length() - 2) + "*";
				}
				locals[i].set(parameterType, "param" + i);
			}
		}
	}
}
