package hr.fer.zemris.java.hw03.prob1;

public enum LexerState {
	
	/**
	 * Starting state, when not surrounded by '#'.
	 */
	BASIC, 
	
	/**
	 * When sourrounded by '#', no escapeing, no tokens of type NUMBER.
	 */
	EXTENDED;

}
