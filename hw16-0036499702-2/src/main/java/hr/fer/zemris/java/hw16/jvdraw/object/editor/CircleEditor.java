package hr.fer.zemris.java.hw16.jvdraw.object.editor;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.object.Circle;

/**
 * Editor of {@link Circle}. Can edit center point, radius and color.
 * @author Alex
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Circle that is being edited.
	 */
	private Circle circle;
	
	/**
	 * X coordinate of center.
	 */
	private int x;
	
	/**
	 * Y coordinate of center.
	 */
	private int y;
	
	/**
	 * Radius of circle.
	 */
	private int radius;
	
	/**
	 * Text field for input of x.
	 */
	private JTextField inputX;
	
	/**
	 * Text field for input of y.
	 */
	private JTextField inputY;
	
	/**
	 * Text field for input of radius.
	 */
	private JTextField inputRadius;
	
	/**
	 * Color area for choosing new color of circle.
	 */
	private JColorArea inputColor;
	
	/**
	 * Constructor.
	 * @param circle
	 * 				circle to be edited
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		initGUI();
	}
	
	/**
	 * Initializes graphic user interface of this component. Offers fields for input of new values.
	 */
	private void initGUI() {
		setLayout(new GridLayout(8, 2));
		add(new JLabel("Edit circle"));
		
		add(new JLabel());
		add(new JLabel("Center:"));
		add(new JLabel());
		
		inputX = new JTextField(Integer.toString(circle.getCenter().x));
		add(new JLabel("	x: "));
		add(inputX);
		
		inputY = new JTextField(Integer.toString(circle.getCenter().y));
		add(new JLabel("	y:"));
		add(inputY);
		
		inputRadius = new JTextField(Integer.toString(circle.getRadius()));
		add(new JLabel("Radius:"));
		add(inputRadius);
		
		inputColor = new JColorArea(circle.getColor());
		add(new JLabel("Color: "));
		add(inputColor);
		
		setVisible(true);
	}

	@Override
	public void checkEditing() {
		x = Integer.parseInt(inputX.getText());
		y = Integer.parseInt(inputY.getText());
		radius = Integer.parseInt(inputRadius.getText());
		
		if (radius < 0) {
			throw new NumberFormatException("Radius cannot be less than 0!");
		}
	}

	@Override
	public void acceptEditing() {
		circle.setCenter(new Point(x, y));
		circle.setRadius(radius);
		circle.setColor(inputColor.getCurrentColor());
	}

}
