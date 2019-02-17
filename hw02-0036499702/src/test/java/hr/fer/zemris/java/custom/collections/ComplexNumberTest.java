package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * Class used to test class ComplexNumber.
 * @author Alex
 *
 */
public class ComplexNumberTest {

	private static final int SCALE = 100;
	/**
	 * Scale for comparing double values (only first two decimals are tested).
	 * @param number value that needs to be scaled
	 * @return scaled value
	 */
	private long scale(double number) {
		return Math.round(number * SCALE);
	}

	@Test
	public void testFromReal() {
		ComplexNumber c = ComplexNumber.fromReal(2.3);
		Assert.assertEquals(scale(2.3), scale(c.getReal()));
		Assert.assertEquals(scale(0.0), scale(c.getImaginary()));
	}

	@Test
	public void testFromImaginary() {
		ComplexNumber c = ComplexNumber.fromImaginary(-4.5);
		Assert.assertEquals(scale(-4.5), scale(c.getImaginary()));
		Assert.assertEquals(scale(0.0), scale(c.getReal()));
	}

	@Test
	public void testFromMagnitudeAndAngle() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(4, 1.20);
		Assert.assertEquals(scale(1.45), scale(c.getReal()));
		Assert.assertEquals(scale(3.73), scale(c.getImaginary()));
	}

	@Test
	public void testParse() {
		ComplexNumber c = ComplexNumber.parse("-2-3i");
		Assert.assertEquals(scale(-2.0), scale(c.getReal()));
		Assert.assertEquals(scale(-3.0), scale(c.getImaginary()));
		
		ComplexNumber c2 = ComplexNumber.parse("-2i");
		Assert.assertEquals(scale(0.0), scale(c2.getReal()));
		Assert.assertEquals(scale(-2.0), scale(c2.getImaginary()));
		
		ComplexNumber c3 = ComplexNumber.parse("-2.67");
		Assert.assertEquals(scale(-2.67), scale(c3.getReal()));
		Assert.assertEquals(scale(0.0), scale(c3.getImaginary()));

		ComplexNumber c4 = ComplexNumber.parse("-2.67i+6.7");
		Assert.assertEquals(scale(6.7), scale(c4.getReal()));
		Assert.assertEquals(scale(-2.67), scale(c4.getImaginary()));
	}

	@Test
	public void testGetMagnitude() {
		ComplexNumber c = new ComplexNumber(3.4, 5.6);
		Assert.assertEquals(scale(6.55), scale(c.getMagnitude()));
	}

	@Test
	public void testGetAngle() {
		ComplexNumber c = new ComplexNumber(3.4, 5.6);
		Assert.assertEquals(scale(1.03), scale(c.getAngle()));
	}

	@Test
	public void testAdd() {
		ComplexNumber c = new ComplexNumber(3.4, 5.6);
		ComplexNumber c2 = new ComplexNumber(-7.8, 9.0);
		
		Assert.assertEquals(scale(-4.4), scale(c.add(c2).getReal()));
		Assert.assertEquals(scale(14.6), scale(c.add(c2).getImaginary()));		
	}

	@Test
	public void testSub() {
		ComplexNumber c = new ComplexNumber(3.4, 5.6);
		ComplexNumber c2 = new ComplexNumber(-7.8, 9.0);
		
		Assert.assertEquals(scale(11.2), scale(c.sub(c2).getReal()));
		Assert.assertEquals(scale(-3.4), scale(c.sub(c2).getImaginary()));
	}

	@Test
	public void testMul() {
		ComplexNumber c = new ComplexNumber(3.4, 5.6);
		ComplexNumber c2 = new ComplexNumber(-7.8, 9.0);
		
		Assert.assertEquals(scale(-76.92), scale(c.mul(c2).getReal()));
		Assert.assertEquals(scale(-13.08), scale(c.mul(c2).getImaginary()));
	}

	@Test
	public void testDiv() {
		ComplexNumber c = new ComplexNumber(3.4, 5.6);
		ComplexNumber c2 = new ComplexNumber(-7.8, 9.0);
		
		Assert.assertEquals(scale(0.17), scale(c.div(c2).getReal()));
		Assert.assertEquals(scale(-0.52), scale(c.div(c2).getImaginary()));
	}

	@Test
	public void testPower() {
		ComplexNumber c = new ComplexNumber(3.4, 5.6);
		
		Assert.assertEquals(scale(4847.26), scale(c.power(5).getReal()));
		Assert.assertEquals(scale(-11052.15), scale(c.power(5).getImaginary()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPowerNegative() {
		ComplexNumber c = new ComplexNumber(3.4, 5.6);
		c.power(-3);
	}

	@Test
	public void testRoot() {
		ComplexNumber c = new ComplexNumber(56.43, -47.2);
		int n = 5;
		ComplexNumber[] roots = c.root(n);
		
		Assert.assertEquals(scale(1.03), scale(roots[0].getReal()));
		Assert.assertEquals(scale(2.12), scale(roots[0].getImaginary()));
		Assert.assertEquals(scale(-1.7), scale(roots[1].getReal()));
		Assert.assertEquals(scale(1.64), scale(roots[1].getImaginary()));
		Assert.assertEquals(scale(-2.09), scale(roots[2].getReal()));
		Assert.assertEquals(scale(-1.11), scale(roots[2].getImaginary()));
		Assert.assertEquals(scale(0.41), scale(roots[3].getReal()));
		Assert.assertEquals(scale(-2.33), scale(roots[3].getImaginary()));
		Assert.assertEquals(scale(2.34), scale(roots[4].getReal()));
		Assert.assertEquals(scale(-0.33), scale(roots[4].getImaginary()));
	}

	@Test
	public void testToString() {
		ComplexNumber c = new ComplexNumber(56.43, -47.2);
		Assert.assertEquals("56.43-47.2i", c.toString());
	}

}
