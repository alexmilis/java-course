package hr.fer.zemris.java.gui.calc.Calculator.operations;

import javax.swing.JOptionPane;

/**
 * Static implementations of {@link IOperation}.
 * @author Alex
 *
 */
public class Operations {	
	
	/**
	 * Executes pending operation and stored obtained value as current value of model.
	 */
	public final static IOperation EQUALS = (model, calc) -> model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
	
	/**
	 * Clears current value.
	 */
	public final static IOperation CLR = (model, calc) -> model.clear();
	
	/**
	 * Resets the calculator. Clears current value, active operand and pending operation.
	 */
	public final static IOperation RES = (model, calc) -> model.clearAll();
	
	/**
	 * Pushes current value on stack.
	 */
	public final static IOperation PUSH = (model, calc) -> calc.getStack().push(model.getValue());
	
	/**
	 * Pops a value from stack and sets it as model's current value.
	 * If stack is empty, it shows the user an appropriate dialog box.
	 */
	public final static IOperation POP = (model, calc) -> {
		if(!calc.getStack().isEmpty()) {
			model.setValue(calc.getStack().pop());
		} else {
			JOptionPane.showMessageDialog(calc, "Stack is empty, cannot pop!", "Empty stack", JOptionPane.WARNING_MESSAGE);
		}
	};
	
	/**
	 * Changes the sign of current value.
	 */
	public final static IOperation SWAP = (model, calc) -> model.swapSign();
	
}
