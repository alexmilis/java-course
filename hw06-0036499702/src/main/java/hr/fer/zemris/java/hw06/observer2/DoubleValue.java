package hr.fer.zemris.java.hw06.observer2;

/**
 * Observer of {@link IntegerStorage} that prints out double value of value stored 
 * for n times that value is changed.
 * Implements interface {@link IntegerStorageObserver}.
 * @author Alex
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Number of times double value will be printed before this observer removes itself from observer list.
	 */
	private int n;
	
	/**
	 * Number of times double value has already been printed.
	 */
	private int currentN;
	
	/**
	 * Constructor of DoubleValue.
	 * @param n number of changes double value can take.
	 */
	public DoubleValue(int n) {
		this.n = n;
		this.currentN = 0;
	}
	
	/**
	 * Method that describes action that observer performs when value is changed.
	 * Prints out double value of value currently stored in IntegerStorage.
	 */
	@Override
	public void valueChanged(IntegerStorageChange change) {
		System.out.println("Double value: " + change.getNewValue() * 2);
		currentN++;
		if(currentN == n) {
			change.getIstorage().addToRemove(this);
		}
	}

}
