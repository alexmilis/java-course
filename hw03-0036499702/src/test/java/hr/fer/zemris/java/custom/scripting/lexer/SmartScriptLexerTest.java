package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmartScriptLexerTest {
	
	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}

	@Test(expected=NullPointerException.class)
	public void testNullInput() {
		// must throw!
		new SmartScriptLexer(null);
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testGetReturnsLastNext() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}

	@Test(expected=SmartScriptLexerException.class)
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}
	
	@Test
	public void testText() {
		SmartScriptLexer lexer = new SmartScriptLexer("AB \nEF{ KLM\\{$#} kraj");
		assertEquals("AB \nEF{ KLM{$#} kraj", lexer.nextToken().getValue());		
	}
	
	@Test(expected = SmartScriptLexerException.class)
	public void testTextEx1() {
		SmartScriptLexer lexer = new SmartScriptLexer("ABCD \\ kraj");
		lexer.nextToken();
	}
	
	@Test(expected = SmartScriptLexerException.class)
	public void testTextEx2() {
		SmartScriptLexer lexer = new SmartScriptLexer("ABCD \\\" kraj");
		lexer.nextToken();
	}
	
	@Test
	public void testTag1() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ = 1 $}");
		assertEquals("=", lexer.nextToken().getValue());
		assertEquals(TokenType.ECHO, lexer.getToken().getType());		

		assertEquals("1", lexer.nextToken().getValue());	
		assertEquals(TokenType.NUMBER, lexer.getToken().getType());		

	}
	
	@Test
	public void testTag2() {
		SmartScriptLexer lexer = new SmartScriptLexer("Text {$ = 1 $} end");
		assertEquals("Text ", lexer.nextToken().getValue());		
		assertEquals("=", lexer.nextToken().getValue());		
		assertEquals("1", lexer.nextToken().getValue());
		assertEquals(" end", lexer.nextToken().getValue());
		assertEquals(TokenType.EOF, lexer.nextToken().getType());		
	}
	
	@Test(expected = SmartScriptLexerException.class)
	public void testTagEx() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ = {$ 1 $} end");
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test
	public void testTagWords() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ = + @word2 word3_2$}");
		lexer.nextToken();
		assertEquals("+", lexer.nextToken().getValue());		
		assertEquals(TokenType.OPERATOR, lexer.getToken().getType());	
		
		assertEquals("@word2", lexer.nextToken().getValue());		
		assertEquals(TokenType.FUNCTION, lexer.getToken().getType());	

		assertEquals("word3_2", lexer.nextToken().getValue());		
		assertEquals(TokenType.VARIABLE, lexer.getToken().getType());	
	}
	
	@Test
	public void testTagWords2() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ = 10.2 \"33\"-12 $}");
		lexer.nextToken();
		assertEquals("10.2", lexer.nextToken().getValue());		
		assertEquals(TokenType.NUMBER, lexer.getToken().getType());	
		
		assertEquals("33", lexer.nextToken().getValue());		
		assertEquals(TokenType.STRING, lexer.getToken().getType());	

		assertEquals("-12", lexer.nextToken().getValue());		
		assertEquals(TokenType.NUMBER, lexer.getToken().getType());			
	}
	
	@Test
	public void testMultipleTags() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ = 1 $} {$ for i 10 2");
		assertEquals("=", lexer.nextToken().getValue());		
		assertEquals(TokenType.ECHO, lexer.getToken().getType());	
		
		assertEquals("1", lexer.nextToken().getValue());		
		assertEquals(TokenType.NUMBER, lexer.getToken().getType());	
		
		assertEquals(" ", lexer.nextToken().getValue());		
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		
		assertEquals("FOR", lexer.nextToken().getValue());		
		assertEquals(TokenType.FOR, lexer.getToken().getType());		
	}
	
	@Test
	public void testString() {
		SmartScriptLexer lexer = new SmartScriptLexer("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}");
		lexer.nextToken();
		lexer.nextToken();
		
		assertEquals("Joe \"Long\" Smith", lexer.nextToken().getValue());		
		assertEquals(TokenType.STRING, lexer.getToken().getType());	
		
	}

}
