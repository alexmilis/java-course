package hr.fer.zemris.java.hw06.observer1;

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
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Provided new value: " + istorage.getValue() + ", square is: " + istorage.getValue() * istorage.getValue());
	}

}
