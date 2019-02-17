package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that creates page with table of sin(x) and cos(x) values for all x in given bounds.
 * It takes two arguments (in degrees):
 * 			a = lower bound
 * 			b = higher bound
 * It calculates previously mentioned values for all int values of x in interval [a, b].
 * If arguments are invalid, a is set to 0, and b is set to 360.
 * @author Alex
 *
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Integer a = null;
		Integer b = null;
		
		try {
			a = Integer.parseInt(req.getParameter("a"));
		} catch (NumberFormatException ex) {
			a = 0;
		}
		
		try {
			b = Integer.parseInt(req.getParameter("b"));
		} catch (NumberFormatException ex) {
			b = 360;
		}
		
		if(a > b) {
			int help = a;
			a = b;
			b = help;
		}
		
		if(b > a + 720) {
			b = a + 720;
		}
		
		List<Entry> entries = new ArrayList<>();
		
		for(int i = a; i <= b; i++) {
			entries.add(new Entry(i,
					Math.cos(Math.toRadians(i)),
					Math.sin(Math.toRadians(i))));
		}
		
		req.setAttribute("entries", entries);
		
		req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
	
	/**
	 * Class used to store data as an attribute of current session.
	 * It stored values of x, sin(x) and cos(x).
	 * @author Alex
	 *
	 */
	public static class Entry {
		/**
		 * x in degrees
		 */
		int number;
		
		/**
		 * sin(x)
		 */
		double sin;
		
		/**
		 * cos(x)
		 */
		double cos;
		
		/**
		 * Constructor of Entry.
		 * @param number
		 * 				x in degrees
		 * @param sin
		 * 				sin(x)
		 * @param cos
		 * 				cos(x)
		 */
		public Entry(int number, double sin, double cos) {
			super();
			this.number = number;
			this.sin = sin;
			this.cos = cos;
		}
		
		/**
		 * Gets number.
		 * @return
		 * 			x in degrees
		 */
		public int getNumber() {
			return number;
		}

		/**
		 * Gets value of sin(x).
		 * @return
		 * 			sin(x)
		 */
		public double getSin() {
			return sin;
		}
		
		/**
		 * Gets value of cos(x).
		 * @return
		 * 			cos(x)
		 */
		public double getCos() {
			return cos;
		}
		
	}

}
