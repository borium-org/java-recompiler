package org.borium.javarecompiler.classfile.instruction;

import java.io.*;

/**
 * Create new array.
 */
public class InstructionNEWARRAY extends Instruction
{
	/**
	 * The atype is a code that indicates the type of array to create. It must take
	 * one of the following values: <table border>
	 * <tr>
	 * <th>Array Type</th>
	 * <th>atype</th>
	 * </tr>
	 * <tr>
	 * <td>T_BOOLEAN</td>
	 * <td>4</td>
	 * </tr>
	 * <tr>
	 * <td>T_CHAR</td>
	 * <td>5</td>
	 * </tr>
	 * <tr>
	 * <td>T_FLOAT</td>
	 * <td>6</td>
	 * </tr>
	 * <tr>
	 * <td>T_DOUBLE</td>
	 * <td>7</td>
	 * </tr>
	 * <tr>
	 * <td>T_BYTE</td>
	 * <td>8</td>
	 * </tr>
	 * <tr>
	 * <td>T_SHORT</td>
	 * <td>9</td>
	 * </tr>
	 * <tr>
	 * <td>T_INT</td>
	 * <td>10</td>
	 * </tr>
	 * <tr>
	 * <td>T_LONG</td>
	 * <td>11</td>
	 * </tr>
	 * </table>
	 */
	@SuppressWarnings("unused")
	private int atype;

	public InstructionNEWARRAY(ByteArrayInputStream in)
	{
		atype = in.read();
	}

	@Override
	public int length()
	{
		return 2;
	}
}
