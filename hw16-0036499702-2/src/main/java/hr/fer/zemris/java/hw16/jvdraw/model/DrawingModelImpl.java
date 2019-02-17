package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObjectListener;

/**
 * Implementation of {@link DrawingModel} used in application {@link JVDraw}.
 * It stores {@link GeometricalObject} drawn by user.
 * It is also registered as {@link GeometricalObjectListener} of all object stored in it,
 * so it call call application to update if any object changes.
 * 
 * @author Alex
 *
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener {

	/**
	 * List of stored objects.
	 */
	private List<GeometricalObject> objects = new ArrayList<>();
	
	/**
	 * List of listeners.
	 */
	private List<DrawingModelListener> listeners = new LinkedList<>();
	
	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}
	
	@Override
	public void remove(GeometricalObject object) {
		if (object == null) {
			return;
		}

		int index = objects.indexOf(object);
		objects.remove(object);
		listeners.forEach(l -> l.objectsRemoved(this, index, index));
	}
	
	@Override
	public int getSize() {
		return objects.size();
	}
	
	@Override
	public GeometricalObject getObject(int index) {
		if(index < 0 || index > objects.size() - 1) {
			return null;
		}
		return objects.get(index);
	}
	
	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		if(!objects.contains(object)) {
			throw new IllegalArgumentException("Given object is not in this model!");
		}
		
		int index = objects.indexOf(object);
		objects.remove(object);
		
		int newIndex = index + offset;
		
		if (newIndex < 0) {
			newIndex = objects.size() + 1 + newIndex;
		} else if (newIndex >= objects.size() + 1) {
			newIndex %= (objects.size() + 1);
		}
		
		objects.add(newIndex, object);
		
		int a = newIndex;
		listeners.forEach(l -> l.objectsChanged(this, index, a));
	}
	
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l);
		listeners.add(l);
	}
	
	@Override
	public void add(GeometricalObject object) {
		Objects.requireNonNull(object);
		objects.add(object);
		object.addGeometricalObjectListener(this);
		listeners.forEach(l -> l.objectsAdded(this, objects.size() - 1, objects.size() - 1));
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		changeOrder(o, objects.indexOf(o));
	}

}
