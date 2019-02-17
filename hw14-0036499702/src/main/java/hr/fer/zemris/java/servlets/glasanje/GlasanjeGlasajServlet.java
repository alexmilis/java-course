package hr.fer.zemris.java.servlets.glasanje;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Servlet that handles voting. It takes one argument:
 * 			id = id of band that gets the vote
 * If file with results doesn't exist, it creates it and fills it with zeros.
 * If file already exists, it just adds 1 to number of existing votes for band specified by id.
 * @author Alex
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long id = Long.parseLong(req.getParameter("id"));
		
		if(!DAOProvider.getDao().vote(id)) {
			req.setAttribute("error", "Poll option with id " + id + " does not exist!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?id=" + req.getParameter("pollID"));
	}

}
