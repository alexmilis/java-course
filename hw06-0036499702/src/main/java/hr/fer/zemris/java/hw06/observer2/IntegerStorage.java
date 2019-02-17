package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * Storage for integer value. Has a list of {@link IntegerStorageObserver} 
 * that needs to be notified every time value changes.
 * @author Alex
 *
 */
public class IntegerStorage {
	
	/**
	 * Stored value.
	 */
	private int value;
	
	/**
	 * List of observers that observe this storage.
	 */
	private List<IntegerStorageObserver> observers;
	
	/**
	 * List of observers that need to be removed.
	 */
	private List<IntegerStorageObserver> toRemove;
	
	/**
	 * Constructor of IntegerStorage.
	 * @param initialValue first value to be stored in storage.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new ArrayList<>();
		this.toRemove = new ArrayList<>();
	}
	
	/**
	 * Adds observer to observer list.
	 * @param observer to be added.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if(!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Removes observer from observer list.
	 * @param observer to be removed.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if(observers.contains(observer)) {
			observers.remove(observer);
		}
	}

	/**
	 * Removes all observers from observer list.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Getter for stored value.
	 * @return
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Adds observer to the list of observers that need to be removed.
	 * @param observer
	 */
	public void addToRemove(IntegerStorageObserver observer) {
		this.toRemove.add(observer);
	}

	/**
	 * Setter for stored value. Notifies all observers if value is changed.
	 * @param value to be stored.
	 */
	public void setValue(int value) {
		if (this.value != value) {
			IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);
			this.value = value;
			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(change);
				}
				for(IntegerStorageObserver observer : toRemove) {
					observers.remove(observer);
				}
			}
		}
	}
}
