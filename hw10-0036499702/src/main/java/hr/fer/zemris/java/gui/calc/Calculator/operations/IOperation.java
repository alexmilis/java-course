package hr.fer.zemris.java.gui.calc.Calculator.operations;

import hr.fer.zemris.java.gui.calc.Calculator.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.Calculator.Calculator;

/**
 * Functional interface for operations that take no numbers as arguments.
 * @author Alex
 *
 */
public interface IOperation {
	
	/**
	 * Executes operation. 
	 * @param model model of calculator.
	 * @param calc calculator
	 */
	public void execute(CalcModelImpl model, Calculator calc);
	
}
