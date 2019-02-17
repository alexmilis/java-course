package hr.fer.zemris.java.hw06.observer1;

/**
 * Functional interface that needs to be implemented by observers of {@link IntegerStorage}.
 * @author Alex
 *
 */
public interface IntegerStorageObserver {
	
	/**
	 * Method that describes action that observer performs when value is changed.
	 * @param istorage integer storage that is being observed.
	 */
	public void valueChanged(IntegerStorage istorage);
	
}
