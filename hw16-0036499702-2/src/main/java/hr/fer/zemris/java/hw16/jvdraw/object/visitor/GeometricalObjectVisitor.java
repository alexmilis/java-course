package hr.fer.zemris.java.hw16.jvdraw.object.visitor;

import hr.fer.zemris.java.hw16.jvdraw.object.Circle;
import hr.fer.zemris.java.hw16.jvdraw.object.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.object.Line;

/**
 * Visitor that visits {@link GeometricalObject}.
 * @author Alex
 *
 */
public interface GeometricalObjectVisitor {

	/**
	 * Visit line.
	 * @param line
	 * 				line to be visited
	 */
	public abstract void visit(Line line);
	
	/**
	 * Visit circle.
	 * @param circle
	 * 				circle to be visited
	 */
	public abstract void visit(Circle circle);
	
	/**
	 * Visit filled circle.
	 * @param filledCircle
	 * 				filled circle to be visited
	 */
	public abstract void visit(FilledCircle filledCircle);
	 
}
