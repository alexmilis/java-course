package hr.fer.zemris.java.hw05.collections;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

public class SimpleHashTableTest {
	
	SimpleHashtable<String, Integer> table = new SimpleHashtable<>();


	@Before
	public void setUp() throws Exception {
		table.put("Ana", 1);
		table.put("Petra", 2);
		table.put("Luka", 3);
		table.put("Ante", 4);
	}

	@Test
	public void testSize() {
		assertEquals(4, table.size());
	}
	
	@Test
	public void testGet() {
		assertEquals(2, (int) table.get("Petra"));
	}
	
	@Test
	public void testPut() {
		table.put("Ana", 5);
		assertEquals(4, table.size());
		assertEquals(5, (int) table.get("Ana"));
	}
	
	@Test
	public void testContainsKey() {
		assertEquals(true, table.containsKey("Petra"));
		assertEquals(false, table.containsKey("Ivana"));		
	}
	
	@Test
	public void testContainsValue() {
		assertEquals(true, table.containsValue(2));
		assertEquals(false, table.containsValue(6));
		assertEquals(false, table.containsValue(null));
	}

	@Test
	public void testRemove() {
		table.remove("Ana");
		assertEquals(3, table.size());
		assertEquals(false, table.containsKey("Ana"));		
	}
	
	@Test
	public void testToString() {
		String output = "[Ana=1, Petra=2, Ante=4, Luka=3]";
		assertEquals(output, table.toString());
	}
	
	@Test
	public void testResize() {
		for(int i = 0; i < 16; i++) {
			table.put("ana" + i, i);
		}
		assertEquals(20, table.size());
	}

}
