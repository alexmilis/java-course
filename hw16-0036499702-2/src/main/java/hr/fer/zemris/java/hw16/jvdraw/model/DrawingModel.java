package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObject;

/**
 * Model used for storing data about current shapes as {@link GeometricalObject}.
 * Has a list of listeners it has to notify each time data changes.
 * 
 * @author Alex
 *
 */
public interface DrawingModel {

	/**
	 * Gets number of shapes stored in this model.
	 * @return
	 * 				number of {@link GeometricalObject}
	 */
	public int getSize();

	/**
	 * Gets {@link GeometricalObject} stored under index index.
	 * @param index
	 * 				index under which desired object is stored
	 * @return
	 * 				object under index if such exists, null otherwise
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Stores object in model, adds object to data.
	 * @param object
	 * 				{@link GeometricalObject} to be added, cannot be null
	 */
	public void add(GeometricalObject object);
	
	/**
	 * Removes given object from this model.
	 * @param object
	 * 				object to be removed
	 */
	public void remove(GeometricalObject object);
	
	/**
	 * Stores object under index offset if possible.
	 * @param object
	 * 				object to be stored
	 * @param offset
	 * 				index under which object will be stored
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Adds listener to this model's list of listeners.
	 * @param l
	 * 				{@link DrawingModelListener} to be added, cannot be null
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes listener from this model's list of listeners.
	 * @param l
	 * 				{@link DrawingModelListener} to be removed
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
