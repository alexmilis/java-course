package hr.fer.zemris.java.hw16.jvdraw.object.visitor;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw16.jvdraw.object.Circle;
import hr.fer.zemris.java.hw16.jvdraw.object.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.object.Line;

/**
 * Geometrical object visitor that paints geometrical objects.
 * @author Alex
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Graphics used to draw objects.
	 */
	private Graphics2D g;
	
	/**
	 * Constructor.
	 * @param g
	 * 				graphics2D
	 */
	public GeometricalObjectPainter(Graphics2D g) {
		super();
		this.g = g;
	}

	@Override
	public void visit(Line line) {
		g.setColor(line.getColor());
		g.drawLine(line.getStart().x, line.getStart().y, 
					line.getEnd().x, line.getEnd().y);
	}

	@Override
	public void visit(Circle circle) {
		int radius = circle.getRadius();
		g.setColor(circle.getColor());
		g.drawOval(circle.getCenter().x - radius,
					circle.getCenter().y - radius,
					radius * 2, radius * 2);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		int radius = filledCircle.getRadius();
		
		g.setColor(filledCircle.getColor());
		g.drawOval(filledCircle.getCenter().x - radius,
					filledCircle.getCenter().y - radius,
					radius * 2, radius * 2);
		
		g.setColor(filledCircle.getBgColor());
		g.fillOval(filledCircle.getCenter().x - radius,
					filledCircle.getCenter().y - radius,
					radius * 2, radius * 2);
	}

}
