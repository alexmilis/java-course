package hr.fer.zemris.java.gui.layouts;

/**
 * Class RCPosition is used as constraint of components in {@link CalcLayout}.
 * It stores two read-only values: row and column.
 * Values of row and column are integers bigger than 0.
 * @author Alex
 *
 */
public class RCPosition {
	
	/**
	 * Index of row.
	 */
	private int row;
	
	/**
	 * Index of column.
	 */
	private int column;
	
	/**
	 * Constuctor of RCPosition.
	 * @param row index of row
	 * @param column index of column.
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns value that is stored as index of row.
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns value that is stored as index of column.
	 * @return column
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RCPosition other = (RCPosition) obj;
		if (column != other.column) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "(" + row + ", " + column + ")";
	}

}
