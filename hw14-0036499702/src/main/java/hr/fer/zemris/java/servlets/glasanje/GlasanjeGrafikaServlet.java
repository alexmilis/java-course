package hr.fer.zemris.java.servlets.glasanje;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet that draws pie chart with results of voting. 
 * It creates dataset for pie chart from results attribute.
 * @author Alex
 *
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Long pollID = Long.parseLong(req.getParameter("id"));
		
		PieDataset data = createDataset(DAOProvider.getDao().getPollOption(pollID, true));
		JFreeChart pie = createChart(data, "Grafički prikaz rezultata");
		
		BufferedImage bim = pie.createBufferedImage(400, 400);
		
		resp.setContentType("image/png");
		
		ChartUtils.writeBufferedImageAsPNG(resp.getOutputStream(), bim);
	}
	
	/**
	 * Creates dataset for pie chart.
	 * @param list
	 * 				map of results, key = band name, value = number of votes
	 * @return
	 * 				dataset for pie chart with data from results
	 */
	private  PieDataset createDataset(List<PollOption> list) {
        DefaultPieDataset result = new DefaultPieDataset();
        list.forEach(op -> {
        	if(op.getVotes() != 0) result.setValue(op.getOptionName(), op.getVotes());	
        });
        return result;
    }
	
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
}
