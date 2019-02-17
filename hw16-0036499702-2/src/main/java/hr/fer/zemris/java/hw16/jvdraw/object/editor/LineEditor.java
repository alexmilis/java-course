package hr.fer.zemris.java.hw16.jvdraw.object.editor;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.object.Line;

/**
 * Editor used to edit {@link Line}. Can edit start point, end point and color of line.
 * @author Alex
 *
 */
public class LineEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Line line;
	
	/**
	 * X coordinate of start.
	 */
	private int startX;
	
	/**
	 * Y coordinate of start.
	 */
	private int startY;
	
	/**
	 * X coordinate of end.
	 */
	private int endX;
	
	/**
	 * Y coordinate of end.
	 */
	private int endY;
		
	/**
	 * Text field for input of start.x.
	 */
	private JTextField inputStartX;
	
	/**
	 * Text field for input of start.y.
	 */
	private JTextField inputStartY;
	
	/**
	 * Text field for input of end.x.
	 */
	private JTextField inputEndX;
	
	/**
	 * Text field for input end.y.
	 */
	private JTextField inputEndY;
	
	/**
	 * Color area for choosing color of line.
	 */
	private JColorArea inputColor;
	
	/**
	 * Constructor.
	 * @param line
	 * 				line to be edited
	 */
	public LineEditor(Line line) {
		this.line = line;
		initGUI();
	}

	private void initGUI() {
		
		setLayout(new GridLayout(8, 2));
		add(new JLabel("Edit line"));
		
		add(new JLabel());
		add(new JLabel("Start:"));
		add(new JLabel());
		
		inputStartX = new JTextField(Integer.toString(line.getStart().x));
		add(new JLabel("	x: "));
		add(inputStartX);
		
		inputStartY = new JTextField(Integer.toString(line.getStart().y));
		add(new JLabel("	y:"));
		add(inputStartY);
		
		add(new JLabel("End:"));
		add(new JLabel());
		
		inputEndX = new JTextField(Integer.toString(line.getEnd().x));
		add(new JLabel("	x: "));
		add(inputEndX);
		
		inputEndY = new JTextField(Integer.toString(line.getEnd().y));
		add(new JLabel("	y:"));
		add(inputEndY);
		
		inputColor = new JColorArea(line.getColor());
		add(new JLabel("Color: "));
		add(inputColor);
		
		setVisible(true);
	}

	@Override
	public void checkEditing() {
		startX = Integer.parseInt(inputStartX.getText());
		startY = Integer.parseInt(inputStartY.getText());
		endX = Integer.parseInt(inputEndX.getText());
		endY = Integer.parseInt(inputEndY.getText());
	}

	@Override
	public void acceptEditing() {
		line.setStart(new Point(startX, startY));
		line.setEnd(new Point(endX, endY));
		line.setColor(inputColor.getCurrentColor());
	}

}
