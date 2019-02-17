package hr.fer.zemris.java.gui.calc.Calculator.operations;

import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.Calculator.Calculator;

/**
 * Class that contains all static binary operations supported by {@link Calculator}.
 * All results are double values.
 * @author Alex
 *
 */
public class BinaryOperations {
	
	/**
	 * Binary operation that adds two numbers.
	 */
	public static final DoubleBinaryOperator ADD = (op1, op2) -> op1 + op2;
	
	/**
	 * Binary operation that subtracts two numbers.
	 */
	public static final DoubleBinaryOperator SUB = (op1, op2) -> op1 - op2;
	
	/**
	 * Binary operation that multiplies two numbers.
	 */
	public static final DoubleBinaryOperator MUL = (op1, op2) -> op1 * op2;
	
	/**
	 * Binary operation that divides two values.
	 */
	public static final DoubleBinaryOperator DIV = (op1, op2) -> op1 / op2;

	/**
	 * Binary operation that calculates op1^op2.
	 */
	public static final DoubleBinaryOperator POW = (op1, op2) -> Math.pow(op1, op2);
	
	/**
	 * Inverse of POW. Calculates op2-th root of op1.
	 */
	public static final DoubleBinaryOperator ROOT = (op1, op2) -> Math.pow(op1, 1.0 / op2);
}
