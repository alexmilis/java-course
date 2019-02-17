package hr.fer.zemris.java.hw16.jvdraw.object.editor;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.object.FilledCircle;

/**
 * Editor of {@link FilledCircle}. Can edit center point, radius, outline color and fill color.
 * @author Alex
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Filled circle that is being edited.
	 */
	private FilledCircle filledCircle;
	
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
	 * Color area for choosing new outline color of filled circle.
	 */
	private JColorArea inputColor;
	
	/**
	 * Color are for choosing new fill color of filled circle.
	 */
	private JColorArea inputBgColor;
	
	/**
	 * Constructor.
	 * @param filledCircle
	 * 				filled circle to be edited
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout(8, 2));
		add(new JLabel("Edit filled circle"));
		
		add(new JLabel());
		add(new JLabel("Center:"));
		add(new JLabel());
		
		inputX = new JTextField(Integer.toString(filledCircle.getCenter().x));
		add(new JLabel("	x: "));
		add(inputX);
		
		inputY = new JTextField(Integer.toString(filledCircle.getCenter().y));
		add(new JLabel("	y:"));
		add(inputY);
		
		inputRadius = new JTextField(Integer.toString(filledCircle.getRadius()));
		add(new JLabel("Radius:"));
		add(inputRadius);
		
		inputColor = new JColorArea(filledCircle.getColor());
		add(new JLabel("Color: "));
		add(inputColor);
		
		inputBgColor = new JColorArea(filledCircle.getBgColor());
		add(new JLabel("Background color: "));
		add(inputBgColor);
		
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
		filledCircle.setCenter(new Point(x, y));
		filledCircle.setRadius(radius);
		filledCircle.setColor(inputColor.getCurrentColor());
		filledCircle.setBgColor(inputBgColor.getCurrentColor());
	}

}
