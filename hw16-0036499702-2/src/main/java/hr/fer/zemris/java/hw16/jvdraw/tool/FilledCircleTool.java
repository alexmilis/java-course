package hr.fer.zemris.java.hw16.jvdraw.tool;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.object.FilledCircle;

/**
 * Tool used to draw {@link FilledCircle}.
 * Attributes specific for this implementation are:
 * 		radius of circle and provider for fill color
 * 
 * @author Alex
 *
 */
public class FilledCircleTool extends CircleTool {
	
	/**
	 * Provider for color of fill.
	 */
	private IColorProvider bgProvider;
	
	/**
	 * Constructor of FilledCircleTool.
	 * @param fgProvider
	 * @param model
	 * @param bgProvider
	 * @param canvas 
	 */	
	public FilledCircleTool(IColorProvider fgProvider, IColorProvider bgProvider, 
			DrawingModel model, JDrawingCanvas canvas) {
		super(fgProvider, model, canvas);
		this.bgProvider = bgProvider;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(started) {
			radius = (int) start.distance(e.getPoint());
			model.add(new FilledCircle(
					start, radius, colorProvider.getCurrentColor(), bgProvider.getCurrentColor()));
			started = false;
		} else {
			start = e.getPoint();
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
		g2d.drawOval(start.x - radius, start.y - radius, radius * 2, radius * 2);

		g2d.setColor(bgProvider.getCurrentColor());
		g2d.fillOval(start.x - radius, start.y - radius, radius * 2, radius * 2);
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
