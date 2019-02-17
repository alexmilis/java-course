package hr.fer.zemris.java.servlets.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles voting. It takes one argument:
 * 			id = id of band that gets the vote
 * If file with results doesn't exist, it creates it and fills it with zeros.
 * If file already exists, it just adds 1 to number of existing votes for band specified by id.
 * @author Alex
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int noOfBands = 10;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		int id = Integer.parseInt(req.getParameter("id"));
		
		vote(fileName, id);
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");

		
	}

	/**
	 * Method that creates file with results if it doesn't exist and adds vote to vote count.
	 * @param filename
	 * 				name of file in which results are stored
	 * @param id
	 * 				id of band that received the vote
	 * @throws IOException
	 */
	private synchronized void vote(String filename, int id) throws IOException {
		Path path = Paths.get(filename);
		if(!Files.isReadable(path)) {
			StringBuilder sb = new StringBuilder();
			for(int i = 1; i <= noOfBands; i++) {
				sb.append(i).append("\t").append(0).append("\n");
			}
			Files.newOutputStream(path, StandardOpenOption.CREATE).write(sb.toString().getBytes());
		}
		
		List<String> lines = Files.readAllLines(path);
		
		String[] parts = lines.get(id - 1).split("\t");
		
		int votes = Integer.parseInt(parts[1]) + 1;
		
		lines.remove(id - 1);
		lines.add(id - 1, parts[0] + "\t" + Integer.toString(votes));
		
		Files.write(path, lines, StandardOpenOption.WRITE);
		
	}
}
