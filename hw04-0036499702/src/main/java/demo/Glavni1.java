package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Class used to demonstrate Koch curve by creating and configuring LSystemBuilder with implemented methods.
 * @author Alex
 *
 */
public class Glavni1 {

	/**
	 * Main method that invokes method that creates Koch curve.
	 * @param args - not needed in this method
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
		
	}

	/**
	 * Method that creates LSystemBuilder that represents Koch curve.
	 * @param provider LSystemBuilderProvider
	 * @return LSystem - Koch curve
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
				.registerCommand('F', "draw 1")
				.registerCommand('+', "rotate 60")
				.registerCommand('-', "rotate -60")
				.setOrigin(0.05, 0.4)
				.setAngle(0)
				.setUnitLength(0.9)
				.setUnitLengthDegreeScaler(1.0 / 3.0)
				.registerProduction('F', "F+F--F+F")
				.setAxiom("F")
				.build();
	}

}
