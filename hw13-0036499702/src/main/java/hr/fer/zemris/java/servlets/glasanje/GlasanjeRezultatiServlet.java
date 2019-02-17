package hr.fer.zemris.java.servlets.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that processes the results of vote and prepares data for other servlets.
 * It prepares 2 maps:
 * 			results - key = band name, value = number of votes
 * 			winners - key = band name, value = link to their song
 * Results contain all bands, winners contain only bands with most votes.
 * @author Alex
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String fileNameLinks = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<String> links = Files.readAllLines(Paths.get(fileNameLinks));
		
		Map<String, Integer> results = new HashMap<>();
		Map<String, String> idToName = new HashMap<>();
		
		for(String line : links) {
			String[] parts = line.split("\t");
			idToName.put(parts[0], parts[1]);
		}
		
		int max = 0;

		for(String line : lines) {
			String[] parts = line.split("\t");
			int votes = Integer.parseInt(parts[1]);
			if(votes > max) {
				max = votes;
			}
			results.put(idToName.get(parts[0]), votes);
		}
		
		Map<String, String> winners = new HashMap<>();
		
		for(String line : lines) {
			String[] parts = line.split("\t");
			if(Integer.parseInt(parts[1]) == max) {
				int index = getLineOfId(parts[0], links);
				if(index == -1) {
					req.setAttribute("error", "Invalid band id is requested: " + parts[0]);
					req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}
				String[] partsLinks = links.get(index).split("\t");
				winners.put(partsLinks[1], partsLinks[2]);
			}
		}

		req.getSession().setAttribute("results", sort(results));
		req.getSession().setAttribute("winners", winners);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * Sorts map of results by value (number of votes).
	 * @param results
	 * 				results of vote
	 * @return
	 * 				map of results sorted by number of votes, highest to lowest
	 */
	private Map<String, Integer> sort(Map<String, Integer> results) {
		Map<String, Integer> res = new LinkedHashMap<>();
		
		List<Map.Entry<String, Integer>> entries = new ArrayList<>(results.entrySet());
		entries.sort((a, b) -> a.getValue() < b.getValue() ? 1 : -1);
		
		for(Map.Entry<String, Integer> entry : entries) {
			res.put(entry.getKey(), entry.getValue());
		}
		
		return res;
	}
	
	/**
	 * Finds index of line that starts with given band id.
	 * @param id
	 * 				id of band
	 * @param links
	 * 				list of strings in which index should be found
	 * @return
	 * 				index of id if id exists in that list, -1 otherwise
	 */
	private int getLineOfId(String id, List<String> links) {
		for(int i = 0; i < links.size(); i++) {
			if(links.get(i).startsWith(id)) {
				return i;
			}
		}
		return -1;
	}
	
	
}
