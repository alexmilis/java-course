package hr.fer.zemris.java.hw05.db.parser;

import java.util.Objects;

public class QueryLexer {
	
	private static final char[] OPERATORS = {'>', '<', '=', '!'};
	
	private static final String[] FIELDS = {"jmbag", "firstName", "lastName"};
	
	private char[] data;
	
	private Token token;
	
	private int currentIndex;

	public QueryLexer(String text) {
		Objects.requireNonNull(text);
		this.data = text.toCharArray();
		this.token = null;
		this.currentIndex = 0;
	}
	
	public Token getToken() {
		return this.token;
	}
	
	public Token nextToken() {
		extractToken();
		return getToken();
	}
	
	private void extractToken() {
		if(data.length == 0 && token == null) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		if(token != null && token.getType() == TokenType.EOF) {
			throw new UnsupportedOperationException("Can't extract token after EOF.");
		}
		
		if(currentIndex >= data.length - 1) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		removeWhitespace();
		
		if(isOperator(data[currentIndex])) {
			if(data[currentIndex + 1] == '=') {
				token = new Token(TokenType.OPERATOR, data[currentIndex] + "=");
				currentIndex += 2;
				return;
			}
			token = new Token(TokenType.OPERATOR, data[currentIndex++] + "");
			return;
		}
		
		if(data[currentIndex] == '\"') {
			currentIndex++;
			token = new Token(TokenType.STRING, extractWord());
			currentIndex++;
			return;
		}
		
		String word = extractWord();
		if(word.equals("LIKE")){
			token = new Token(TokenType.OPERATOR, word);
		} else if(isField(word)) {
			token = new Token(TokenType.FIELD, word);
		} else if(word.toUpperCase().equals("AND")) {
			token = new Token(TokenType.AND, word);
		} else {
			throw new LexerException("Invalid input, cannot be made into a token: " + word);
		}
		
	}
	
	private boolean isOperator(char c) {
		for(char op : OPERATORS) {
			if(c == op) {
				return true;
			}
		}
		return false;
	}

	private boolean isField(String s) {
		for(String field : FIELDS) {
			if(s.equals(field)) {
				return true;
			}
		}
		return false;
	}
	
	private String extractWord() {
		int count = 0;
		while(!Character.isWhitespace((data[currentIndex + count])) && 
				!isOperator(data[currentIndex + count]) &&
				data[currentIndex + count] != '\"') {
			count++;
			if(currentIndex + count > data.length - 1) {
				break;
			}
		}
		String s = String.valueOf(data, currentIndex, count);
		currentIndex += count;
		return s;
	}
	
	private void removeWhitespace() {
		while(Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
			if(currentIndex >= data.length - 1) {
				token = new Token(TokenType.EOF, null);
				return;
			}
		}
	}

}
