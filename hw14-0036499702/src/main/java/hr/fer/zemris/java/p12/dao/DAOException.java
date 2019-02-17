package hr.fer.zemris.java.p12.dao;

/**
 * Exceptions thrown by DAO when an error occurs while working with database.
 * @author Alex
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * Serial generated UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with no arguments.
	 */
	public DAOException() {
	}

	/**
	 * Constructor.
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

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

	/**
	 * Constructor.
	 * @param cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}