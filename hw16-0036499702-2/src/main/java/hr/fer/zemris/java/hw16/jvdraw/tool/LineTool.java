package hr.fer.zemris.java.hw16.jvdraw.tool;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.object.Line;

/**
 * Tool that specifically draws {@link Line}.
 * 
 * @author Alex
 *
 */
public class LineTool implements Tool {
	
	/**
	 * Start point.
	 */
	private Point start;
	
	/**
	 * End point.
	 */
	private Point end;
	
	/**
	 * True if start point is not null.
	 */
	private boolean started = false;
	
	/**
	 * Color provider for this tool.
	 */
	private IColorProvider colorProvider;
	
	/**
	 * Drawing model in which drawn objects are stored.
	 */
	private DrawingModel model;
	
	/**
	 * Graphics used for drawing.
	 */
	
	private JDrawingCanvas canvas;
	
	/**
	 * Constructor of LineTool.
	 * @param colorProvider
	 * @param model
	 * @param g2d
	 * @param canvas 
	 */
	public LineTool(IColorProvider colorProvider, DrawingModel model, JDrawingCanvas canvas) {
		this.colorProvider = colorProvider;
		this.model = model;
		this.canvas = canvas;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(started) {
			end = e.getPoint();
			started = false;
			model.add(new Line(start, end, colorProvider.getCurrentColor()));
		} else {
			start = e.getPoint();
			end = e.getPoint();
			started = true;
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(!started) {
			return;
		}
		canvas.repaint();
		g2d.setColor(colorProvider.getCurrentColor());
		g2d.drawLine(start.x, start.y, end.x, end.y);
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

	@Override
	public void mouseMoved(MouseEvent e){
		if(started) {
			end = e.getPoint();
			canvas.repaint();
		}		
	}

}
