package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class used to describe exepctions thrown by SmartScriptLexer.
 * @author Alex
 *
 */
public class SmartScriptLexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of SmartScriptLexerException.
	 * @param arg0 message that should be displayed when exception occurs.
	 */
	public SmartScriptLexerException(String arg0) {
		super(arg0);
	}
}
