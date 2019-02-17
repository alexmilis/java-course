package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Polynomial of n-th degree, determined by its roots (z1, z2, ..., zn).
 * It can be written in form:
 * 		f(z) = (z-z1) * (z - z2) * ... * (z - zn)
 * Instance is initialized by an array of roots.
 * @author Alex
 *
 */
public class ComplexRootedPolynomial {
	
	/**
	 * Roots.
	 */
	private final List<Complex> roots = new ArrayList<>();
	
	/**
	 * Constructor for complex rooted polynomial.
	 * @param roots array of complex numbers that are roots
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		if(roots == null || roots.length == 0) {
			throw new IllegalArgumentException("No roots were given.");
		}
		for(int i = 0; i < roots.length; i++) {
			this.roots.add(roots[i]);
		}
	}
	
	/**
	 * Computes polynomial value at given point.
	 * @param z complex number.
	 * @return new complex number.
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z, "Cannot apply to null!");
		Complex result = new Complex(1, 1);
		for(Complex zi : roots) {
			result = result.multiply(z.sub(zi));
		}
		return result;
	}
	
	/**
	 * Converts this representation to ComplexPolynomial type.
	 * @return complex polynomial.
	 */
	public ComplexPolynomial toComplexPolynom() {
		List<Complex> factors = new ArrayList<>();
		for(int i = 0; i <= roots.size(); i++) {
			factors.add(Complex.ZERO);
		}
		
		int imax = (int) Math.pow(2, roots.size());
		String format = "%" + (Integer.toBinaryString(imax).length() - 1) + "s";
		for(int i = 0; i < imax; i++) {
			String bin = String.format(format, Integer.toBinaryString(i)).replaceAll(" ", "0");
			Complex factor = Complex.ONE;
			int index = 0;
			for(int j = 0, jmax = bin.length(); j < jmax; j++) {
				if(bin.charAt(j) == '1') {
					index++;
					factor = factor.multiply(roots.get(j).negate());
				}
			}
			factors.set(factors.size() - 1 - index, factors.get(factors.size() - 1 - index).add(factor));
		}
		
		return new ComplexPolynomial(factors.toArray(new Complex[1]));
	}
	
	/**
	 * String representation of ComplexRootedPolynomial.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Complex  zi : roots) {
			sb.append("(z - ").append(zi.toString()).append(") * ");
		}
		sb.delete(sb.lastIndexOf(")"), sb.length());
		return sb.toString();
	}
	
	/**
	 * Finds index of closest root for given complex number z that is within
	 * threshold, if there is no such root, returns -1.
	 * @param z complex number.
	 * @param threshold
	 * @return index of closest root.
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		Objects.requireNonNull(z, "Cannot find closest root for null!");
		int index = 0;
		for(int i = 0, imax = roots.size(); i < imax; i++) {
			if(Math.abs(roots.get(i).sub(z).module()) <= roots.get(index).sub(z).module()) {
				index = i;
			}
		}
		
		if(roots.get(index).sub(z).module() < threshold) {
			return index + 1;
		}
		
		return -1;
	}


}
