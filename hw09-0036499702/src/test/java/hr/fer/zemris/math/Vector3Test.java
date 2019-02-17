package hr.fer.zemris.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class Vector3Test {
	
	private final static double THRESHOLD = 0.00001;

	@Test
	public void testNorm() {
		Vector3 v = new Vector3(1.2, 3.4, 5.6);
		assertEquals(6.66033, v.norm(), THRESHOLD);
	}

	@Test
	public void testNormalized() {
		Vector3 v = new Vector3(2, 12, 22);
		Vector3 normalized = v.normalized();
		
		assertTrue(normalized.equals(new Vector3(0.07956, 0.47733, 0.87511)));
	}

	@Test
	public void testAdd() {
		Vector3 v1 = new Vector3(4, 5, 6);
		Vector3 v2 = new Vector3(3, 2, 1);
		Vector3 v3 = v1.add(v2);
		
		assertTrue(v3.equals(new Vector3(7, 7, 7)));
	}

	@Test
	public void testSub() {
		Vector3 v1 = new Vector3(4.3, 5.21, 6.7);
		Vector3 v2 = new Vector3(3, 7.5, 1.6);
		Vector3 v3 = v1.sub(v2);
		
		assertTrue(v3.equals(new Vector3(1.3, -2.29, 5.1)));
	}

	@Test
	public void testDot() {
		Vector3 v1 = new Vector3(3, 4, 5);
		Vector3 v2 = new Vector3(6, 7, 8);
		assertEquals(86, v1.dot(v2), THRESHOLD);
	}

	@Test
	public void testCross() {
		Vector3 v1 = new Vector3(3, 4, 5);
		Vector3 v2 = new Vector3(6, 7, 8);
		Vector3 v3 = v1.cross(v2);

		assertTrue(v3.equals(new Vector3(-3, 6, -3)));
	}

	@Test
	public void testScale() {
		Vector3 v1 = new Vector3(3, 4, 5);
		Vector3 v2 = v1.scale(2.45);
		
		assertTrue(v2.equals(new Vector3(7.35, 9.8, 12.25)));
	}

	@Test
	public void testCosAngle() {
		Vector3 v1 = new Vector3(2, 6, 8);
		Vector3 v2 = new Vector3(3, 5, 9);
		assertEquals(0.98755, v1.cosAngle(v2), THRESHOLD);
		
		Vector3 v3 = new Vector3(2, 6, 8);
		Vector3 v4 = new Vector3(-3, -5, 9);
		assertEquals(0.32918, v3.cosAngle(v4), THRESHOLD);
	}

	@Test
	public void testToArray() {
		Vector3 v1 = new Vector3(3.45, -2.65, 9);
		double[] array = v1.toArray();
		
		assertEquals(3.45, array[0], THRESHOLD);
		assertEquals(-2.65, array[1], THRESHOLD);
		assertEquals(9, array[2], THRESHOLD);
	}

}
