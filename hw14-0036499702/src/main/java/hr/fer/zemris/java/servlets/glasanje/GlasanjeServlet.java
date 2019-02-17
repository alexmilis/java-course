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
 * Servlet that allows voting or bands and calls further processing of that vote.
 * All available bands are shown and voting is done by clicking on link.
 * 
 * @author Alex
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Long pollID = Long.parseLong(req.getParameter("pollID"));	
		Poll poll = DAOProvider.getDao().getPoll(pollID);
		
		if(poll == null) {
			req.setAttribute("error", "Poll with id " + pollID + " does not exist!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		req.setAttribute("poll", poll);
		
		List<PollOption> options = DAOProvider.getDao().getPollOption(pollID, false);
		
		req.setAttribute("options", options);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
