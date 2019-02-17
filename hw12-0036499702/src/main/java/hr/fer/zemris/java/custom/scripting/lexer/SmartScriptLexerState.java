package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration of states of SmartScriptLexer.
 * @author Alex
 *
 */
public enum SmartScriptLexerState {
	
	/**
	 * Starting state, lexer is in this state when not in tags.
	 */
	BASIC, 
	
	/**
	 * State when lexer enters a tag and extracts keyword.
	 */
	START_TAG, 
	
	/**
	 * State when lexer is in a tag and extracts arguments of the tag.
	 */
	TAG;

}