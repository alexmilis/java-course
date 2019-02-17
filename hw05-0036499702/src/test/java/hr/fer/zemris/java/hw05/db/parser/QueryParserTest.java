package hr.fer.zemris.java.hw05.db.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.QueryParser;

public class QueryParserTest {

	@Test
	public void testQueryParser() {
		QueryParser parser = new QueryParser("jmbag = \"23\"");
		assertTrue(parser.getQuery().size() == 1);
		assertTrue(parser.getQuery().get(0).getFieldValueGetter().equals(FieldValueGetters.JMBAG));
		assertTrue(parser.getQuery().get(0).getStringLiteral().equals("23"));
		assertTrue(parser.getQuery().get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS));
	}
	
	@Test
	public void testQueryParserAnd() {
		QueryParser parser = new QueryParser("jmbag = \"23\" anD lastName <= \"horvat\"");
		assertTrue(parser.getQuery().size() == 2);
		assertTrue(parser.getQuery().get(1).getFieldValueGetter().equals(FieldValueGetters.LAST_NAME));
		assertTrue(parser.getQuery().get(1).getStringLiteral().equals("horvat"));
		assertTrue(parser.getQuery().get(1).getComparisonOperator().equals(ComparisonOperators.LESS_OR_EQUALS));
	}
	
	@Test(expected = ParserException.class)
	public void testQueryParserEx() {
		QueryParser parser = new QueryParser("jmbag  =  \"23\" anD \"horvat\" <= lastName ");
		parser.getQuery();
	}
	
	@Test(expected = ParserException.class)
	public void testQueryParserEx2() {
		QueryParser parser = new QueryParser("jmbag  =  \"23\" anD lastName <= \"horvat\" and = \"33\" ");
		parser.getQuery();
	}
	
	@Test(expected = LexerException.class)
	public void testQueryParserEx3() {
		QueryParser parser = new QueryParser("jmbag  =  \"23\" anD name <= \"horvat\"");
		parser.getQuery();
	}

	@Test
	public void testIsDirectQuery() {
		QueryParser parser = new QueryParser("jmbag  =  \"23\" anD lastName <= \"horvat\"");
		assertTrue(parser.isDirectQuery() == false);
		
		QueryParser parser2 = new QueryParser("jmbag  =  \"23\"");
		assertTrue(parser2.isDirectQuery() == true);
	}

	@Test
	public void testGetQueriedJMBAG() {
		QueryParser parser = new QueryParser("jmbag  =  \"23\"");
		assertTrue(parser.getQueriedJMBAG().equals("23"));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testGetQueriedJMBAGEx() {
		QueryParser parser = new QueryParser("jmbag  =  \"23\" anD lastName <= \"horvat\"");
		parser.getQueriedJMBAG();
	}
	
	@Test
	public void Example() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		System.out.println("isDirectQuery(): " + qp1.isDirectQuery()); // true
		System.out.println("jmbag was: " + qp1.getQueriedJMBAG()); // 0123456789
		System.out.println("size: " + qp1.getQuery().size()); // 1
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		System.out.println("isDirectQuery(): " + qp2.isDirectQuery()); // false
		// System.out.println(qp2.getQueriedJMBAG()); // would throw!
		System.out.println("size: " + qp2.getQuery().size()); // 2
	}

}
