package hr.fer.zemris.java.custom.collections;

/**
 * Class used to define exception that occurs when an action is invoked over an empty stack.
 * @author Alex
 *
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates an exception.
	 */
	public EmptyStackException() {
		super();
	}
	
	/**
	 * Creates an exception.
	 * @param message is displayed when this exception occurs.
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
}
