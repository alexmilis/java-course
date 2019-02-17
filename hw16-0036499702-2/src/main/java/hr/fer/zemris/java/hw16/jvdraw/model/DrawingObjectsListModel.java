package hr.fer.zemris.java.hw16.jvdraw.model;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObject;

/**
 * Model used for creating {@link JList} in {@link JVDraw} that shows all current {@link GeometricalObject}.
 * It is implemented as object adapter of {@link DrawingModel}.
 * 
 * @author Alex
 *
 */
public class DrawingObjectsListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Drawing model used to store geometrical objects.
	 */
	private DrawingModel model;	
	
	/**
	 * Constructor of DrawingObjectsListModel.
	 * @param model
	 * 				drawing model used to store data
	 */
	public DrawingObjectsListModel(DrawingModel model) {
		super();
		this.model = model;
		model.addDrawingModelListener(this);
		
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		for(ListDataListener l : getListDataListeners()) {
			l.intervalAdded(new ListDataEvent(source, 1, index0, index1));
		}
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		for(ListDataListener l : getListDataListeners()) {
			l.intervalRemoved(new ListDataEvent(source, 2, index0, index1));
		}
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {		
		for(ListDataListener l : getListDataListeners()) {
			l.contentsChanged(new ListDataEvent(source, 0, index0, index1));
		}
	}

}
