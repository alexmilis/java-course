package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;

import org.junit.Test;

public class DictionaryTest {

	@Test
	public void initTest() {
		Dictionary dict = new Dictionary();
		assertEquals(true, dict.isEmpty());
		assertEquals(0, dict.size());
	}
	
	@Test
	public void putTest() {
		Dictionary dict = new Dictionary();
		dict.put('a', 1);
		
		assertEquals(false, dict.isEmpty());
		assertEquals(1, dict.size());
		
		dict.put('b', 2);
		assertEquals(2, dict.size());
		
		dict.put('a', 3);
		assertEquals(2, dict.size());
		assertEquals(3, dict.get('a'));
		assertEquals(2, dict.get('b'));
	}
	
	@Test(expected = NullPointerException.class)
	public void putNullTest() {
		Dictionary dict = new Dictionary();
		dict.put(null, 2);
	}
	
	@Test
	public void getTest() {
		Dictionary dict = new Dictionary();
		dict.put('a', 1);
		dict.put('b', 2);
		
		assertEquals(1, dict.get('a'));
		assertEquals(2, dict.get('b'));
	}
	
	@Test(expected = NullPointerException.class)
	public void getNullTest() {
		Dictionary dict = new Dictionary();
		dict.get(null);
	}
	
	@Test(expected = DictionaryException.class)
	public void getNonExistingTest() {
		Dictionary dict = new Dictionary();
		dict.put('a', 22);
		dict.get('b');
	}

}
