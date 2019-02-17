package hr.fer.zemris.java.hw05.db;

/**
 * Class that stores constant field value getters for {@link StudentRecord}.
 * All getters implement IFieldGetter.
 * @author Alex
 *
 */
public class FieldValueGetters {
	
	/**
	 * Getter for first name of student.
	 */
	public static final IFieldValueGetter FIRST_NAME = 
			record -> record.getFirstName();
			
	/**
	 * Getter for last name of student.
	 */
	public static final IFieldValueGetter LAST_NAME = 
			record -> record.getLastName();
			
	/**
	 * Getter for student's jmbag.
	 */
	public static final IFieldValueGetter JMBAG = 
			record -> record.getJmbag();

}
