package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Class used to demonstrate creating of LSystems from files.
 * @author Alex
 *
 */
public class Glavni3 {

	/**
	 * Method that creates and shows LSystem.
	 * @param args - not needed
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
	
}
