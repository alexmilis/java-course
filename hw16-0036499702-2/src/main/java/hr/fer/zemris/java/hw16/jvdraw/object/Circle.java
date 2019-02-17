package hr.fer.zemris.java.hw16.jvdraw.object;

import java.awt.Color;
import java.awt.Point;
import java.util.StringJoiner;

import hr.fer.zemris.java.hw16.jvdraw.object.editor.CircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.object.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.object.visitor.GeometricalObjectVisitor;

/**
 * Implementation of {@link GeometricalObject} that represents a circle.
 * Circle has properties center and radius.
 * Auto-generated name is in form "Circle#" where # is auto-incremented number.
 * 
 * @author Alex
 *
 */
public class Circle extends GeometricalObject {
	
	/**
	 * Center of circle.
	 */
	protected Point center;
	
	/**
	 * Radius of circle.
	 */
	protected int radius;
	
	/**
	 * Base string of name.
	 */
	private static final String DEFAULT_NAME = "Circle ";
	
	/**
	 * Constructor of circle.
	 * @param center
	 * 				center of circle
	 * @param radius
	 * 				radius of circle
	 * @param color
	 * 				color of circle
	 */
	public Circle(Point center, int radius, Color color) {
		this.center = center;
		this.radius = radius;
		this.color = color;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	/**
	 * Getter for circle center.
	 * @return
	 * 				center of circle
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Setter for center of circle.
	 * @param center
	 */
	public void setCenter(Point center) {
		this.center = center;
		notifyListeners(this);
	}

	/**
	 * Getter for radius of circle.
	 * @return
	 * 				radius of circle
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Setter for radius of circle.
	 * @param radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
		notifyListeners(this);
	}

	@Override
	public String getSaveLine() {
		StringJoiner sj = new StringJoiner(" ", DEFAULT_NAME.toUpperCase(), "");
		
		sj.add(Integer.toString(center.x)).add(Integer.toString(center.y));
		sj.add(Integer.toString(radius));
		sj.add(Integer.toString(color.getRed())).add(Integer.toString(color.getGreen())).add(Integer.toString(color.getBlue()));
		
		return sj.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(DEFAULT_NAME);
		sb.append("(").append(center.x).append(",").append(center.y);
		sb.append("),").append(radius);
		
		return sb.toString();
	}

}
