package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class used to describe command that pops a state from context.
 * @author Alex
 *
 */
public class PopCommand implements Command {
	
	/**
	 * Pops TurtleState from the top of the stack.
	 * @throws EmptyStackException if stack in context is empty
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
