package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class used to describe command that rotates the turtle for given angle.
 * @author Alex
 *
 */
public class RotateCommand implements Command {

	/**
	 * Angle of rotation.
	 */
	private double angle;
	
	/**
	 * Constructor.
	 * @param angle in degrees.
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	/**
	 * Rotates current direction of turtle for given angle.
	 * @throws EmptyStackException if stack in context is empty
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setDirection(
				ctx.getCurrentState().getDirection().rotated(this.angle));
	}
	
	/**
	 * Getter for field angle.
	 * @return
	 */
	public double getAngle() {
		return angle;
	}

}
