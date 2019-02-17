package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.object.visitor.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.object.visitor.GeometricalObjectVisitor;

/**
 * Canvas on which user can draw shapes (line, circle, filled circle).
 * 
 * @author Alex
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Model that stores all drawn shapes.
	 */
	private DrawingModel model;
	
	/**
	 * Reference to parent component {@link JVDraw} that created this canvas.
	 */
	private JVDraw draw;
	
	/**
	 * Constructor of JDrawingCanvas.
	 * @param draw
	 * 				main frame that created this canvas
	 * @param model
	 * 				drawing model that stores shapes
	 */
	public JDrawingCanvas(JVDraw draw, DrawingModel model) {
		this.draw = draw;
		this.model = model;
		model.addDrawingModelListener(this);
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		GeometricalObjectVisitor paintVisitor = new GeometricalObjectPainter(g2d);
		for(int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(paintVisitor);
		}
		if(draw.getTool() != null) {
			draw.getTool().paint(g2d);			
		}
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		GeometricalObjectVisitor paintVisitor = new GeometricalObjectPainter((Graphics2D) getGraphics());
		for (int i = index0; i <= index1; i++) {
			model.getObject(i).accept(paintVisitor);
		}	
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		GeometricalObjectVisitor paintVisitor = new GeometricalObjectPainter((Graphics2D) getGraphics());
		for (int i = index0; i <= index1; i++) {
			model.getObject(i).accept(paintVisitor);
		}
		repaint();
	}

}
