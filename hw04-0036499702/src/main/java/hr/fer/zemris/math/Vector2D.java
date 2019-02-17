package hr.fer.zemris.math;

import java.text.DecimalFormat;

/**
 * Class used to describe a 2-dimensional vector.
 * @author Alex
 *
 */
public class Vector2D {
	
	/**
	 * x component of vector.
	 */
	private double x;
	
	/**
	 * y component of vector.
	 */
	private double y;
	
	/**
	 * Constructor.
	 * @param x component
	 * @param y component
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x component.
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for y component.
	 * @return
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Adds vector offset to self.
	 * @param offset
	 */
	public void translate(Vector2D offset) {
		this.x = this.x + offset.x;
		this.y = this.y + offset.y;
	}
	
	/**
	 * Creates new vector that equals sum of this vector and offset.
	 * @param offset
	 * @return vector that equals sum
	 */
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	/**
	 * Rotates vector for given angle.
	 * @param angle in degrees
	 */
	public void rotate(double angle) {
		angle = angleToRadian(angle);
		double x2 = Math.cos(angle)*this.x - Math.sin(angle)*this.y;
		double y2 = Math.sin(angle)*this.x + Math.cos(angle)*this.y;
		this.x = x2;
		this.y = y2;
	}
	
	/**
	 * Creates new vector that equals this one rotated for given angle.
	 * @param angle in degrees
	 * @return rotated vector
	 */
	public Vector2D rotated(double angle) {
		angle = angleToRadian(angle);
		double x2 = Math.cos(angle)*this.x - Math.sin(angle)*this.y;
		double y2 = Math.sin(angle)*this.x + Math.cos(angle)*this.y;
		return new Vector2D(x2, y2);
	}

	/**
	 * Scales vector with factor scaler.
	 * @param scaler
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	/**
	 * Creates new vector that equals this one scaled.
	 * @param scaler
	 * @return scaled vector
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}
	
	/**
	 * Creates new vector that is copy of this one.
	 * @return vector copy
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}
	
	/**
	 * String representation of vector, rounded to 2 decimals.
	 */
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(this.x) + "i + " + df.format(this.y) + "j";
	}
	
	/**
	 * Help methode used for turning angle from degrees to radians.
	 * @param angle
	 * @return
	 */
	private double angleToRadian(double angle) {
		return angle * Math.PI / 180.0;
	}
	
}
