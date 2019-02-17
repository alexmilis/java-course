package hr.fer.zemris.java.hw03.prob1;

/**
 * Class used to describe exceptions that occur in class Lexer.
 * @author Alex
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of LexerException.
	 * @param arg0 message that should be displayed when exception occurs.
	 */
	public LexerException(String arg0) {
		super(arg0);
	}

}
