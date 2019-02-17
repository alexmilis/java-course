package hr.fer.zemris.java.servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that sets background color for all pages in application.
 * @author Alex
 *
 */
@WebServlet("/setcolor")
public class ColorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setAttribute("pickedBgCol", req.getParameter("pickedBgCol"));
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

}
