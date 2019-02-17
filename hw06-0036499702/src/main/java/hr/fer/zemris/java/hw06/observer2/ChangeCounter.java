package hr.fer.zemris.java.hw06.observer2;

/**
 * Observer of class {@link IntegerStorage} that counts how many changes have been made
 * to the stored value since the registration of counter. 
 * Implements interface {@link IntegerStorageObserver}.
 * @author Alex
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Number of changes that have been made.
	 */
	private int changes;
	
	/**
	 * Constructor of ChangeCounter.
	 */
	public ChangeCounter() {
		this.changes = 0;
	}
	
	/**
	 * Method that describes action that observer performs when value is changed.
	 * Prints out number of modifications.
	 */
	@Override
	public void valueChanged(IntegerStorageChange change) {
		System.out.println("Number of value changes since tracking: " + ++changes);
	}

}
