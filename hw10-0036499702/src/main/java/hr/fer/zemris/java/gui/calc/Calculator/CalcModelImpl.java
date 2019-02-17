package hr.fer.zemris.java.gui.calc.Calculator;

import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.CalcValueListener;

/**
 * Implementation of {@link CalcModel} for {@link Calculator}.
 * Implements inherited methods.
 * Stores three values:	current value
 * 						active operand
 * 						pending operation
 * @author Alex
 *
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * Current value.
	 */
	private String number;
	
	/**
	 * Active operand. It is used in pending binary operations.
	 */
	private String activeOperand;
	
	/**
	 * Pending binary operation.
	 */
	private DoubleBinaryOperator pendingOperation;
		
	/**
	 * List of {@link CalcValueListener} that are notified every time current value changes.
	 */
	private List<CalcValueListener> listeners;
	
	/**
	 * Constructor. 
	 */
	public CalcModelImpl() {
		super();
		this.number = null;
		this.listeners = new LinkedList<>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	
	@Override
	public double getValue() {
		if(number == null) {
			return 0.0;
		}
		return Double.parseDouble(number);
	}

	@Override
	public void setValue(double value) {
		if(!Double.isNaN(value) && !Double.isInfinite(value)) {
			number = Double.toString(value);
			listeners.forEach(l -> l.valueChanged(this));
		}
	}

	@Override
	public void clear() {
		number = null;
		listeners.forEach(l -> l.valueChanged(this));
	}

	@Override
	public void clearAll() {
		clear();
		clearActiveOperand();
		pendingOperation = null;
	}

	@Override
	public void swapSign() {
		if(number == null) {
			return;
		}
		if(number.length() != 0) {
			if(number.startsWith("-")) {
				number = number.substring(1);
			} else {
				number = "-" + number;
			}
			listeners.forEach(l -> l.valueChanged(this));
		}
	}

	@Override
	public void insertDecimalPoint() {
		if(number == null) {
			number = "0.";
		}
		if(!number.contains(".")) {
			number += ".";
		}
	}

	@Override
	public void insertDigit(int digit) {
		if(number == null) {
			number = digit + "";
		} else if(!(number.equals("0") && digit == 0)){
			if(number.startsWith("0") && !number.contains(".")) {
				number = number.substring(1);
			}
			if(Double.parseDouble(number + digit) > Double.MAX_VALUE) {
				return;
			}
			number += digit;
		}
		listeners.forEach(l -> l.valueChanged(this));
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalStateException if active operand is not set.
	 */
	@Override
	public double getActiveOperand() {
		if(isActiveOperandSet()) {
			return Double.parseDouble(activeOperand);
		}
		throw new IllegalStateException("Active operand is not set!");
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand + "";
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}
	
	@Override
	public String toString() {
		if(number == null) {
			return "0";
		}
		return number;
	}

}
