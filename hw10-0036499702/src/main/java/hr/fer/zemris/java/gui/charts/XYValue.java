package hr.fer.zemris.java.gui.charts;

/**
 * Class that represents value that can be added to {@link BarChart}.
 * @author Alex
 *
 */
public class XYValue {
	
	/**
	 * X component of value.
	 */
	private int x;
	
	/**
	 * Y component of value.
	 */
	private int y;
	
	/**
	 * Constructor.
	 * @param x x component
	 * @param y y component
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x.
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for y.
	 * @return y
	 */
	public int getY() {
		return y;
	}

}
