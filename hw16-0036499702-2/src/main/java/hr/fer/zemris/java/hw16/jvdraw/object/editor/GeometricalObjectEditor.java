package hr.fer.zemris.java.hw16.jvdraw.object.editor;

import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObject;

/**
 * Editor of {@link GeometricalObject}. Used to edit points, radius and color.
 * @author Alex
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Checks if new values are valid.
	 */
	public abstract void checkEditing();
	
	/**
	 * Executed edit and stores changes.
	 */
	public abstract void acceptEditing();

}
