package hr.fer.zemris.java.hw05.db;

/**
 * Class that represents one expression created by {@link QueryParser}.
 * It has attributes field value getter, operator and string.
 * @author Alex
 *
 */
public class ConditionalExpression {
	
	/**
	 * Getter for field value of record.
	 */
	private IFieldValueGetter fieldValueGetter;
	
	/**
	 * String used in comparison.
	 */
	private String stringLiteral;
	
	/**
	 * Operator of comparison.
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Constructor of this class.
	 * @param getter IFieldValueGetter
	 * @param literal string
	 * @param operator comparison operator
	 */
	public ConditionalExpression(IFieldValueGetter getter, String literal, IComparisonOperator operator) {
		this.fieldValueGetter = getter;
		this.stringLiteral = literal;
		this.comparisonOperator = operator;
	}

	/**
	 * Getter for fieldValueGetter.
	 * @return fieldValueGetter
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}

	/**
	 * Getter for string literal.
	 * @return string
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Getter for comparison operator.
	 * @return comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
