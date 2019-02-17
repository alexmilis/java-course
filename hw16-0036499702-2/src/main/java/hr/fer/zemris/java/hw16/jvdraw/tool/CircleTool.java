package hr.fer.zemris.java.hw16.jvdraw.tool;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.object.Circle;

/**
 * Tool used to draw {@link Circle}.
 * Attribute specific for this implementation is radius of circle.
 * 
 * @author Alex
 *
 */
public class CircleTool implements Tool {
	
	/**
	 * Start point.
	 */
	protected Point start;
	
	/**
	 * True if start point is not null.
	 */
	protected boolean started = false;
	
	/**
	 * Color provider for this tool.
	 */
	protected IColorProvider colorProvider;
	
	/**
	 * Drawing model in which drawn objects are stored.
	 */
	protected DrawingModel model;
	
	/**
	 * Canvas on which circle is drawn.
	 */
	protected JDrawingCanvas canvas;
	
	/**
	 * Radius of circle.
	 */
	protected int radius;
	
	/**
	 * Constructor of CircleTool.
	 * @param colorProvider
	 * @param model
	 * @param canvas 
	 */
	public CircleTool(IColorProvider colorProvider, DrawingModel model, JDrawingCanvas canvas) {
		this.colorProvider = colorProvider;
		this.model = model;
		this.canvas = canvas;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(started) {
			radius = (int) start.distance(e.getPoint());
			model.add(new Circle(start, radius, colorProvider.getCurrentColor()));
			started = false;
		} else {
			start = e.getPoint();
			started = true;
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(started) {
			radius = (int) start.distance(e.getPoint());
			canvas.repaint();
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(!started) {
			return;
		}
		canvas.repaint();
		g2d.setColor(colorProvider.getCurrentColor());
		g2d.drawOval(start.x - radius, start.y - radius, radius * 2, radius * 2);

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

}
