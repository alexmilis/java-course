package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;


public class SmartScriptParserTest {
	
	//StringLoader load = new StringLoader();
	
	@Test(expected = SmartScriptParserException.class)
	public void testNullInit() {
		@SuppressWarnings("unused")
		SmartScriptParser parser = new SmartScriptParser(null);
	}
	
	@Test
	public void testInit() throws IOException {
		SmartScriptParser parser = new SmartScriptParser(new String(
				Files.readAllBytes(Paths.get("./src/test/resources/test1.txt")),
				StandardCharsets.UTF_8));
		assertEquals(1, parser.getDocumentNode().numberOfChildren());
		assertEquals("This is text.", parser.getDocumentNode().getChild(0).toString());
	}
	
	@Test
	public void testInitTag() throws IOException {
		SmartScriptParser parser = new SmartScriptParser(new String(
				Files.readAllBytes(Paths.get("./src/test/resources/test2.txt")),
				StandardCharsets.UTF_8));
		assertEquals(2, parser.getDocumentNode().numberOfChildren());
		assertEquals("{$= i $}", parser.getDocumentNode().getChild(1).toString());
	}
	
	@Test
	public void testInitTag2() throws IOException {
		@SuppressWarnings("unused")
		SmartScriptParser parser = new SmartScriptParser(new String(
				Files.readAllBytes(Paths.get("./src/test/resources/test3.txt")),
				StandardCharsets.UTF_8));
	}
	
	@Test
	public void testString() throws IOException {
		SmartScriptParser parser = new SmartScriptParser(new String(
				Files.readAllBytes(Paths.get("./src/test/resources/test4.txt")),
				StandardCharsets.UTF_8));
		assertEquals("{$= \"ABCD EFGH I\"J\" KLM\n\r\t end\" $}", parser.getDocumentNode().getChild(0).toString());
	}
	
	@Test
	public void testText() throws IOException {
		SmartScriptParser parser = new SmartScriptParser(new String(
				Files.readAllBytes(Paths.get("./src/test/resources/test5.txt")),
				StandardCharsets.UTF_8));
		assertEquals("Example {$=1$}. Now actually write one ", parser.getDocumentNode().getChild(0).toString());
		assertEquals(2, parser.getDocumentNode().numberOfChildren());
	}
	
	@Test
	public void testFor() throws IOException {
		SmartScriptParser parser = new SmartScriptParser(new String(
				Files.readAllBytes(Paths.get("./src/test/resources/test6.txt")),
				StandardCharsets.UTF_8));
		assertEquals("{$ FOR i 1 10 1 $}", parser.getDocumentNode().getChild(0).toString());
		assertEquals(3, parser.getDocumentNode().getChild(0).numberOfChildren());
		assertEquals(1, parser.getDocumentNode().numberOfChildren());
	}
	
	@SuppressWarnings("unused")
	@Test(expected = SmartScriptParserException.class)
	public void testForWithoutVariable() throws IOException {
		SmartScriptParser parser = new SmartScriptParser(new String(
				Files.readAllBytes(Paths.get("./src/test/resources/test7.txt")),
				StandardCharsets.UTF_8));
	}
	
	@SuppressWarnings("unused")
	@Test(expected = SmartScriptParserException.class)
	public void testForWithoutVariable2() throws IOException {
		SmartScriptParser parser = new SmartScriptParser(new String(
				Files.readAllBytes(Paths.get("./src/test/resources/test8.txt")),
				StandardCharsets.UTF_8));
	}
	
	@SuppressWarnings("unused")
	@Test(expected = SmartScriptParserException.class)
	public void testForTooFewArgs() throws IOException {
		SmartScriptParser parser = new SmartScriptParser(new String(
				Files.readAllBytes(Paths.get("./src/test/resources/test9.txt")),
				StandardCharsets.UTF_8));
	}
	
	@SuppressWarnings("unused")
	@Test(expected = SmartScriptParserException.class)
	public void testForTooManyArgs() throws IOException {
		SmartScriptParser parser = new SmartScriptParser(new String(
				Files.readAllBytes(Paths.get("./src/test/resources/test10.txt")),
				StandardCharsets.UTF_8));
	}
	
	@SuppressWarnings("unused")
	@Test(expected = SmartScriptParserException.class)
	public void testForFunction() throws IOException {
		SmartScriptParser parser = new SmartScriptParser(new String(
				Files.readAllBytes(Paths.get("./src/test/resources/test11.txt")),
				StandardCharsets.UTF_8));
	}
	
	@SuppressWarnings("unused")
	@Test(expected = SmartScriptParserException.class)
	public void testTooManyEndTags() throws IOException {
		SmartScriptParser parser = new SmartScriptParser(new String(
				Files.readAllBytes(Paths.get("./src/test/resources/test12.txt")),
				StandardCharsets.UTF_8));
	}

}
