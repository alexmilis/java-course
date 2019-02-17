package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * Listener of {@link DrawingModel}. It offers 3 methods for tracking the work of drawing model:
 * 		objectsAdded, objectsRemoved, objectsChanged
 * 
 * All methods have same arguments:
 * 			- source - source of change
 * 			- index0 - start index of geometrical objects from model's list that changed
 * 			- index1 - end index of geometrical objects from model's list that changed
 * Bounds index0 and index1 are inclusive.
 * 
 * @author Alex
 *
 */
public interface DrawingModelListener {

	/**
	 * Performs specified action when objects have been added to drawing model.
	 * @param source
	 * 				model to which objects were added
	 * @param index0
	 * 				start index of added objects
	 * @param index1
	 * 				end index of added objects
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Performs specified action when objects have been removed from drawing model.
	 * @param source
	 * 				model from which objects were removed
	 * @param index0
	 * 				start index of removed objects
	 * @param index1
	 * 				end index of removed objects
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Performs specified action when objects from drawing model have been changed.
	 * @param source
	 * 				model from which objects were changed
	 * @param index0
	 * 				start index of changed objects
	 * @param index1
	 * 				end index of changed objects
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);

}
