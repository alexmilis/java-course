package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * Lexer for class SmartScriptParser. Breaks input string into tokens.
 * @author Alex
 *
 */
public class SmartScriptLexer {

	/**
	 * Input data (input string is stored here).
	 */
	private char[] data;
	
	/**
	 * Current token.
	 */
	private Token token;
	
	/**
	 * Index of last char in d
	 */
	private int currentIndex;
	
	/**
	 * State of this lexer.
	 */
	private SmartScriptLexerState state;
		
	/**
	 * Constructor of class SmartScriptLexer. Startting state of lexer is BASIC.
	 * @param text string that needs to be tokenized.
	 */
	public SmartScriptLexer(String text) {
		this.data = Objects.requireNonNull(text).toCharArray();
		this.currentIndex = 0;
		this.state = SmartScriptLexerState.BASIC;
	}
	
	/**
	 * Getter for current token.
	 * @return token.
	 */
	public Token getToken() {
		return this.token;
	}
	
	/**
	 * Getter for state of lexer.
	 * @return lexer state.
	 */
	public SmartScriptLexerState getState() {
		return this.state;
	}
	
	/**
	 * Method that finds next token.
	 * @return next token.
	 */
	public Token nextToken() {
		extractToken();
		return this.token;
	}
	
	/**
	 * Method that extracts the next token.
	 * @throws SmartScriptLexerException if next token can't be extracted.
	 */
	private void extractToken() {
		if(data.length == 0 && token == null) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		if(token != null && token.getType() == TokenType.EOF) {
			throw new SmartScriptLexerException("Can't extract token after EOF.");
		}
		
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		if(state.equals(SmartScriptLexerState.BASIC)) {
			String s = extractText();
			if(s.length() > 0) {
				token = new Token(TokenType.TEXT, s);
			} else {
				nextToken();
			}
		} else if(state.equals(SmartScriptLexerState.TAG)) {
			removeWhitespace();
			if(data[currentIndex] == '{') {
				throw new SmartScriptLexerException("Can't be a tag inside a tag!");
			}
			
			if(Character.isLetter(data[currentIndex])) {
				token = new Token(TokenType.VARIABLE, extractWord());
				return;
			}
			
			if(Character.isDigit(data[currentIndex])) {
				token = new Token(TokenType.NUMBER, extractWord());
				return;
			}
			
			if(data[currentIndex] == '-') {
				if(Character.isDigit(data[currentIndex + 1])) {
					token = new Token(TokenType.NUMBER, extractWord());		
				} else {
					token = new Token(TokenType.OPERATOR, extractWord());
				}
				return;
			}
			
			if(data[currentIndex] == '"') {
				currentIndex++;
				token = new Token(TokenType.STRING, extractString());
				currentIndex++;
				return;
			}
			
			if(data[currentIndex] == '@'){
				token = new Token(TokenType.FUNCTION, extractWord());
				return;
			}
			
			if(data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
				currentIndex += 2;
				state = SmartScriptLexerState.BASIC;
				nextToken();
				return;
			}
			
			token = new Token(TokenType.OPERATOR, "" + data[currentIndex++]);
			
		} else { //state = START_TAG
			removeWhitespace();
			if(data[currentIndex] == '=') {
				token = new Token(TokenType.ECHO, "=");
				currentIndex++;
			} else {
				String s = extractWord().toUpperCase();
				if(s.equals("END")) {
					token = new Token(TokenType.END, s);
				} else {
					token = new Token(TokenType.FOR, s);
				}
			}
			state = SmartScriptLexerState.TAG;
		}
	}
	
	/**
	 * Method that moves currentIndex so it skips whitespace (space, new line, tab, ...).
	 */
	private void removeWhitespace() {
		while(Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
			if(currentIndex == data.length - 1) {
				token = new Token(TokenType.EOF, null);
				return;
			}
		}
	}
	
	/**
	 * Method used to extract words like variable names or functions.
	 * @return string representation of word.
	 * @throws SmartScriptLexerException if tag is opened inside a tag.
	 */
	private String extractWord() {
		if(data[currentIndex] == '{') {
			throw new SmartScriptLexerException("No tags are allowed inside a tag");
		}
		
		StringBuilder str = new StringBuilder();
		while(!Character.isWhitespace(data[currentIndex]) && data[currentIndex] != '$' && data[currentIndex] != '"') {
			str.append(data[currentIndex++]);
		}
		return str.toString();
	}
	
	/**
	 * Method used to extract string from tag.
	 * @throws SmartScriptLexerException if string is invalid.
	 */
	private String extractString() {
		StringBuilder str = new StringBuilder();
		
		while(data[currentIndex] != '"') {
			if(data[currentIndex] != '\\') {
				str.append(data[currentIndex++]);
				if(currentIndex == data.length) {
					break;
				}
				continue;
			}
			
			switch(data[++currentIndex]) {
			case '\\':
				str.append('\\');
				break;
			case '"':
				str.append('"');
				break;
			case 'n':
				str.append((char) 10);
				break;
			case 'r':
				str.append((char) 13);
				break;
			case 't':
				str.append((char) 9);
				break;
			default:
				throw new SmartScriptLexerException("Invalid input, no valid char after \\");
			}
			currentIndex++;
		}
		return str.toString();
	}
	
	/**
	 * Method that extracts text outside of tags.
	 * @return string text
	 * @throws SmartScriptLexerException if text is invalid.
	 */
	private String extractText() {
		StringBuilder str = new StringBuilder();

		while(!(data[currentIndex] == '{' && data[currentIndex + 1] == '$') && currentIndex != data.length) {
			if(data[currentIndex] != '\\') {
				str.append(data[currentIndex++]);
				if(currentIndex == data.length) {
					break;
				}
				continue;
			}
			
			switch(data[++currentIndex]) {
			case '\\':
				str.append('\\');
				break;
			case '{':
				str.append('{');
				break;
			default:
				throw new SmartScriptLexerException("Invalid input, no valid char after \\");
			}
			currentIndex++;
		}
		
		if(currentIndex < data.length) {
			currentIndex += 2;
			state = SmartScriptLexerState.START_TAG;
		}
		
		return str.toString();
	}
	
}
