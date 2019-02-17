package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class used to describe command that scales current effective step.
 * @author Alex
 *
 */
public class ScaleCommand implements Command {

	/**
	 * Factor of scaling.
	 */
	private double factor;
	
	/**
	 * Constructor.
	 * @param factor
	 */
	public ScaleCommand(double factor) {
		super();
		this.factor = factor;
	}

	/**
	 * Scales current effective step with given factor.
	 * @throws EmptyStackException if stack in context is empty
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setEffectiveStep(
				ctx.getCurrentState().getEffectiveStep() * factor);
	}

	/**
	 * Getter for field factor.
	 * @return
	 */
	public double getFactor() {
		return factor;
	}
}
