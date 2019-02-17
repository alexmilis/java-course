package hr.fer.zemris.java.servlets.glasanje;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtils;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.pie.PieChart;

/**
 * Servlet that draws pie chart with results of voting. 
 * It creates dataset for pie chart from results attribute.
 * @author Alex
 *
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		@SuppressWarnings("unchecked")
		PieDataset data = createDataset((Map<String, Integer>) req.getSession().getAttribute("results"));
		PieChart pie = new PieChart("Grafički prikaz rezultata", data);
		
		BufferedImage bim = pie.getChart().createBufferedImage(400, 400);
		
		resp.setContentType("image/png");
		
		ChartUtils.writeBufferedImageAsPNG(resp.getOutputStream(), bim);
	}
	
	/**
	 * Creates dataset for pie chart.
	 * @param map
	 * 				map of results, key = band name, value = number of votes
	 * @return
	 * 				dataset for pie chart with data from results
	 */
	private  PieDataset createDataset(Map<String, Integer> map) {
        DefaultPieDataset result = new DefaultPieDataset();
        
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
        	result.setValue(entry.getKey(), entry.getValue());
        }
        
        return result;
    }
}
