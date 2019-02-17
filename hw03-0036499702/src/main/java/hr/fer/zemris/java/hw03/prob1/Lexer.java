package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * Lexer for tokenization of input.
 * @author Alex
 *
 */
public class Lexer {
	
	/**
	 * Data that needs to be analyzed.
	 */
	private char[] data;
	
	/**
	 * Current token.
	 */
	private Token token;
	
	/**
	 * Current position in data.
	 */
	private int currentIndex;
	
	/**
	 * State of lexer.
	 */
	private LexerState state;
	
	
	/**
	 * Constructor of class Lexer. Starting lexer state is BASIC.
	 * @param text string that should be analyzed.
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text);
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
	}
	
	/**
	 * Getter for current token.
	 * @return token.
	 */
	public Token getToken() {
		return this.token;
	}
	
	/**
	 * Setter for lexer state.
	 * @param state.
	 */
	public void setState(LexerState state) {
		this.state = Objects.requireNonNull(state);
	}
	
	/**
	 * Method that finds next token.
	 * @return next token.
	 * @throws LexerException if next token can't be extracted.
	 */
	public Token nextToken() {
		extractToken();
		return getToken();
	}

	/**
	 * Method that extracts the next token.
	 * @throws LexerException if next token can't be extracted.
	 */
	private void extractToken() {
		if(data.length == 0 && token == null) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		if(token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("Can't extract token after EOF.");
		}
		
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		removeWhitespace();
		
		if(state.equals(LexerState.BASIC)) {
			extractBasic();
		} else {
			extractExtended();
		}

		
	}
	
	/**
	 * Method that extracts tokens while lexer is in state BASIC.
	 */
	private void extractBasic() {
		if(Character.isDigit(data[currentIndex])) {
			token = new Token(TokenType.NUMBER, extractNumber());
			return;
		}
		
		boolean flag = false;

		if(Character.isLetter(data[currentIndex])) {
			String s = extractWord();
			token = new Token(TokenType.WORD, s);
			flag = true;
		}
		
		while(checkEscape()) {
			String s = new String();
			if(!Character.isWhitespace(data[currentIndex - 1])) {
				s += (String) token.getValue();
			}
			currentIndex++;
			s += data[currentIndex++];
			
			if(!Character.isWhitespace(data[currentIndex])) {
				s+= extractWord();
			}
			token = new Token(TokenType.WORD, s);
			flag = true;
		}
		
		if(flag) return;
		
		if(currentIndex < data.length - 1) {
			if(data[currentIndex] == '#') {
				changeState();
			}
			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
		}
	}
	
	/**
	 * Method that exctracts tokens while lexer is in state EXTENDED.
	 */
	private void extractExtended() {
		
		int count = 0;		
		while(!Character.isWhitespace(data[currentIndex + count]) && data[currentIndex + count] != '#') {
			count++;
			if(currentIndex + count >= data.length - 1) {
				break;
			}
		}
		
		if(count > 0) {
			token = new Token(TokenType.WORD, String.valueOf(data, currentIndex, count));
			currentIndex += count;
		} else if (data[currentIndex] == '#'){
			token = new Token(TokenType.SYMBOL, '#');
			currentIndex++;
			changeState();
		}
		
	}
	
	/**
	 * Help method used to remove whitespace before extracting a token.
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
	 * Method used to extract a word.
	 * @return string word
	 */
	private String extractWord() {
		int count = 0;
		while(Character.isLetter(data[currentIndex + count])) {
			count++;
			if(currentIndex + count > data.length - 1) {
				break;
			}
		}
		String s = String.valueOf(data, currentIndex, count);
		currentIndex += count;
		return s;
	}
		
	/**
	 * Method used to extract a number that must be of type long.
	 * @return number
	 * @throws LexerException if number can't be parsed as long.
	 */
	private long extractNumber() {
		int count = 0;
		while(Character.isDigit(data[currentIndex + count])) {
			count++;
			if(currentIndex + count > data.length - 1) {
				break;
			}
		}
		String s = String.valueOf(data, currentIndex, count);
		currentIndex += count;
		try {
			long number = Long.parseLong(s);
			return number;
		} catch (NumberFormatException ex) {
			throw new LexerException("Input is invalid, number is can't be parsed as long." + s + "ooo" + count);
		}
	}
	
	/**
	 * Help method used to check if there is escapeing in extraction of string.
	 * @return true if there is escapeing.
	 * @throws LexerException if input is invalid (no number or special char after \).
	 */
	private boolean checkEscape() {
		if(currentIndex > data.length - 1) {
			return false;
		}
		if(data[currentIndex] == '\\') {
			if(currentIndex + 1 == data.length) {
				throw new LexerException("Invalid input, no characters after \\");
			} else if (Character.isLetter(data[currentIndex + 1])) {
				throw new LexerException("Invalid input, letter after \\");
			} else {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Help method used to change state of lexer.
	 */
	private void changeState() {
		if(state.equals(LexerState.BASIC)) {
			state = LexerState.EXTENDED;
		} else {
			state = LexerState.BASIC;
		}
	}
}
