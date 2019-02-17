package hr.fer.zemris.java.hw05.db;

/**
 * Class that stores operator constants. All constants implement {@link IComparisonOperator} 
 *  and implement inherited method satisfied(value1, value2).
 * @author Alex
 *
 */
public class ComparisonOperators {
	
	/**
	 * Returns true if value1 is less than value2. Symbol is <.
	 */
	public static final IComparisonOperator LESS =
			(value1, value2) -> value1.compareTo(value2) < 0;
	
	/**
	 * Returns true if value1 is less or equal to value2. Symbol is <=.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = 
			(value1, value2) -> value1.compareTo(value2) <= 0;
			
	/**
	 * Returns true if value1 is greater than value2. Symbol is >.
	 */
	public static final IComparisonOperator GREATER = 
			(value1, value2) -> value1.compareTo(value2) > 0;
		
	/**
	 * Returns true if value1 is greater or equal to value2. Symbol is >=.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS =
			(value1, value2) -> value1.compareTo(value2) >= 0;
	
	/**
	 * Returns true if value1 equals value2. Symbol is =.
	 */
	public static final IComparisonOperator EQUALS = 
			(value1, value2) -> value1.compareTo(value2) == 0;
			
	/**
	 * Returns true if value1 is not equal to value2. Symbol is !=.
	 */
	public static final IComparisonOperator NOT_EQUALS = 
			(value1, value2) -> value1.compareTo(value2) != 0;
	
	/**
	 * Returns true if value1 can written using value2 and wildcard (*). Symbol is LIKE.
	 */
	public static final IComparisonOperator LIKE = 
			(value1, value2) -> {
				if(value2.indexOf('*') != value2.lastIndexOf('*')) {
					throw new IllegalArgumentException("String literal with more than one '*' is not supported: " + value2);
				}
				
				int index = value2.indexOf('*');
				if(index == -1) {
					return value1.equals(value2);
				}
				
				if(index == 0) {
					return value1.endsWith(value2.substring(1));
				}
				
				if(index == value2.length() - 1) {
					return value1.startsWith(value2.substring(0, value2.length() - 1));
				}
				
				return value1.startsWith(value2.substring(0, index)) && value1.endsWith(value2.substring(index + 1));
			};

}
