package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Class that describes the state of turtle. It contains turtle's position, direction, color and effective step.
 * @author Alex
 *
 */
public class TurtleState {
	
	/**
	 * Current position of turtle.
	 */
	private Vector2D position;
	
	/**
	 * Current direction turtle is facing.
	 */
	private Vector2D direction;
	
	/**
	 * Current color of turtle.
	 */
	private Color color;
	
	/**
	 * Current effective step of turtle.
	 */
	private double effectiveStep;
	
	/**
	 * Constructor of class TurtleState.
	 * @param position Vector2D of current position
	 * @param direction Vector2D of current direction
	 * @param color current color
	 * @param effectiveStep current effectiveStep
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double effectiveStep) {
		super();
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.effectiveStep = effectiveStep;
	}

	/**
	 * Method that returns a copy of this state.
	 * @return TurtleState copy
	 */
	public TurtleState copyOf() {
		return new TurtleState(
				this.position,
				this.direction,
				this.color,
				this.effectiveStep);
	}

	/**
	 * Getter for current position.
	 * @return Vector2D position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Setter for current position.
	 * @param position
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Getter for current direction.
	 * @return Vector2D direction
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Setter for current direction.
	 * @param direction Vector2D
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Getter for current color.
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for current color.
	 * @param color of type java.awt.color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Getter for current effective step.
	 * @return double step
	 */
	public double getEffectiveStep() {
		return effectiveStep;
	}

	/**
	 * Setter for current effectiveStep.
	 * @param effectiveStep double
	 */
	public void setEffectiveStep(double effectiveStep) {
		this.effectiveStep = effectiveStep;
	}	
	
}
