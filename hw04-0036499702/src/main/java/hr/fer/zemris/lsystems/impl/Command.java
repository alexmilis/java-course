package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface that contains method essential for all commands.
 * @author Alex
 *
 */
public interface Command {
	
	/**
	 * Method that executes command. Each class that implements this interface has its own implementation of this method.
	 * @param ctx context of command, stack
	 * @param painter 
	 */
	void execute(Context ctx, Painter painter);

}
