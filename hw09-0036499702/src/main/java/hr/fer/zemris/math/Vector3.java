package hr.fer.zemris.math;

import java.util.Locale;
import java.util.Objects;

/**
 * Class Vector 3 is used to describe 3D vector. Each vector is final and has 3 components: x, y and z.
 * It offers various methods for operations with vectors. Methods return new Vector3.
 * @author Alex
 *
 */
public class Vector3 {
	
	/**
	 * x component of vector.
	 */
	private final double x;
	
	/**
	 * y component of vector.
	 */
	private final double y;
	
	/**
	 * z component of vector.
	 */
	private final double z;
	
	/**
	 * Threshold used in method equals.
	 */
	private final static double THRESHOLD = 0.00001;

	
	/**
	 * Constructor of 3d vector.
	 * @param x x component
	 * @param y y component
	 * @param z z component
	 */
	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Calculates norm of vector.
	 * @return double norm
	 */
	public double norm() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	/**
	 * Returns normalized vector.
	 * @return vector3
	 */
	public Vector3 normalized() {
		double norm = norm();
		return new Vector3(x/norm, y/norm, z/norm);
	}
	
	/**
	 * Calculates sum of two vectors.
	 * @param other vector to be added to this vector.
	 * @return new vector that equals sum of this and other.
	 */
	public Vector3 add(Vector3 other) {
		Objects.requireNonNull(other, "Cannot add null!");
		return new Vector3(x+other.x, y+other.y, z+other.z);
	}
	
	/**
	 * Subtracts other vector from this one.
	 * @param other vector to be subtractred.
	 * @return new vector that equals difference of this and other.
	 */
	public Vector3 sub(Vector3 other) {
		Objects.requireNonNull(other, "Cannot subtract null!");
		return new Vector3(x-other.x, y-other.y, z-other.z);
	}
	
	/**
	 * Calculates scalar product of this and other vector.
	 * @param other vector.
	 * @return double value of scalar product.
	 */
	public double dot(Vector3 other) {
		Objects.requireNonNull(other, "Cannot calculate scalar product with null!");
		return x*other.x + y*other.y + z*other.z;
	}
	
	/**
	 * Vector product of this and other vector.
	 * @param other vector.
	 * @return new vector that is cross product of this and other.
	 */
	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other, "Cannot calculate cross product with null!");
		double x1 = y*other.z - z*other.y;
		double y1 = z*other.x - x*other.z;
		double z1 = x*other.y - y*other.x;
		return new Vector3(x1, y1, z1);
	}
	
	/**
	 * Scales this vector with value s.
	 * @param s double value to be scaled with.
	 * @return scaled vector.
	 */
	public Vector3 scale(double s) {
		return new Vector3(x*s, y*s, z*s);
	}
	
	/**
	 * Calculates cosine of angle between this and other vector.
	 * @param other vector.
	 * @return cos(angle) as double value.
	 */
	public double cosAngle(Vector3 other) {
		Objects.requireNonNull(other, "Cannot calculate angle with null!");
		return dot(other) / (norm() * other.norm());
	}
	
	/**
	 * Getter for x components of vector.
	 * @return double x.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Getter for y component of vector.
	 * @return double y.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Getter for z component of vector.
	 * @return double z.
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Representation of vector as an array of 3 elements.
	 * @return array of doubles.
	 */
	public double[] toArray() {
		return new double[] {x, y, z};
	}
	
	/**
	 * String representation of vector.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(String.format(Locale.US, "%.6f", x)).append(", ")
			.append(String.format(Locale.US, "%.6f", y)).append(", ")
			.append(String.format(Locale.US, "%.6f", z)).append(")");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
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
			return false;
		}
		Vector3 other = (Vector3) obj;
		if (Math.abs(x - other.x) > THRESHOLD) {
			return false;
		}
		if (Math.abs(y - other.y) > THRESHOLD) {
			return false;
		}
		if (Math.abs(z - other.z) > THRESHOLD) {
			return false;
		}
		return true;
	}
	
}
