package hr.fer.zemris.math;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ComplexPolynomialTest {
	
	@Test
	public void testOrder() {
		List<Complex> factors = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			factors.add(Complex.ONE);
		}
		ComplexPolynomial poly = new ComplexPolynomial(factors.toArray(new Complex[1]));
		assertEquals(4, poly.order());
	}

	@Test
	public void testMultiply() {
		List<Complex> factors = new ArrayList<>();
		factors.add(new Complex(5, 0));
		factors.add(new Complex(4, 0));
		factors.add(new Complex(3, 0));
		factors.add(new Complex(2, 0));
		factors.add(new Complex(1, 0));
		ComplexPolynomial poly1 = new ComplexPolynomial(factors.toArray(new Complex[1]));
		
		List<Complex> factors2 = new ArrayList<>();
		factors2.add(new Complex(9, 0));
		factors2.add(new Complex(1, 0));
		factors2.add(new Complex(2, 0));
		ComplexPolynomial poly2 = new ComplexPolynomial(factors2.toArray(new Complex[1]));
		
		ComplexPolynomial poly3 = poly1.multiply(poly2);
		List<Complex> factors3 = poly3.getFactors();
		
		assertTrue(factors3.get(0).equals(new Complex(45, 0)));
		assertTrue(factors3.get(1).equals(new Complex(41, 0)));
		assertTrue(factors3.get(2).equals(new Complex(41, 0)));
		assertTrue(factors3.get(3).equals(new Complex(29, 0)));
		assertTrue(factors3.get(4).equals(new Complex(17, 0)));
		assertTrue(factors3.get(5).equals(new Complex(5, 0)));
		assertTrue(factors3.get(6).equals(new Complex(2, 0)));
	}
	
	@Test
	public void testDerive() {
		ComplexPolynomial poly1 = new ComplexPolynomial(
				new Complex(3, 0),
				new Complex(6, 0),
				new Complex(8, 0),
				new Complex(0, 0),
				new Complex(1, 0));
		
		ComplexPolynomial poly2 = poly1.derive();
		List<Complex> factors = poly2.getFactors();
				
		assertTrue(factors.get(0).equals(new Complex(12, 0)));
		assertTrue(factors.get(1).equals(new Complex(18, 0)));
		assertTrue(factors.get(2).equals(new Complex(16, 0)));
		assertTrue(factors.get(3).equals(new Complex(0, 0)));
	}

}
