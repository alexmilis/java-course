package hr.fer.zemris.java.hw05.db;
/**
 * Functional interface that consists of method that gets an attribute from {@link StudentRecord}.
 * @author Alex
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * Gets specified attribute from {@link StudentRecord}.
	 * @param record
	 * @return
	 */
	public String get(StudentRecord record);

}
