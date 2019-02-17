package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class used to describe a command that pushes a state on the top of the stack.
 * @author Alex
 *
 */
public class PushCommand implements Command {

	/**
	 * Pushes a copy of the TurtleState that is currently on the top of the stack.
	 * @throws EmptyStackException if stack in context is empty
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copyOf());
	}

}
