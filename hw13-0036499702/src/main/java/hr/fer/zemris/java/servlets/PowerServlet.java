package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.excel.ExcelFile;

/**
 * Servlet that triggers download of Excel workbook (xls file) that contains data specified by 3 arguments:
 * 		a = lowest value
 * 		b = highest value
 * 		n = power
 * Workbook has n sheets. On each sheet with some index i is a table with 
 * int values from a to b and that same values risen to i-th power.
 * Is given arguments are invalid, error page is shown.
 * @author Alex
 *
 */
@WebServlet("/power")
public class PowerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Integer a = null;
		Integer b = null;
		Integer n = null;
		
		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));

			if(a < -100 || a > 100) {
				req.setAttribute("error", "Argument a is invalid: " + a);
				req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
			if(b < -100 || b > 100) {
				req.setAttribute("error", "Argument b is invalid: " + b);
				req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
			if(n < 1 || a > 5) {
				req.setAttribute("error", "Argument n is invalid: " + n);
				req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
		} catch (NumberFormatException ex) {
			req.setAttribute("error", "Arguments are not integers!");
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		
		ExcelFile excel = new ExcelFile(a, b, n);
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "power.xls");
		
		try {
			excel.write(resp.getOutputStream());
		} catch (IOException ex){
			req.setAttribute("error", "Unable to download excel file!");
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
		
	}
	
}
