package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class used to describe a command that skips a step. It moves the turtle, but it doesn't draw a line.
 * @author Alex
 *
 */
public class SkipCommand implements Command {
	
	/**
	 * Step to be skipped.
	 */
	private double step;

	/**
	 * Constructor.
	 * @param step
	 */
	public SkipCommand(double step) {
		super();
		this.step = step;
	}

	/**
	 * Changes the position of turtle without drawing a line.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setPosition(
				ctx.getCurrentState().getPosition().translated(
						ctx.getCurrentState().getDirection().scaled(this.step)
							.scaled(ctx.getCurrentState().getEffectiveStep())));
	}
	
	/**
	 * Getter for field step.
	 * @return
	 */
	public double getStep() {
		return step;
	}

}
