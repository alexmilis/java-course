package hr.fer.zemris.java.hw02;

import java.lang.Math;
import java.util.Objects;

/**
 * Class used to describe complex numbers. Complex number have real and imaginary parts.
 * Complex numbers can also be defined by their magnitude and angle.
 * @author Alex
 *
 */
public class ComplexNumber {
	
	private final double real;
	private final double imaginary;
	
	/**
	 * Creates a complex number with given real and imaginary parts.
	 * @param real real part of complex number
	 * @param imaginary imaginary part of complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = Objects.requireNonNull(real);
		this.imaginary = Objects.requireNonNull(imaginary);
	}
	
	/**
	 * Private constructor that is used only to initialize an array in 
	 * method roots, cannot be invoked anywhere outside this class
	 */
	private ComplexNumber() {
		this.real = 0;
		this.imaginary = 0;
	}
	
	//public static factory
	/**
	 * Creates complex number when given only real part, imaginary is set to 0.0.
	 * @param real real part of complex number
	 * @return complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Creates complex number when given only imaginary part, real is set to 0.0.
	 * @param imaginary imaginary part of complex number
	 * @return complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Creates complex number from magnitude and angle. 
	 * @param magnitude
	 * @param angle
	 * @return complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(Math.abs(magnitude) * Math.cos(angle), Math.abs(magnitude) * Math.sin(angle));
	}
	
	/**
	 * Creates complex number from string. 
	 * @param s string to parse
	 * @return complex number
	 * @throws NumberFormatException if string can't be turned to complex number
	 */
	public static ComplexNumber parse(String s) {
		int breakpoint = s.lastIndexOf("-");
		if(breakpoint == -1 || breakpoint == 0) {
			breakpoint = s.lastIndexOf("+");
		}
		
		double re = 0;
		double im = 0;
		
		try {
			if(breakpoint == -1) {
				if(s.contains("i")) {
					im = Double.parseDouble(s.substring(0, s.length() - 1));
				} else {
					re = Double.parseDouble(s);
				}
			} else {
				if(s.indexOf("i") < breakpoint) {
					im = Double.parseDouble(s.substring(0, s.indexOf("i")));
					re = Double.parseDouble(s.substring(breakpoint));
				} else {
					re = Double.parseDouble(s.substring(0, breakpoint));
					im = Double.parseDouble(s.substring(breakpoint, s.length() - 1));
				}
			}
		} catch (NumberFormatException ex) {
			System.err.println(s + " is not a number");
		}
		return new ComplexNumber(re, im);
	}
	
	//public instance methods for information retrieval
	/**
	 * Returns real part of complex number.
	 * @return real
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Returns imaginary part of complex number.
	 * @return imaginary
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Calculates magnitude of complex number.
	 * @return magnitude
	 */
	public double getMagnitude() {
		return Math.sqrt(real*real + imaginary*imaginary);
	}
	
	/**
	 * Calculates angle of complex number. Angle is from interval [0, 2PI].
	 * @return angle
	 */
	public double getAngle() {
		double a = Math.atan2(imaginary, real);
		return a < 0 ? a + Math.PI*2.0 : a;
	}
	
	//public instance methods which allow calculations
	/**
	 * Calculates the sum of 2 complex numbers.
	 * @param c complex number that should be added to number who invoked the method
	 * @return complex number
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.getReal(), imaginary + c.getImaginary());
	}
	
	/**
	 * Calculates the difference of 2 complex numbers.
	 * @param c complex number that should be subtracted
	 * @return complex number
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.getReal(), imaginary - c.getImaginary());
	}
	
	/**
	 * Multiplies 2 complex numbers.
	 * @param c complex number that is the second operand
	 * @return complex number that is the result of multiplication
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return fromMagnitudeAndAngle(getMagnitude() * c.getMagnitude(), 
				getAngle() + c.getAngle());
	}
	
	/**
	 * Divides 2 complex numbers.
	 * @param c complex number that is the second operand
	 * @return complex number that is the result of division
	 */
	public ComplexNumber div(ComplexNumber c) {
		return fromMagnitudeAndAngle(getMagnitude()/c.getMagnitude(), 
				getAngle() - c.getAngle());
	}
	
	/**
	 * Calculates nth power of complex number.
	 * @param n power to which complex number should be raised
	 * @return complex number
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("Power can't be negative: " + n);
		}
		return fromMagnitudeAndAngle(Math.pow(getMagnitude(), n), 
				n * getAngle());
	}

	/**
	 * Calculates nth roots of complex number.
	 * @param n number of roots 
	 * @return array of complex number that are nth roots
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("Can't calculate negative root: " + n);
		}
		
		ComplexNumber[] result = new ComplexNumber[n];
		for(int i = 0; i < n; i++) {
			result[i] = fromMagnitudeAndAngle(Math.pow(getMagnitude(), 1.0 / n), 
					(getAngle() + 2.0*i*Math.PI) / n);
		}
		return result;
	}
	
	//method for conversion
	/**
	 * Method that generates string representation of complex number.
	 * @return string reprensentation of complex number
	 */
	@Override
	public String toString() {
		String c = imaginary < 0 ? "" : "+";
		return real + c + imaginary + "i";
	}
}
