package hr.fer.zemris.java.hw16.jvdraw.object;

import java.awt.Color;
import java.awt.Point;
import java.util.StringJoiner;

import hr.fer.zemris.java.hw16.jvdraw.object.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.object.editor.LineEditor;
import hr.fer.zemris.java.hw16.jvdraw.object.visitor.GeometricalObjectVisitor;

/**
 * Implementation of {@link GeometricalObject} that represents a line.
 * Line has properties start and end point.
 * Auto-generated name is in form "Line#" where # is auto-incremented number.
 * 
 * @author Alex
 *
 */
public class Line extends GeometricalObject {
	
	/**
	 * Start point of line.
	 */
	private Point start;
	
	/**
	 * End point of line.
	 */
	private Point end;
	
	/**
	 * Base string of name.
	 */
	private static final String DEFAULT_NAME = "Line ";
	
	/**
	 * Constructor of line.
	 * @param start
	 * 				start point
	 * @param end
	 * 				end point
	 * @param color
	 * 				color of line
	 */
	public Line(Point start, Point end, Color color) {
		this.start = start;
		this.end = end;
		this.color = color;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

	/**
	 * Getter for start point.
	 * @return
	 * 				start point of line
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * Setter for start point.
	 * @param start
	 */
	public void setStart(Point start) {
		this.start = start;
		notifyListeners(this);
	}

	/**
	 * Getter for end point.
	 * @return
	 * 				end point of line
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * Setter for end point of line
	 * @param end
	 */
	public void setEnd(Point end) {
		this.end = end;
		notifyListeners(this);
	}

	@Override
	public String getSaveLine() {
		StringJoiner sj = new StringJoiner(" ", DEFAULT_NAME.toUpperCase(), "");
		
		sj.add(Integer.toString(start.x)).add(Integer.toString(start.y));
		sj.add(Integer.toString(end.x)).add(Integer.toString(end.y));
		sj.add(Integer.toString(color.getRed())).add(Integer.toString(color.getGreen())).add(Integer.toString(color.getBlue()));
		
		return sj.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(DEFAULT_NAME);
		sb.append("(").append(start.x).append(",").append(start.y).append(")-(");
		sb.append(end.x).append(",").append(end.y).append(")");
		
		return sb.toString();
	}
	
}
