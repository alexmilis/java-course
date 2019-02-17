package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class used to describe context of commands.
 * @author Alex
 *
 */
public class Context {
	
	/**
	 * Stack on which TurtleStates are stored.
	 */
	private ObjectStack stack;
	
	/**
	 * Constructor.
	 */
	public Context() {
		this.stack = new ObjectStack();
	}
	
	/**
	 * Returns state that is on the top of the stack.
	 * @return TurtleState
	 * @throws EmptyStackException if stack is empty
	 */
	public TurtleState getCurrentState() {
		return (TurtleState) stack.peek();
	}
	
	/**
	 * Puts state on the top of the stack.
	 * @param state TurtleState
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Pops a state from the top of the stack.
	 * @throws EmptyStackException if stack is empty.
	 */
	public void popState() {
		stack.pop();
	}

}
