package hr.fer.zemris.java.servlets.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that allows voting or bands and calls further processing of that vote.
 * All available bands are shown and voting is done by clicking on link.
 * 
 * @author Alex
 *
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		
		Map<String, String> bands = new LinkedHashMap<>();
		
		lines.forEach(l -> {
			String[] parts = l.split("\t");
			bands.put(parts[0], parts[1]);
		});
		
		req.getSession().setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
