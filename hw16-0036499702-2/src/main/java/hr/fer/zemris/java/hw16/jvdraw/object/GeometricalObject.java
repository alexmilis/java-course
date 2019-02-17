package hr.fer.zemris.java.hw16.jvdraw.object;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.object.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.object.visitor.GeometricalObjectVisitor;

/**
 * Abstract class that represents any geometrical object drawn by user on canvas. 
 * It has 3 implementations:
 * 			{@link Line}, {@link Circle}, {@link FilledCircle}
 * Each object has color and name. Rest of attributes depend n implementation.
 * Names of objects are auto-generated.
 * 
 * @author Alex
 *
 */
public abstract class GeometricalObject {

	/**
	 * Color of this object.
	 */
	protected Color color;
	
	/**
	 * List of this object's listeners.
	 */
	private List<GeometricalObjectListener> listeners = new LinkedList<>();
	
	/**
	 * Accepts geometrical object visitor.
	 * @param v
	 * 				{@link GeometricalObjectVisitor}
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	
	/**
	 * Creates editor of this geometrical object.
	 * @return
	 * 				editor of this geometrical object
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Notifies all listeners.
	 * @param o
	 * 				object that changed
	 */
	public void notifyListeners(GeometricalObject o) {
		listeners.forEach(l -> l.geometricalObjectChanged(o));
	}
	
	/**
	 * Adds listener to list of listeners.
	 * @param l
	 * 				{@link GeometricalObjectListener}, cannot be null
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l);
		if(!listeners.contains(l)) {
			listeners.add(l);
		}
	}
	
	/**
	 * Removes geometrical object listener from this object's list of listeners.
	 * @param l
	 * 				{@link GeometricalObjectListener} to be removed
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}

	/**
	 * Getter for object's color.
	 * @return
	 * 				color of object
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for obejct's color.
	 * @param color
	 * 				new color of object
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Gets line that is used to save this geometrical object to file.
	 * @return
	 * 				saving line of geometrical object
	 */
	public abstract String getSaveLine();
}
