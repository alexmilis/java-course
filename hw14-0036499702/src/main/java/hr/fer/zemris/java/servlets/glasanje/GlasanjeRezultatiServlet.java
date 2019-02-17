package hr.fer.zemris.java.servlets.glasanje;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet that processes the results of vote and prepares data for other servlets.
 * It prepares 2 lists: results and winners.
 * Results contain all bands, winners contain only bands with most votes.
 * @author Alex
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long pollID = Long.parseLong(req.getParameter("id"));
		
		Poll poll = DAOProvider.getDao().getPoll(pollID);
		
		if(poll == null) {
			req.setAttribute("error", "Poll with id " + pollID + " does not exist!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		List<PollOption> options = DAOProvider.getDao().getPollOption(pollID, true);
		List<PollOption> winners = DAOProvider.getDao().getWinners(pollID);
		
		req.setAttribute("poll", poll);
		
		req.setAttribute("results", options);
		req.setAttribute("winners", winners);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
