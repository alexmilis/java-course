package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.parser.LexerException;
import hr.fer.zemris.java.hw05.db.parser.ParserException;
import hr.fer.zemris.java.hw05.db.parser.QueryLexer;
import hr.fer.zemris.java.hw05.db.parser.Token;
import hr.fer.zemris.java.hw05.db.parser.TokenType;

/**
 * Parser for query commands.
 * @author Alex
 *
 */
public class QueryParser {
	
	/**
	 * Lexer for query input.
	 */
	private QueryLexer lexer;
	
	/**
	 * Query that has been parsed.
	 */
	private List<ConditionalExpression> query;
	
	/**
	 * Constructor of this class.
	 * @param text query input that needs to be parsed.
	 * @throws ParserException if text is null.
	 * @throws LexerException if text cannot be turned into tokens.
	 */
	public QueryParser(String text) {
		try {
			this.lexer = new QueryLexer(text);
		} catch (NullPointerException ex) {
			throw new ParserException("query cannot be null!");
		}
		query = new ArrayList<>();
		parse();
	}
	
	/**
	 * Method that parses input into query. Groups tokens by 3 and turns them into conditional expressions.
	 * @throws ParserException if tokens are invalid.
	 */
	private void parse() {
		Token token;		
		do {
			token = lexer.nextToken();
			IFieldValueGetter getter = getGetter(token);
			
			token = lexer.nextToken();
			IComparisonOperator operator = getOperator(token);
			
			token = lexer.nextToken();
			if(!token.getType().equals(TokenType.STRING)) {
				throw new ParserException("Invalid query, no string after operator");
			}
			String literal = token.getValue();
			
			query.add(new ConditionalExpression(getter, literal, operator));
			lexer.nextToken();
		} while (!lexer.getToken().getType().equals(TokenType.EOF));
	}
	
	/**
	 * Gets field value getter from token.
	 * @param token token that has to be parsed.
	 * @return corresponding field value getter.
	 * @throws ParserException if invalid field is requested.
	 */
	private IFieldValueGetter getGetter(Token token) {
		if(!token.getType().equals(TokenType.FIELD)) {
			throw new ParserException("Invalid query, no field name before operator");
		}
		switch(token.getValue()) {
		case "jmbag":
			return FieldValueGetters.JMBAG;
		case "firstName":
			return FieldValueGetters.FIRST_NAME;
		case "lastName":
			return FieldValueGetters.LAST_NAME;
		default:
			throw new ParserException("Invalid field name");
		}
	}
	
	/**
	 * Parses operator from token by symbol.
	 * @param token
	 * @return comparison operator.
	 * @throws ParserException if symbol is invalid.
	 */
	private IComparisonOperator getOperator(Token token) {
		if(!token.getType().equals(TokenType.OPERATOR)) {
			throw new ParserException("Invalid query, no operator after field name");
		}
		switch(token.getValue()) {
		case "<":
			return ComparisonOperators.LESS;
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		case "=":
			return ComparisonOperators.EQUALS;
		case ">":
			return ComparisonOperators.GREATER;
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;
		case "!=":
			return ComparisonOperators.NOT_EQUALS;
		case "LIKE":
			return ComparisonOperators.LIKE;
		default:
			throw new ParserException("Invalid operator: " + token.getValue());
		}
	}
	
	/**
	 * Determines if query if direct. Direct query is "jmbag = "somestring"".
	 * @return true if qiiery if direct.
	 */
	public boolean isDirectQuery() {
		if(query.size() == 1 && query.get(0).getFieldValueGetter().equals(FieldValueGetters.JMBAG)
				&& query.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS)) {
			return true;
		}
		return false;
	}
	
	/**
	 * If query is direct, returns jmbag with which record can be easily fetched.
	 * @return string jmbag
	 * @throws IllegalStateException if query is not direct.
	 */
	public String getQueriedJMBAG() {
		if(isDirectQuery()) {
			return query.get(0).getStringLiteral();
		}
		throw new IllegalStateException("Query is not direct!");
	}
	
	/**
	 * Getter for query.
	 * @return list of conditional expressions.
	 */
	public List<ConditionalExpression> getQuery(){
		return query;
	}
	

}
