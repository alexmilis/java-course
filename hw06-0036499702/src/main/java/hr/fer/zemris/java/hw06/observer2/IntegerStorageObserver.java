package hr.fer.zemris.java.hw06.observer2;

/**
 * Functional interface that needs to be implemented by observers of {@link IntegerStorage}.
 * @author Alex
 *
 */
public interface IntegerStorageObserver {
	
	/**
	 * Method that describes action that observer performs when value is changed.
	 * @param change {@link IntegerStorageChange} that describes change that happened.
	 */
	public void valueChanged(IntegerStorageChange change);
	
}
