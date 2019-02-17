package hr.fer.zemris.java.hw05.db;

/**
 * Functioinal interface with method satisfied.
 * @author Alex
 *
 */
public interface IComparisonOperator {
	
	/**
	 * Determines if arguments satisfy operator.
	 * @param value1
	 * @param value2
	 * @return true if arguments satisfy
	 */
	public boolean satisfied(String value1, String value2);

}
