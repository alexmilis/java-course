package hr.fer.zemris.java.hw05.db.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryLexerTest {

	@Test
	public void testJmbag() {
		QueryLexer lexer = new QueryLexer("jmbag = \"23\"");
		assertTrue(lexer.nextToken().getValue().equals("jmbag"));
		assertTrue(lexer.nextToken().getType().equals(TokenType.OPERATOR));
		assertTrue(lexer.nextToken().getValue().equals("23"));
		assertTrue(lexer.nextToken().getType().equals(TokenType.EOF));
	}
	
	@Test
	public void testMultiple() {
		QueryLexer lexer = new QueryLexer("jmbag = \"23\" and lastName<=\"ab*cd\"");
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		assertTrue(lexer.nextToken().getType().equals(TokenType.AND));
		assertTrue(lexer.nextToken().getValue().equals("lastName"));
		assertTrue(lexer.nextToken().getType().equals(TokenType.OPERATOR));
		assertTrue(lexer.nextToken().getValue().equals("ab*cd"));
	}

}
