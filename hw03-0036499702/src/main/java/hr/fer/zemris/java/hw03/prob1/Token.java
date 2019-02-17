package hr.fer.zemris.java.hw03.prob1;

/**
 * Class used to describe tokens generated by class Lexer.
 * @author Alex
 *
 */
public class Token {
	
	/**
	 * Type of token.
	 */
	private TokenType type;
	
	/**
	 * Value of token.
	 */
	private Object value;

	/**
	 * Constructor of token.
	 * @param type type of token
	 * @param value value of token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Getter for field value.
	 * @return
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Getter for field type.
	 * @return
	 */
	public TokenType getType() {
		return this.type;
	}
}
