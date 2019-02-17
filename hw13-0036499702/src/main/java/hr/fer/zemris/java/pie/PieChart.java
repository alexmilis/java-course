package hr.fer.zemris.java.pie;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Represents pie chart. Can be initialized only with name or with name and dataset. 
 * If no dataset is given in constructor, it uses sample dataset.
 * @author Alex
 *
 */
public class PieChart {
	
	/**
	 * Actual chart.
	 */
	private JFreeChart chart;

	/**
	 * Constructor of PieChart that uses sample dataset.
	 * @param chartTitle
	 * 				name of pie chart
	 */
    public PieChart(String chartTitle) {
        PieDataset dataset = createDataset();
        chart = createChart(dataset, chartTitle);
    }
    
    /**
     * Constructor of PieChart.
     * @param chartTitle
     * 				name of pie chart
     * @param dataset
     * 				dataset of pie chart
     */
    public PieChart(String chartTitle, PieDataset dataset) {
        chart = createChart(dataset, chartTitle);
    }

    /**
     * Creates a sample dataset.
     */
    private  PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Linux", 29);
        result.setValue("Mac", 20);
        result.setValue("Windows", 51);
        return result;
    }
    
    /**
     * Creates chart with given data.
     * @param dataset
     * 				dataset of pie chart
     * @param title
     * 				title of pie chart
     * @return
     * 				chart
     */
    private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(
            title,                  // chart title
            dataset,                // data
            true,                   // include legend
            true,
            false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;

    }

    /**
     * Getter for chart.
     * @return
     * 			chart
     */
	public JFreeChart getChart() {
		return chart;
	}
	
}
