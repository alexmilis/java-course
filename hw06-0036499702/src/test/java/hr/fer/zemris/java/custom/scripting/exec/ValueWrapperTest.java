package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValueWrapperTest {

	@Test
	public void testAdd() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
		assertTrue((int) v1.getValue() == Integer.valueOf(0)); 
		assertTrue(v1.getValue().getClass() == Integer.class);
		assertTrue(v2.getValue() == null);
		
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
		assertTrue((double) v3.getValue() == Double.valueOf(13)); 
		assertTrue(v3.getValue().getClass() == Double.class);
		assertTrue((int) v4.getValue() == Integer.valueOf(1)); 

		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
		assertTrue((int) v5.getValue() == Integer.valueOf(13));
		assertTrue(v5.getValue().getClass() == Integer.class);
		assertTrue((int) v6.getValue() == Integer.valueOf(1)); 
	}
	
	@Test(expected = RuntimeException.class)
	public void testAddEx() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		v7.add(v8.getValue()); // throws RuntimeException
	}

	@Test
	public void testSubtract() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper("2.5");
		v1.add(v2.getValue());
		assertTrue((double) v1.getValue() == Double.valueOf("14.5")); 
	}

	@Test
	public void testMultiply() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(2);
		v1.multiply(v2.getValue());
		assertTrue((int) v1.getValue() == Integer.valueOf(24)); 
		assertTrue(v1.getValue().getClass() == Integer.class);

		
		ValueWrapper v3 = new ValueWrapper("12");
		ValueWrapper v4 = new ValueWrapper(2.0);
		v3.multiply(v4.getValue());
		assertTrue((double) v3.getValue() == Double.valueOf(24)); 
		assertTrue(v3.getValue().getClass() == Double.class);
	}

	@Test
	public void testDivide() {
		ValueWrapper v1 = new ValueWrapper("5");
		ValueWrapper v2 = new ValueWrapper(2);
		v1.divide(v2.getValue());
		assertTrue((int) v1.getValue() == Integer.valueOf(2)); 
		assertTrue(v1.getValue().getClass() == Integer.class);
		
		ValueWrapper v3 = new ValueWrapper("5");
		ValueWrapper v4 = new ValueWrapper(2.0);
		v3.divide(v4.getValue());
		assertTrue((double) v3.getValue() == Double.valueOf(2.5)); 
		assertTrue(v3.getValue().getClass() == Double.class);
	}

	@Test
	public void testNumCompare() {
		ValueWrapper v1 = new ValueWrapper("5");
		ValueWrapper v2 = new ValueWrapper(2);
		assertTrue(v1.numCompare(v2.getValue()) > 0);
		
		ValueWrapper v3 = new ValueWrapper("12.2");
		ValueWrapper v4 = new ValueWrapper(12.2);
		assertTrue(v3.numCompare(v4.getValue()) == 0);
		
		ValueWrapper v5 = new ValueWrapper("3.4");
		ValueWrapper v6 = new ValueWrapper(5);
		assertTrue(v5.numCompare(v6.getValue()) < 0);
	}

}
