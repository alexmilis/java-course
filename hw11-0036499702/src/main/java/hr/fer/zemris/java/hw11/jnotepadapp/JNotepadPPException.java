package hr.fer.zemris.java.hw11.jnotepadapp;

/**
 * Class that represents exceptions thrown ba {@link JNotepadPP}.
 * @author Alex
 *
 */
public class JNotepadPPException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public JNotepadPPException() {
	}

	/**
	 * Constructor.
	 * @param message
	 */
	public JNotepadPPException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * @param cause
	 */
	public JNotepadPPException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor.
	 * @param message
	 * @param cause
	 */
	public JNotepadPPException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public JNotepadPPException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
