package hr.fer.zemris.java.gui.calc;

/**
 * Functional interface that defines listeners of {@link CalcModel}.
 * @author Alex
 *
 */
public interface CalcValueListener {
	
	/**
	 * Performs an action each time current value of {@link CalcModel} changes.
	 * @param model
	 */
	void valueChanged(CalcModel model);
}