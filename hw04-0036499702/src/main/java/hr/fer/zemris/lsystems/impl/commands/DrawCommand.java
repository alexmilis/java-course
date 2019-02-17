package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * Class used to describe command that draws a line.
 * @author Alex
 *
 */
public class DrawCommand implements Command {

	/**
	 * Length of line.
	 */
	private double step;
	
	/**
	 * Constructor.
	 * @param step
	 */
	public DrawCommand(double step) {
		super();
		this.step = step;
	}

	/**
	 * Method that calculates next position of turtle and draws the line.
	 * @throws EmptyStackException if stack in context is empty
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		Vector2D position = ctx.getCurrentState().getPosition();
		Vector2D newPosition = position.translated(ctx.getCurrentState().getDirection().scaled(this.step)
				.scaled(ctx.getCurrentState().getEffectiveStep()));
		painter.drawLine(position.getX(), position.getY(), newPosition.getX(), newPosition.getY(), ctx.getCurrentState().getColor(), 1f);
		ctx.getCurrentState().setPosition(newPosition);
	}
	
	/**
	 * Getter for field step.
	 * @return step
	 */
	public double getStep() {
		return step;
	}

}
