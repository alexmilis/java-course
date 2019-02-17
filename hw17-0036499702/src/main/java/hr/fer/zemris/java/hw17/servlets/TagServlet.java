package hr.fer.zemris.java.hw17.servlets;

import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw17.util.Util;

/**
 * Servlet that is invoked during the initialization of page index.html.
 * It's only purpose is to provide real path that is used to resolve default paths in class {@link Util}.
 * 
 * @author Alex
 *
 */
@WebServlet("/tag")
public class TagServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Util.setPath(Paths.get(req.getServletContext().getRealPath("/WEB-INF")));
		System.out.println(req.getServletContext().getRealPath("/WEB-INF"));
		req.getRequestDispatcher("/rest/tag").forward(req, resp);
	}

}
