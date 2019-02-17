package hr.fer.zemris.java.hw05.collections;
import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

public class PrimjerTest {
	
	SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);

	@Before
	public void setUp() throws Exception {
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); 
	}

	@Test
	public void testRemove() {
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}
		assertEquals(3, examMarks.size());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testRemoveEx1() {
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter2 = examMarks.iterator();
		while(iter2.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter2.next();
			if(pair.getKey().equals("Ivana")) {
				iter2.remove();
				iter2.remove();
			}
		}
	}
	
	@Test(expected = ConcurrentModificationException.class)
	public void testRemoveEx2() {
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter3 = examMarks.iterator();
		while (iter3.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter3.next();
			if (pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
		}
	}

}
