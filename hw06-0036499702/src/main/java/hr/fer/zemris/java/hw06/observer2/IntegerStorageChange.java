package hr.fer.zemris.java.hw06.observer2;

/**
 * Class that describes change of value stored in {@link IntegerStorage}. Contains old and new value.
 * @author Alex
 *
 */
public class IntegerStorageChange {
	
	/**
	 * {@link IntegerStorage} that has been changed.
	 */
	private IntegerStorage istorage;
	
	/**
	 * Value previously stored.
	 */
	private int oldValue;
	
	/**
	 * Value currently stored.
	 */
	private int newValue;
	
	/**
	 * Constructor of IntegerStorageChange.
	 * @param istorage integer storage.
	 * @param oldValue previously stored.
	 * @param newValue currently stored.
	 */
	public IntegerStorageChange(IntegerStorage istorage, int oldValue, int newValue) {
		super();
		this.istorage = istorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Getter for integer storage.
	 * @return
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * Getter for old value.
	 * @return
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * Getter for new value.
	 * @return
	 */
	public int getNewValue() {
		return newValue;
	}

}
