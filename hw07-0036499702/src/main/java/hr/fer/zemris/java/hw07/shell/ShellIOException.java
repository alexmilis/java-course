package hr.fer.zemris.java.hw07.shell;

/**
 * Exception thrown by {@link MyShell} and additional classes if 
 * there has been a problem with input or output.
 * @author Alex
 *
 */
public class ShellIOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShellIOException() {
		// TODO Auto-generated constructor stub
	}

	public ShellIOException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ShellIOException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ShellIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
