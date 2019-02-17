package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Model which holds data for {@link BarChartComponent}.
 * Values of columns are stored as {@link XYValue}.
 * @author Alex
 *
 */
public class BarChart {
	
	/**
	 * Values that need to be shown in bar chart.
	 * {@link XYValue} 	x component -> value
	 * 					y component -> height of column for value x
	 */
	private List<XYValue> values;
	
	/**
	 * Description of x axis.
	 */
	private String xdesc;
	
	/**
	 * Description of y axis.
	 */
	private String ydesc;
	
	/**
	 * Minimum value of y.
	 */
	private int ymin;
	
	/**
	 * Maximum value of y.
	 */
	private int ymax;
	
	/**
	 * Spacing between values on y axis.
	 */
	private int space;

	/**
	 * Constructor.
	 * @param values {@link XYValue}
	 * @param xdesc description of x axis
	 * @param ydesc description of y axis
	 * @param ymin minimum value of y
	 * @param ymax maximum value of y
	 * @param space space between two values of y
	 */
	public BarChart(List<XYValue> values, String xdesc, String ydesc, int ymin, int ymax, int space) {
		super();
		values.sort((v1, v2) -> v1.getX() - v2.getX());
		this.values = values;
		this.xdesc = xdesc;
		this.ydesc = ydesc;
		this.ymin = ymin;
		this.space = space;
		
		if((ymax-ymin) % space == 0) {
			this.ymax = ymax;
		} else {
			this.ymax = ymin + ((ymax-ymin) / space + 1) * space;
		}
	}

	/**
	 * Getter for values.
	 * @return list of {@link XYValue}
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Getter for description of x axis.
	 * @return description as string
	 */
	public String getXdesc() {
		return xdesc;
	}

	/**
	 * Getter for description of y axis.
	 * @return description as string.
	 */
	public String getYdesc() {
		return ydesc;
	}

	/**
	 * Getter for minimim value of y.
	 * @return ymin
	 */
	public int getYmin() {
		return ymin;
	}

	/**
	 * Getter for maximum value of y.
	 * @return ymax
	 */
	public int getYmax() {
		return ymax;
	}

	/**
	 * Getter for space between y values.
	 * @return space
	 */
	public int getSpace() {
		return space;
	}
	
}
