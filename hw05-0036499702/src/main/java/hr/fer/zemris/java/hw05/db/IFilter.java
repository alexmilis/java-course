package hr.fer.zemris.java.hw05.db;

/**
 * Functional interface that accepts a record based on filter.
 * @author Alex
 *
 */
public interface IFilter {
	
	/**
	 * Determines if a record is accepted or not.
	 * @param record {@link StudentRecord}
	 * @return true if record is accepted.
	 */
	public boolean accepts(StudentRecord record);

}
