package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration that lists all types of token.
 * @author Alex
 *
 */
public enum TokenType {
	
	/**
	 * End of file.
	 */
	EOF, 
	
	/**
	 * Text outside of tags.
	 */
	TEXT, 
	
	/**
	 * Start of echo tag.
	 */
	ECHO, 
	
	/**
	 * Start of for tag.
	 */
	FOR, 
	
	/**
	 * String in a tag.
	 */
	STRING, 
	
	/**
	 * Function in a tag.
	 */
	FUNCTION, 
	
	/**
	 * Number in a tag.
	 */
	NUMBER, 
	
	/**
	 * Variable in a tag.
	 */
	VARIABLE, 
	
	/**
	 * Operator in a tag.
	 */
	OPERATOR, 
	
	/**
	 * End of tag.
	 */
	END;

}
