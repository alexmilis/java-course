package hr.fer.zemris.java.hw05.db.parser;

/**
 * Token of {@link QueryLexer}.
 * @author Alex
 *
 */
public class Token {
	
	/**
	 * Value of token.
	 */
	private String value;
	
	/**
	 * Type of token.
	 */
	private TokenType type;
	
	/**
	 * Constructor of this class.
	 * @param type TokenType
	 * @param value string
	 */
	public Token(TokenType type, String value) {
		this.value = value;
		this.type = type;
	}

	/**
	 * Getter for field value.
	 * @return string value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Getter for field type.
	 * @return TokenType type
	 */
	public TokenType getType() {
		return type;
	}
}
