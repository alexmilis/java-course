package hr.fer.zemris.java.hw16.jvdraw.object;

/**
 * Listener of {@link GeometricalObject}.
 * 
 * @author Alex
 *
 */
@FunctionalInterface
public interface GeometricalObjectListener {

	/**
	 * Performs speficied action when {@link GeometricalObject} that was observed has changed.
	 * @param o
	 * 				{@link GeometricalObject} that changed
	 */
	 public void geometricalObjectChanged(GeometricalObject o);

}
