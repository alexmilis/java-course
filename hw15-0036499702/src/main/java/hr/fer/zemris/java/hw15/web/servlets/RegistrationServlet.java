package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Servlet that registers new {@link BlogUser} and stores it in database. 
 * If any of attributes is missing, it returns user back to registration form.
 * 
 * @author Alex
 *
 */
@WebServlet("/servleti/registration")
public class RegistrationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String firstName = req.getParameter("fn");
		String lastName = req.getParameter("ln");
		String email = req.getParameter("email");
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");
		
		if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || nick.isEmpty() || password.isEmpty()) {
			req.setAttribute("error", "Some of requested fields were left blank, user is not registered");
			req.setAttribute("fn", firstName);
			req.setAttribute("ln", lastName);
			req.setAttribute("email", email);
			req.setAttribute("nick", nick);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}
		
		
		BlogUser user = new BlogUser();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setNick(nick);
		
		try {
			user.setPasswordHash(Util.getsha(password));
		} catch (NoSuchAlgorithmException e) {
			req.getSession().setAttribute("error", "Unable to encode given password, enter a different one");
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
		}
		
		DAOProvider.getDAO().registerUser(user);
		req.setAttribute("error", "New user successfully registered");
		
		req.getSession().setAttribute("currentId", user.getId());
		req.getSession().setAttribute("currentNick", user.getNick());
		req.getSession().setAttribute("currentFn", user.getFirstName());
		req.getSession().setAttribute("currentLn", user.getLastName());
		req.getSession().setAttribute("currentEmail", user.getEmail());
	
		req.getRequestDispatcher("main").forward(req, resp);
	}

}
