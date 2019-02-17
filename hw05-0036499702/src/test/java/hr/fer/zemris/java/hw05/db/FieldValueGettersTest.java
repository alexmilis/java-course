package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class FieldValueGettersTest {

	@Test
	public void testJMBAG() {
		StudentRecord record = new StudentRecord("0123", "Ivan", "Horvat", 3);
		assertTrue(FieldValueGetters.JMBAG.get(record).equals("0123"));
	}

	@Test
	public void testFirstName() {
		StudentRecord record = new StudentRecord("0123", "Horvat", "Ivan", 3);
		assertTrue(FieldValueGetters.FIRST_NAME.get(record).equals("Ivan"));
	}
	
	@Test
	public void testLastName() {
		StudentRecord record = new StudentRecord("0123", "Horvat", "Ivan", 3);
		assertTrue(FieldValueGetters.LAST_NAME.get(record).equals("Horvat"));
	}
}
