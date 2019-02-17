package hr.fer.zemris.java.gui.calc.Calculator.operations;

import java.util.function.DoubleUnaryOperator;

/**
 * Class that contains static operations that require only one argument.
 * All results are double values.
 * @author Alex
 *
 */
public class UnaryOperations {

	/**
	 * Calculates 1/op.
	 */
	public static final DoubleUnaryOperator ONE_DIVIDED_BY_X = op -> 1.0 / op;
	
	/**
	 * Calculates log10(op).
	 */
	public static final DoubleUnaryOperator LOG = op -> Math.log10(op);
	
	/**
	 * Calculates ln(op).
	 */
	public static final DoubleUnaryOperator LN = op -> Math.log(op);
	
	/**
	 * Calculates sin(op).
	 */
	public static final DoubleUnaryOperator SIN = op -> Math.sin(op);
	
	/**
	 * Calculates cos(op).
	 */
	public static final DoubleUnaryOperator COS = op -> Math.cos(op);
	
	/**
	 * Calculates tan(op).
	 */
	public static final DoubleUnaryOperator TAN = op -> Math.tan(op);
	
	/**
	 * Calculates ctg(op).
	 */
	public static final DoubleUnaryOperator CTG = op -> 1.0 / Math.tan(op);
	
	
	
	//inverse functions
	
	/**
	 * Inverse of LOG -> calculates 10^op.
	 */
	public static final DoubleUnaryOperator POW_TEN = op -> Math.pow(10, op);
	
	/**
	 * Inverse of LN -> calculates 2^op.
	 */
	public static final DoubleUnaryOperator POW_TWO = op -> Math.pow(2, op);
	
	/**
	 * Inverse of SIN -> calculates arcsin(op).
	 */
	public static final DoubleUnaryOperator ASIN = op -> Math.asin(op);
	
	/**
	 * Inverse of COS -> calculates arccos(op).
	 */
	public static final DoubleUnaryOperator ACOS = op -> Math.acos(op);
	
	/**
	 * Inverse of TAN -> calculates arctan(op).
	 */
	public static final DoubleUnaryOperator ATAN = op -> Math.atan(op);
	
	/**
	 * Inverse of CTG -> calculates arcctg(op).
	 */
	public static final DoubleUnaryOperator ACTG = op -> Math.atan(1.0 / op);
	
}
