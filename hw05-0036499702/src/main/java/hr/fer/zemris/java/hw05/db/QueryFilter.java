package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Complex filter made of multiple {@link ConditionalExpression}.
 * @author Alex
 *
 */
public class QueryFilter implements IFilter {
	
	/**
	 * List of conditional expressions.
	 */
	private List<ConditionalExpression> list;

	/**
	 * Constructor of this class.
	 * @param list
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}

	/**
	 * Accepts student record if it satisfies all filters.
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression cond : list) {
			String recordValue = cond.getFieldValueGetter().get(record);
			if(!cond.getComparisonOperator().satisfied(recordValue, cond.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}

}
