package hr.fer.zemris.java.hw06.observer2;

/**
 * Observer of {@link IntegerStorage} that calculates square value of stored value.
 * @author Alex
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Method that describes action that observer performs when value is changed.
	 * Prints out square value of value that is currently stored in integer storage.
	 */
	@Override
	public void valueChanged(IntegerStorageChange change) {
		System.out.println("Provided new value: " + change.getNewValue() + 
				", square is: " + change.getNewValue() * change.getNewValue());
	}

}
