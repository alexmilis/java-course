package hr.fer.zemris.math;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ComplexRootedPolynomialTest {
	
	@Test
	public void testToComplexPolynom() {
		List<Complex> roots = new ArrayList<>();
		roots.add(Complex.ONE);
		roots.add(Complex.ONE_NEG);
		roots.add(Complex.IM);
		roots.add(Complex.IM_NEG);
		
		ComplexRootedPolynomial rooted = new ComplexRootedPolynomial(roots.toArray(new Complex[1]));
		ComplexPolynomial polynomial = rooted.toComplexPolynom();
		List<Complex> factors = polynomial.getFactors();
		
		assertTrue("First", factors.get(0).equals(Complex.ONE_NEG));
		
		for(int i = 1; i < 4; i++) {
			assertTrue(factors.get(i).equals(Complex.ZERO));
		}
		
		assertTrue("Last", factors.get(4).equals(Complex.ONE));
	}
	
	@Test
	public void testToComplexPolynom2() {
		List<Complex> roots = new ArrayList<>();
		roots.add(new Complex(2, 0));
		roots.add(new Complex(3, 0));
		roots.add(new Complex(4, 0));
		
		ComplexRootedPolynomial rooted = new ComplexRootedPolynomial(roots.toArray(new Complex[1]));
		ComplexPolynomial polynomial = rooted.toComplexPolynom();
		List<Complex> factors = polynomial.getFactors();
		
		assertTrue(factors.get(0).equals(new Complex(-24, 0)));

		assertTrue(factors.get(1).equals(new Complex(26, 0)));
		assertTrue(factors.get(2).equals(new Complex(-9, 0)));
		assertTrue(factors.get(3).equals(Complex.ONE));
	}
	

	@Test
	public void testIndexOfClosestRootFor() {
		List<Complex> roots = new ArrayList<>();
		roots.add(Complex.ONE);
		roots.add(Complex.ONE_NEG);
		roots.add(Complex.IM);
		roots.add(Complex.IM_NEG);
		
		ComplexRootedPolynomial rooted = new ComplexRootedPolynomial(roots.toArray(new Complex[1]));
		
		int index = rooted.indexOfClosestRootFor(new Complex(-0.9995, 0), 0.002);
		assertTrue(index == 2);
	}
	
	@Test
	public void testIndexOfClosestRootFor2() {
		List<Complex> roots = new ArrayList<>();
		roots.add(new Complex(23, -9));
		roots.add(new Complex(12, 22));
		roots.add(new Complex(-12, 32));
		roots.add(new Complex(-23, 9));
		
		ComplexRootedPolynomial rooted = new ComplexRootedPolynomial(roots.toArray(new Complex[1]));
		
		int index = rooted.indexOfClosestRootFor(new Complex(-22.7, 9.1), 0.01);
		assertEquals(-1, index);
	}

}
