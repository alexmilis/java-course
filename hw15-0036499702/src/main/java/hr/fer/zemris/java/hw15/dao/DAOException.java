package hr.fer.zemris.java.hw15.dao;

/**
 * Exception thrown by DAO if requested operation over database cannot be executed.
 * 
 * @author Alex
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * @param message
	 * @param cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * @param message
	 */
	public DAOException(String message) {
		super(message);
	}
}