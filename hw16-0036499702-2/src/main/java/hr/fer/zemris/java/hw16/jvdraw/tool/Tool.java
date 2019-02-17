package hr.fer.zemris.java.hw16.jvdraw.tool;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObject;

/**
 * Interface that is implemented by each tool that is used to create some {@link GeometricalObject}.
 * 
 * @author Alex
 *
 */
public interface Tool {
	
	/**
	 * Performs specified action when mouse is pressed.
	 * @param e
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Performs specified action when mouse is released.
	 * @param e
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Performs specified action when mouse is clicked.
	 * @param e
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Performs specified action when mouse is moved.
	 * @param e
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Performs specified action when mouse is dragged.
	 * @param e
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Paints the component.
	 * @param g2d
	 * 				graphics used to paint the component
	 */
	public void paint(Graphics2D g2d);

}
