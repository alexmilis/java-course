package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class used to describe command that changes the color of turtle.
 * @author Alex
 *
 */
public class ColorCommand implements Command {

	/**
	 * Color of turtle.
	 */
	private Color color;
	
	/**
	 * Constuctor.
	 * @param color
	 */
	public ColorCommand(Color color) {
		super();
		this.color = color;
	}

	/**
	 * Method that changes the color of turtle.
	 * @throws EmptyStackException if stack in context is empty
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(this.color);
	}

	/**
	 * Getter for field color.
	 * @return color
	 */
	public Color getColor() {
		return color;
	}
}
