package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Component that draws {@link BarChart}.
 * @author Alex
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Model of bar chart.
	 */
	private BarChart model;
	
	/**
	 * Size of predefined space between components on the graph.
	 * ex. space between the bottom of this component and description of x axis.
	 */
	private final int delta = 15; 

	/**
	 * X coordinate of left lower corned of chart.
	 */
	private int xzero;
	
	/**
	 * Y coordinate of left lower corned of chart.
	 */
	private int yzero;
	
	/**
	 * Gap between y values of chart in pixels.
	 */
	private int ygap;
	
	/**
	 * Gap between x values of chart in pixels.
	 */
	private int xgap;
	
	/**
	 * Height of font used in this chart.
	 */
	private int fontHeight;
	
	/**
	 * Width of one character.
	 */
	private int fontWidth;
	
	/**
	 * Default font name.
	 */
	private String defaultFontName = "Arial";
	
	/**
	 * Default font size.
	 */
	private int defaultFontSize = 12;
	
	public BarChartComponent(BarChart model) {
		super();
		this.model = model;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		setBackground(Color.WHITE);
		
		yzero = getHeight() - 3*delta - 2*fontHeight;
		
		int ymaxw = Integer.toString(model.getYmax()).length();
		xzero = 3*delta + fontHeight + ymaxw*fontWidth;
		
		int yNumber = (model.getYmax() - model.getYmin()) / model.getSpace();
		ygap = (yzero - delta) / yNumber;
		
		int xNumber = model.getValues().size();
		xgap = (getWidth() - delta - xzero) / xNumber;
		
		g.setFont(new Font(defaultFontName, Font.PLAIN, defaultFontSize));
		FontMetrics fm = g.getFontMetrics(g.getFont());
		fontHeight = fm.getHeight();
		fontWidth = fm.charWidth('0');
		
		g.setColor(Color.BLACK);
		int xdescx = (getWidth() - fm.stringWidth(model.getXdesc())) / 2;
		g.drawString(model.getXdesc(), xdescx, getHeight() - delta);
		
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(new Font(defaultFontName, Font.PLAIN, defaultFontSize * 2));
		AffineTransform defaultAt = g2d.getTransform();
		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		g2d.setTransform(at);
		
		int ydescy = (getHeight() - g2d.getFontMetrics(g2d.getFont()).stringWidth(model.getYdesc())) / 2;
		g2d.drawString(model.getYdesc(), -ydescy * 2, delta * 2);
				
		g2d.setTransform(defaultAt);
		
		
		drawXPointer(g);
		drawXLines(g, yNumber);
		
		drawColumns(g);
		
		drawYPointer(g);
		drawYLines(g, xNumber);
	}
	
	/**
	 * Draws lines parallel with x axis and writes values of y next to them.
	 * @param g graphics
	 * @param yNumber number of y values
	 */
	private void drawXLines(Graphics g, int yNumber) {
		g.setFont(new Font(defaultFontName, Font.BOLD, defaultFontSize));
		for(int i = 0; i <= yNumber; i++) {
			int y = yzero - i*ygap;
			g.setColor(Color.GRAY);
			g.drawLine(xzero - 5, y, xzero, y);
			
			g.setColor(i == 0 ? Color.GRAY : Color.ORANGE);
			g.drawLine(xzero, y, getWidth()-delta, y);
			
			String text = Integer.toString(model.getYmin() + i*model.getSpace());
			int x = xzero - delta - text.length()*fontWidth;
			
			g.setColor(Color.BLACK);
			g.drawString(text, x, y);
		}
	}
	
	/**
	 * Draws lines parallel with y axis.
	 * @param g graphics
	 * @param xNumber number of x values
	 */
	private void drawYLines(Graphics g, int xNumber) {
		g.setFont(new Font(defaultFontName, Font.BOLD, defaultFontSize));
		for(int i = 0; i < xNumber; i++) {
			int x = xzero + i*xgap;
			
			g.setColor(Color.GRAY);
			g.drawLine(x, yzero + 5, x, yzero);
			
			g.setColor(i == 0 ? Color.GRAY : Color.ORANGE);
			g.drawLine(x, yzero, x, delta);
		}
	}
	
	/**
	 * Draws columns and x values from model.
	 * @param g graphics
	 */
	private void drawColumns(Graphics g) {
		int counter = 0;
		for(XYValue value : model.getValues()) {
			g.setColor(Color.RED);
			int h = value.getY() / model.getSpace() * ygap;
			g.fillRect(xzero + counter*xgap, yzero - h, xgap, h);
			
			String text = Integer.toString(value.getX());
			int y = yzero + delta;
			int x = xzero + (int)(0.5 * xgap) + counter * xgap;
			
			g.setColor(Color.BLACK);
			g.drawString(text, x, y);
			
			counter++;
		}
	}
	
	/**
	 * Draws pointer on the end of x axis.
	 * @param g
	 */
	private void drawXPointer(Graphics g) {
		int[] xPoints = {getWidth() - delta, 
						 getWidth() - delta, 
						 getWidth() - delta + 5};

		int[] yPoints = {yzero - 5, 
						 yzero + 5, 
						 yzero};

		g.fillPolygon(xPoints, yPoints, 3);
	}
	
	/**
	 * Draws pointer on the end of y axis.
	 * @param g
	 */
	private void drawYPointer(Graphics g) {
		int[] xPoints = {xzero - 5, 
						 xzero + 5, 
						 xzero};

		int[] yPoints = {delta, 
						 delta, 
						 delta - 5};
		
		g.fillPolygon(xPoints, yPoints, 3);		
	}
}
