package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Interface that declares all methods that are needed to implement a model for calculator.
 * @author Alex
 *
 */
public interface CalcModel {
	
	/**
	 * Adds {@link CalcValueListener} to model's list of listeners. 
	 * @param l
	 */
	void addCalcValueListener(CalcValueListener l);
	
	/**
	 * Removes {@link CalcValueListener} from list of listeners.
	 * @param l
	 */
	void removeCalcValueListener(CalcValueListener l);
	
	/**
	 * Returns string representation of current value stored.
	 * @return value as string
	 */
	String toString();
	
	/**
	 * Returns current value.
	 * @return double value
	 */
	double getValue();
	
	/**
	 * Sets current value to given value.
	 * @param value
	 */
	void setValue(double value);
	
	/**
	 * Clears current value.
	 */
	void clear();
	
	/**
	 * Clears current value, active operand and pending operation.
	 */
	void clearAll();
	
	/**
	 * Changes the sign of current value.
	 */
	void swapSign();
	
	/**
	 * Inserts decimal point into current value. 
	 * If value already contains decimal point, it does nothing.
	 */
	void insertDecimalPoint();
	
	/**
	 * Adds digit to the end of current value.
	 * It removes leading zeros.
	 * @param digit
	 */
	void insertDigit(int digit);
	
	/**
	 * Checks if active operand is set.
	 * @return true if it is set
	 */
	boolean isActiveOperandSet();
	
	/**
	 * Returns active operand.
	 * @return active operand
	 */
	double getActiveOperand();
	
	/**
	 * Sets active operand to given value.
	 * @param activeOperand
	 */
	void setActiveOperand(double activeOperand);
	
	/**
	 * Clears active operand.
	 */
	void clearActiveOperand();
	
	/**
	 * Returns pending operation.
	 * @return double binary operation
	 */
	DoubleBinaryOperator getPendingBinaryOperation();
	
	/**
	 * Sets pending operation.
	 * @param op pending operation
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}