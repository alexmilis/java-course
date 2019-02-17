package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Servlet used for login. It requires of user to input valid nick and password. 
 * If that condition is not satisfied, it shows appropriate message on main page.
 * After successful login, it sets session attributes for current user.
 * 
 * @author Alex
 *
 */
@WebServlet("/servleti/login")
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");
		
		if(nick.isEmpty() && password.isEmpty()) {
			req.setAttribute("error", "Nick and password are null!");
		}
		
		if(nick.isEmpty()) {
			req.setAttribute("error", "Nick is null!");
		}
		
		if(password.isEmpty()) {
			req.setAttribute("error", "Password is null!");
		}
		
		if(password.isEmpty() || nick.isEmpty()) {
			req.getRequestDispatcher("main").forward(req, resp);
		}
				
		try {
			BlogUser user = DAOProvider.getDAO().getUser(nick);
			if(Util.getsha(password).equals(user.getPasswordHash())) {
				req.getSession().setAttribute("currentId", user.getId());
				req.getSession().setAttribute("currentNick", user.getNick());
				req.getSession().setAttribute("currentFn", user.getFirstName());
				req.getSession().setAttribute("currentLn", user.getLastName());
				req.getSession().setAttribute("currentEmail", user.getEmail());
			} else {
				req.setAttribute("error", "Incorrect password!");
				req.setAttribute("nick", nick);
			}
		} catch (NoSuchAlgorithmException|NoResultException e) {
			req.setAttribute("error", "Incorrect password!");
			req.setAttribute("nick", nick);
		}
		
		req.getRequestDispatcher("main").forward(req, resp);
	}
	

}
