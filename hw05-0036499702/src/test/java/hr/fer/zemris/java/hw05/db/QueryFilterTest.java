package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryFilterTest {

	@Test
	public void testAccepts() {
		StudentRecord record = new StudentRecord("0123", "Horvat", "Ivan", 3);
		QueryParser parser = new QueryParser("jmbag = \"0123\" and lastName = \"Horvat\"");
		QueryFilter filter = new QueryFilter(parser.getQuery());
		
		assertTrue(filter.accepts(record));
	}
	
	@Test
	public void testNotAccepts() {
		StudentRecord record = new StudentRecord("0123", "Horvat", "Ivan", 3);
		QueryParser parser = new QueryParser("jmbag = \"013\" and lastName = \"Horvat\"");
		QueryFilter filter = new QueryFilter(parser.getQuery());
		
		assertFalse(filter.accepts(record));
	}

}
