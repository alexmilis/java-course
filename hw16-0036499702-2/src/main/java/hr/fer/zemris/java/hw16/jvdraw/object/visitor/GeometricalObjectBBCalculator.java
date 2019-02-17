package hr.fer.zemris.java.hw16.jvdraw.object.visitor;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw16.jvdraw.object.Circle;
import hr.fer.zemris.java.hw16.jvdraw.object.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.object.Line;

/**
 * Geometrical object visitor that calculates the smallest bounding box that encapsulates all drawn objects. 
 * It is used during export.
 * @author Alex
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Maximum value of x.
	 */
	private int xmax;
	
	/**
	 * Maximum value of y.
	 */
	private int ymax;
	
	/**
	 * Minimum value of x.
	 */
	private int xmin;
	
	/**
	 * Minimum value of y.
	 */
	private int ymin;
	
	/**
	 * Constructor. Initializes all values to 0.
	 */
	public GeometricalObjectBBCalculator() {
		xmax = 0;
		xmin = 0;
		ymax = 0;
		ymin = 0;
	}
	
	@Override
	public void visit(Line line) {
		xmax = getMax(xmax, line.getStart().x, line.getEnd().x);
		xmin = getMin(xmin, line.getStart().x, line.getEnd().x);
		ymax = getMax(ymax, line.getStart().y, line.getEnd().y);
		ymin = getMin(ymin, line.getStart().y, line.getEnd().y);
	}

	@Override
	public void visit(Circle circle) {
		xmax = circle.getCenter().x + circle.getRadius() > xmax ? circle.getCenter().x + circle.getRadius() : xmax;
		xmin = circle.getCenter().x - circle.getRadius() < xmin ? circle.getCenter().x - circle.getRadius() : xmin;
		ymax = circle.getCenter().y + circle.getRadius() > ymax ? circle.getCenter().y + circle.getRadius() : ymax;
		ymin = circle.getCenter().y - circle.getRadius() < ymin ? circle.getCenter().y - circle.getRadius() : ymin;
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		visit((Circle) filledCircle);
	}
	
	/**
	 * Gets smallest bounding box.
	 * @return
	 * 				bounding box
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(xmin, ymin, xmax - xmin, ymax - ymin);
	}
	
	/**
	 * Returns the biggest of three given values.
	 * @param value1
	 * @param value2
	 * @param value3
	 * @return
	 * 				biggest value
	 */
	private int getMax(int value1, int value2, int value3) {
		if(value1 >= value2 && value1 >= value3)
			return value1;
		if(value2 >= value1 && value2 >= value3)
			return value2;
		return value3;
	}
	
	/**
	 * Returns the smallest of three given values.
	 * @param value1
	 * @param value2
	 * @param value3
	 * @return
	 * 				smallest value
	 */
	private int getMin(int value1, int value2, int value3) {
		if(value1 <= value2 && value1 <= value3)
			return value1;
		if(value2 <= value1 && value2 <= value3)
			return value2;
		return value3;
	}

}
