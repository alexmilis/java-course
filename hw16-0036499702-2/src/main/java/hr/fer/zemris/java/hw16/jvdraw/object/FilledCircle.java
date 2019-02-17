package hr.fer.zemris.java.hw16.jvdraw.object;

import java.awt.Color;
import java.awt.Point;
import java.util.StringJoiner;

import hr.fer.zemris.java.hw16.jvdraw.object.editor.FilledCircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.object.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.object.visitor.GeometricalObjectVisitor;

/**
 * Implementation of {@link GeometricalObject} that represents a filled circle.
 * FilledCircle has properties center, radius and background color.
 * Auto-generated name is in form "Filled circle#" where # is auto-incremented number.
 * 
 * @author Alex
 *
 */
public class FilledCircle extends Circle {
	
	/**
	 * Background color used to fill circle.
	 */
	private Color bgColor;
	
	/**
	 * Base string of name.
	 */
	private static final String DEFAULT_NAME = "FilledCircle ";
	
	/**
	 * String used to save geometrical object.
	 */
	private static final String SAVE_NAME = "FCIRCLE ";
	
	/**
	 * Constructor of circle.
	 * @param center
	 * 				center of circle
	 * @param radius
	 * 				radius of circle
	 * @param color
	 * 				color of circle
	 * @param bgColor
	 * 				background color of fill
	 */
	public FilledCircle(Point center, int radius, Color color, Color bgColor) {
		super(center, radius, color);
		this.bgColor = bgColor;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}
	
	/**
	 * Getter for fill color.
	 * @return
	 * 				background color
	 */
	public Color getBgColor() {
		return bgColor;
	}

	/**
	 * Setter for fill color.
	 * @param bgColor
	 */
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		notifyListeners(this);
	}
	
	@Override
	public String getSaveLine() {
		StringJoiner sj = new StringJoiner(" ", SAVE_NAME.toUpperCase(), "");
		
		sj.add(Integer.toString(center.x)).add(Integer.toString(center.y));
		sj.add(Integer.toString(radius));
		sj.add(Integer.toString(color.getRed())).add(Integer.toString(color.getGreen())).add(Integer.toString(color.getBlue()));
		sj.add(Integer.toString(bgColor.getRed())).add(Integer.toString(bgColor.getGreen())).add(Integer.toString(bgColor.getBlue()));
		
		return sj.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(DEFAULT_NAME);
		sb.append("(").append(center.x).append(",").append(center.y);
		sb.append("),").append(radius);
		sb.append(",").append(String.format("#%02X%02X%02X", bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue()));
		
		return sb.toString();
	}
	
}
