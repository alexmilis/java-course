package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Complex polynomial models a polynomial of complex numbers in form
 * 		f(z) = zn*z^n + z(n-1)*z^(n-1) + ... + z2*z^2 + z1*z + z0
 * zn to z0 are coefficients which are given in constructor.
 * Method apply takes one argument, concrete complex number z, 
 * and calculates value of polynomial for that number.
 * @author Alex
 *
 */
public class ComplexPolynomial {
	
	/**
	 * Final list of complex factors.
	 */
	private final List<Complex> factors = new ArrayList<>();
	
	/**
	 * Constructor of complex polynomial.
	 * @param factors list of complex numbers.
	 * 			coefficient with the lowest index is next to the highest power of z
	 */
	public ComplexPolynomial(Complex... factors) {
		if(factors == null || factors.length == 0) {
			throw new IllegalArgumentException("No factors were given.");
		}
		for(int i = 0; i < factors.length; i++) {
			this.factors.add(factors[i]);
		}
	}
	
	/**
	 * Returns order of this polynomial.
	 * eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	 * @return
	 */
	// 
	public short order() {
		return (short) (factors.size() - 1);
	}
	
	/**
	 * Computes a new polynomial this*p.
	 * @param p complex polynomial.
	 * @return new complex polynomial.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Objects.requireNonNull(p, "Cannot multiply with null!");
		List<Complex> factors2 = p.getFactors();

		List<Complex> factors3 = new ArrayList<>();
		for(int i = 0; i < (order() + 1) * (p.order() + 1); i++) {
			factors3.add(Complex.ZERO);
		}
		
		for(int i = 0, imax = factors.size(); i < imax; i++) {
			for(int j = 0, jmax = factors2.size(); j < jmax; j++) {
				factors3.set(i + j, factors3.get(i + j)
						.add(factors.get(i).multiply(factors2.get(j))));
			}
		}
		
		return new ComplexPolynomial(factors3.toArray(new Complex[1]));
	}
	
	/**
	 * Computes first derivative of this polynomial.
	 * for example, for (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * @return
	 */
	public ComplexPolynomial derive() {
		List<Complex> factors2 = new ArrayList<>();
		for(int i = 0; i < factors.size() - 1; i++) {
			factors2.add(factors.get(i).multiply(new Complex(factors.size() - 1 - i, 0)));
		}
		return new ComplexPolynomial(factors2.toArray(new Complex[1]));
	}
	
	/**
	 * Computes polynomial value at given point z.
	 * @param z complex number.
	 * @return new complex number.
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z, "Cannot apply to null!");
		Complex result = Complex.ZERO;
		for(int i = 0, imax = factors.size(); i < imax; i++) {
			result = result.add(factors.get(i).multiply(z.power(factors.size() - 1 - i)));
		}
		return result;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = factors.size() - 1; i >= 0; i--) {
			sb.append("(").append(factors.get(i).toString())
			.append(")*(").append("z^").append(i).append("+");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * Getter for field factors.
	 * @return list of complex numbers.
	 */
	public List<Complex> getFactors() {
		return factors;
	}

}
