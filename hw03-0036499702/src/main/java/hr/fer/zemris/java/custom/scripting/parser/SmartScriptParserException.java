package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Class that describes exceptions that occur in SmartScriptParser class.
 * @author Alex
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of SmartScriptParserException.
	 * @param arg0 message that sould be displayed when exception occurs.
	 */
	public SmartScriptParserException(String arg0) {
		super(arg0);
	}

}
