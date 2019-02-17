package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class Complex describes a final complex number. It has real and imaginary part.
 * It offers methods for operating with complex numbers:
 * 		add, subtract, multiply, divide, scalar product, cross product
 * and some other methods such as: 
 * 		module, negate, power, root
 * @author Alex
 *
 */
public class Complex {
	
	/**
	 * Real part of complex number.
	 */
	private final double re;
	
	/**
	 * Imaginary part of complex number.
	 */
	private final double im;

	/**
	 * Complex number whose module equals zero.
	 */
	public static final Complex ZERO = new Complex(0,0);
	
	/**
	 * Complex number whose real part is 1 and imaginary part is 0.
	 */
	public static final Complex ONE = new Complex(1,0);
	
	/**
	 * Complex number whose real part is -1 and imaginary part is 0.
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**
	 * Complex number whose real part is 0 and imaginary part is 1.
	 */
	public static final Complex IM = new Complex(0,1);
	
	/**
	 * Complex number whose real part is 0 and imaginary part is -1.
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Threshold for method equals.
	 */
	private final static double THRESHOLD = 0.00001;

	/**
	 * Constructor with no arguments. Sets re and im to 0.
	 */
	public Complex() {
		this(0, 0);
	}
	
	/**
	 * Constructor with arguments.
	 * @param re real part.
	 * @param im imaginary part.
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Calculates module of complex number
	 * @return double module
	 */
	public double module() {
		return Math.sqrt(re*re + im*im);
	}
	
	/**
	 * Multiplies two complex numbers.
	 * @param c complex number to multiply with.
	 * @return new complex number.
	 */
	public Complex multiply(Complex c) {
		Objects.requireNonNull(c, "Cannot multiply with null!");
		return new Complex(re*c.re - im*c.im, re*c.im + im*c.re);
	}
	
	/**
	 * Divides two complex numbers.
	 * @param c complex dividend.
	 * @return new complex number.
	 */
	public Complex divide(Complex c) {
		Objects.requireNonNull(c, "Cannot divide with null!");
		if(c.module() == 0) {
			throw new IllegalArgumentException("Cannot divide by 0!");
		}
		return fromModAndAngle(module()/c.module(), angle() - c.angle());
	}
	
	/**
	 * Adds complex number to this complex number.
	 * @param c complex number to be added.
	 * @return new complex number that equals sum of this and other.
	 */
	public Complex add(Complex c) {
		Objects.requireNonNull(c, "Cannot add null!");
		return new Complex(re+c.re, im+c.im);
	}
	
	/**
	 * Subtracts complex number from this complex number.
	 * @param c complex minuend.
	 * @return new complex number.
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c, "Cannot subtract null!");
		return new Complex(re-c.re, im-c.im);
	}
	
	/**
	 * Negates this complex number.
	 * @return -this
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	/**
	 * Calculates n-th power of this complex number.
	 * @param n power, must be non-negative.
	 * @return new complex number.
	 */
	public Complex power(int n) {
		return fromModAndAngle(Math.pow(module(), n), 
				n * angle());
	}
	
	/**
	 * Generates a list of complex roots of this number.
	 * @param n number of roots to be calculated, must be positive integer.
	 * @return list of complex numbers.
	 */
	public List<Complex> root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("Can't calculate negative root: " + n);
		}
		
		List<Complex> roots = new ArrayList<>();
		for(int i = 0; i < n; i++) {
			roots.add(fromModAndAngle(Math.pow(module(), 1.0 / n), 
					(angle() + 2.0*i*Math.PI) / n));
		}
		return roots;
	}
	
	/**
	 * String representation of complex number.
	 */
	@Override
	public String toString() {
		String c = im < 0 ? "" : "+";
		return re + c + im + "i";
	}
	
	/**
	 * Getter for real part of complex number.
	 * @return double real
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Getter for imaginary part of complex number.
	 * @return double imaginary
	 */
	public double getIm() {
		return im;
	}

	/**
	 * Method that creates new complex number from known module and angle.
	 * @param mod module of complex number.
	 * @param angle angle of complex number.
	 * @return new complex number.
	 */
	private Complex fromModAndAngle(double mod, double angle) {
		return new Complex(Math.abs(mod) * Math.cos(angle), Math.abs(mod) * Math.sin(angle));
	}
	
	/**
	 * Calculates angle of complex number.
	 * @return angle in radians as double value.
	 */
	private double angle() {
		return Math.atan2(im, re);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(im);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(re);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			System.out.println("class");
			return false;
		}
		Complex other = (Complex) obj;
		if (Math.abs(im - other.im) > THRESHOLD) {
			System.out.println("im");
			return false;
		}
		if (Math.abs(re - other.re) > THRESHOLD) {
			System.out.println("re");
			System.out.println(re);
			System.out.println(other.re);
			return false;
		}
		return true;
	}
	
}
