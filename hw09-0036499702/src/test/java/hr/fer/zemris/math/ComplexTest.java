package hr.fer.zemris.math;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class ComplexTest {
	
	private final static double THRESHOLD = 0.00001;

	@Test
	public void testModule() {
		Complex c = new Complex(3.65, 7.21);
		assertEquals(8.08125, c.module(), THRESHOLD);
	}

	@Test
	public void testMultiply() {
		Complex c1 = new Complex(3.4, 5.6);
		Complex c2 = new Complex(-7.8, 9.0);
		Complex c3 = c1.multiply(c2);
			
		assertTrue(c3.equals(new Complex(-76.92, -13.08)));
	}

	@Test
	public void testDivide() {
		Complex c1 = new Complex(3.4, 5.6);
		Complex c2 = new Complex(-7.8, 9.0);
		Complex c3 = c1.divide(c2);
		
		assertTrue(c3.equals(new Complex(0.16836, -0.52369)));
	}

	@Test
	public void testAdd() {
		Complex c1 = new Complex(3.4, 5.6);
		Complex c2 = new Complex(-7.8, 9.0);
		Complex c3 = c1.add(c2);

		assertTrue(c3.equals(new Complex(-4.4, 14.6)));
	}

	@Test
	public void testSub() {
		Complex c1 = new Complex(3.4, 5.6);
		Complex c2 = new Complex(-7.8, 9.0);
		Complex c3 = c1.sub(c2);
		
		assertTrue(c3.equals(new Complex(11.2, -3.4)));
	}

	@Test
	public void testNegate() {
		Complex c1 = new Complex(3.4, 5.6);
		Complex c2 = c1.negate();
		
		assertTrue(c2.equals(new Complex(-3.4, -5.6)));
	}

	@Test
	public void testPower() {
		Complex c1 = new Complex(3.4, 5.6);
		Complex c2 = c1.power(5);
		
		assertTrue(c2.equals(new Complex(4847.26304, -11052.15104)));
	}

	@Test
	public void testRoot() {
		Complex c = new Complex(5, 3);
		List<Complex> roots = c.root(3);
		
		assertTrue(roots.get(0).equals(new Complex(1.77077, 0.32248)));
		assertTrue(roots.get(1).equals(new Complex(-1.16466, 1.37229)));
		assertTrue(roots.get(2).equals(new Complex(-0.60611, -1.69477)));
	}

}
