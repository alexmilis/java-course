package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;

/**
 * Parser that does the syntax analysis of given text.
 * @author Alex
 *
 */
public class SmartScriptParser {

	/**
	 * Lexer used for generating tokens.
	 */
	private SmartScriptLexer lexer;
	
	/**
	 * Starting node of tree built by parser.
	 */
	private ObjectStack stack;
	
	/**
	 * Starting node in stack, root of the tree that parser is building.
	 */
	private DocumentNode documet;
	
	/**
	 * Array of valid operators, all other operators are invalid.
	 */
	private final static String[] OPERATORS = {"+", "-", "/", "*", "^"};
	
	
	/**
	 * Constructor of class SmartScriptParser. Creates an instance of SmartScriptLexer with text as argument.
	 * Invokes method parse for further data processing.
	 * @param text text that needs to be parsed.
	 */
	public SmartScriptParser(String text) {
		try {
			this.lexer = new SmartScriptLexer(text);
		} catch (NullPointerException ex) {
			throw new SmartScriptParserException("Text is null!");
		}
		this.stack = new ObjectStack();
		this.documet = new DocumentNode();
		stack.push(documet);
		parse();
	}
	
	/**
	 * Getter method for document node (root of tree).
	 * @return document node
	 */
	public DocumentNode getDocumentNode() {
		return this.documet;
	}	

	/**
	 * Method that parses tokens from lexer. This method builds the document model tree.
	 * @throws SmartScriptParserException if token can't be parsed into the tree.
	 * 										if stack is empty.
	 * 										if there are too many arguments in for loop.
	 */
	private void parse() {		
		lexer.nextToken();
		
		while(lexer.getToken().getType() != TokenType.EOF) {
			String value = (String) lexer.getToken().getValue();
			switch(lexer.getToken().getType()) {
			case END:
				try {
					stack.pop();
				} catch (EmptyStackException ex){
					throw new SmartScriptParserException("Stack is empty, too many END tags!");
				}
				break;
			case TEXT:
				try {
					Node node = (Node) stack.peek();
					node.addChildNode(new TextNode(value));
				} catch(EmptyStackException ex) {
					throw new SmartScriptParserException("Stack is empty, too many END tags!");
				}
				break;
			case FOR:
				constructFor();
				break;
			case ECHO:
				constructEcho();
				break;
			default:
				throw new SmartScriptParserException("Too many arguments!");
			}
			if(lexer.getToken().getType() != TokenType.EOF) {
				lexer.nextToken();
			}
		}		
	}
	
	/**
	 * Method that represents for loop, it constructs ForLoopNode.
	 * It takes 3 or 4 arguments to construct ForLoopNode, depending on input.
	 * It pushes the node onto the stack.
	 * @throws SmartScriptParserException if stack if empty or there aren't enough arguments.
	 */
	private void constructFor() {
		ElementVariable var = parseVariable(lexer.nextToken());
		Element start = null;
		Element end = null;
		Element step = null;
		
		start = expression();
		end = expression();
		if(lexer.getState() == SmartScriptLexerState.TAG) {
			step = expression();
		}
		
		ForLoopNode forLoop = new ForLoopNode(var, start, end, step);
		try {
			((Node) stack.peek()).addChildNode(forLoop);
		} catch (EmptyStackException ex) {
			throw new SmartScriptParserException("Stack is empty, too many END tags!");
		}
		stack.push(forLoop);
	}
	
	/**
	 * Method that parses expressions of for loop.
	 * @return element that is needed to construct for loop.
	 * @throws SmartScriptParserException if there aren't enough arguments.
	 */
	private Element expression() {
		if(lexer.getToken().getType() == TokenType.EOF) {
			throw new SmartScriptParserException("Not enough arguments!");
		}
		lexer.nextToken();
		switch(lexer.getToken().getType()) {
		case VARIABLE:
			return parseVariable(lexer.getToken());
		case NUMBER:
			return parseNumber(lexer.getToken());
		case STRING:
			return parseString(lexer.getToken());
		default:
			throw new SmartScriptParserException("Not enough arguments!");
		}
	}
	
	/**
	 * Method that constructs EchoNode. It takes a variable number of arguments.
	 * It pushes EchoNode onto the stack.
	 * @throws SmartScriptParserException if argument can't be parsed or stack is empty.
	 */
	private void constructEcho() {
		Element[] elements = new Element[1];
		int index = 0;
		while(lexer.getState() == SmartScriptLexerState.TAG && 
				lexer.getToken().getType() != TokenType.EOF) {
			
			if(index == elements.length) {
				elements = resize(elements);
			}
			
			lexer.nextToken();
			switch(lexer.getToken().getType()) {
			case VARIABLE:
				elements[index++] = parseVariable(lexer.getToken());
				break;
			case NUMBER:
				elements[index++] = parseNumber(lexer.getToken());
				break;
			case STRING:
				elements[index++] = parseString(lexer.getToken());
				break;
			case FUNCTION:
				elements[index++] = parseFunction(lexer.getToken());
				break;
			case OPERATOR:
				elements[index++] = parseOperator(lexer.getToken());
				break;
			case TEXT:
				EchoNode echo = new EchoNode(elements);
				try {
					((Node) stack.peek()).addChildNode(echo);
					((Node) stack.peek()).addChildNode(new TextNode(lexer.getToken().getValue()));
				} catch (EmptyStackException ex) {
					throw new SmartScriptParserException("Stack is empty, too many END tags!");
				}
				return;
			default:
				break;
			}
		}
		
		EchoNode echo = new EchoNode(elements);
		try {
			((Node) stack.peek()).addChildNode(echo);
		} catch (EmptyStackException ex) {
			throw new SmartScriptParserException("Stack is empty, too many END tags!");
		}
		
	}
	
	
	/**
	 * Method that parses function and creates ElementFunction.
	 * @param token token of type FUNCTION.
	 * @return ElementFunction that describes function.
	 * @throws SmartScriptParserException if function name is invalid.
	 */
	private ElementFunction parseFunction(Token token) {
		String function = token.getValue();
		if(!function.startsWith("@")) {
			throw new SmartScriptLexerException("Invalid function name, doesn't start with @: " + function);
		}
		if(!parseWord(function.substring(1))) {
			throw new SmartScriptParserException("Invalid function name: " + function);
		}
		
		return new ElementFunction(function);
	}
	
	/**
	 * Method parses integer and double numbers and returns corresponding element.
	 * integer --> ElementConstantInteger, double --> ElementConstantDouble.
	 * @param token token of type NUMBER.
	 * @return ElementConstantInteger/Double with number as value.
	 * @throws SmartScriptParserException if number can't be parsed.
	 */
	private Element parseNumber(Token token) {
		String number = token.getValue();
		try {
			int n = Integer.parseInt(number);
			return new ElementConstantInteger(n);
		} catch (NumberFormatException ex1) {
			try {
				double n = Double.parseDouble(number);
				return new ElementConstantDouble(n);
			} catch (NumberFormatException ex2) {
				throw new SmartScriptParserException("Number can't be parsed :" + number);
			}
		}
	}
	
	/**
	 * Method parses operator and returns ElementOperator.
	 * List of valid operators is declared as a constant of this class (OPERATORS).
	 * @param token token of type OPERATOR.
	 * @return ElementOperator with operator as value.
	 * @throws SmartScriptParserException if operator is invalid.
	 */
	private ElementOperator parseOperator(Token token) {
		String operator = token.getValue();
		for(String op : OPERATORS) {
			if(op.equals(operator)) {
				return new ElementOperator(operator);
			}
		}
		throw new SmartScriptParserException("Invalid operator: " + operator);
	}
	
	/**
	 * Method that parses variable and returns ElementVariable.
	 * @param token token of type VARIABLE.
	 * @return ElementVariable with variable name as value.
	 * @throws SmartScriptParserException if variable name is invalid.
	 */
	private ElementVariable parseVariable(Token token) {
		if(token.getType() != TokenType.VARIABLE) {
			throw new SmartScriptParserException(token.getValue() + " is not a variable!");
		}
		String name = token.getValue();
		if(!parseWord(name)) {
			throw new SmartScriptLexerException("Invalid variable name: " + name);
		}
		return new ElementVariable(name);
	}
	
	/**
	 * Method that parses string and returns ElementString.
	 * @param token token of type STRING.
	 * @return ElementString with string as value.
	 */
	private ElementString parseString(Token token) {
		String string = token.getValue();		
		return new ElementString(string);
	}
	
	
	/**
	 * Method used to check if variable names and functions are valid.
	 * @param word string that needs to be checked.
	 * @return true if word is valid as variable or function.
	 */
	private boolean parseWord(String word) {
		char[] data = word.toCharArray();
		if(!Character.isLetter(data[0])) {
			return false;
		}
		for(char c : data) {
			if(!Character.isAlphabetic(c) && c != '-') {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Method that resizes an array of elements, it's used in construction of EchoNode.
	 * @param elements array that needs to be resized.
	 * @return array of elements twice the size of previous one.
	 */
	private Element[] resize(Element[] elements) {
		return Arrays.copyOf(elements, elements.length * 2);
	}

}
