package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Demonstrative program for drawing {@link BarChartComponent}.
 * @author Alex
 *
 */
public class BarChartDemo extends JFrame {
	
	/**
	 * Bar chart.
	 */
	private BarChartComponent chart;
	
	/**
	 * Path to file from which data is extracted.
	 */
	private String path;

	/**
	 * Constructor.
	 * @param chart bar chart
	 * @param path path to file
	 */
	public BarChartDemo(BarChartComponent chart, String path) {
		super();
		this.chart = chart;
		this.path = path;
		initGUI();
	}

	/**
	 * Initializes graphic user interface of bar chart demo.
	 * Adds path to file and bar chart to frame.
	 */
	private void initGUI() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("BarChartDemo");
		setLayout(new BorderLayout());
		
		add(new JLabel(path, SwingConstants.CENTER), BorderLayout.PAGE_START);
		add(chart);
		
		setSize(700, 500);
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Main method. Example file from instructions is in directory of this project,
	 * so program can easily be run with argument "./barchart".
	 * @param args command line argument - path to file
	 */
	public static void main(String[] args) {
		Path p = Paths.get(args[0]);
		
		BarChartComponent chart;
				
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(Files.newInputStream(p))));
			
			String xdesc = br.readLine();
			String ydesc = br.readLine();
			String[] data = br.readLine().split(" ");
			int ymin = Integer.parseInt(br.readLine());
			int ymax = Integer.parseInt(br.readLine());
			int space = Integer.parseInt(br.readLine());
			
			List<XYValue> values = new ArrayList<>();
			
			for(String s : data) {
				String[] xy = s.split(",");
				values.add(new XYValue(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
			}
			
			chart = new BarChartComponent(new BarChart(values, xdesc, ydesc, ymin, ymax, space));
			
		} catch (IOException|NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid file for initializing bar chart:" + p.toString(), 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(chart, p.toString()).setVisible(true);
		});

	}

}
