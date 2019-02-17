package hr.fer.zemris.java.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtils;

import hr.fer.zemris.java.pie.PieChart;

/**
 * Servlet that creates page with pie chart of OS usage.
 * @author Alex
 *
 */
@WebServlet("/reportImage")
public class PieChartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PieChart pie = new PieChart("OS usage");
		BufferedImage bim = pie.getChart().createBufferedImage(500, 270);
		
		resp.setContentType("image/png");
		ChartUtils.writeBufferedImageAsPNG(resp.getOutputStream(), bim);
	}
	

}
