package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;

/**
 * Servlet used for adding {@link BlogComment} to {@link BlogEntry}.
 * Each comment requires user'e e-mail and some message.
 * If user is already logged in, his e-mail is automatically filled.
 * After database update, it calls rendering of updated page.
 * 
 * @author Alex
 *
 */
@WebServlet("/servleti/comment")
public class CommentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String usersemail = req.getParameter("usersemail");
		String comment = req.getParameter("comment");
		
		if(usersemail.isEmpty() || comment.isEmpty()) {
			req.setAttribute("error", "There are empty fields left!");
			req.setAttribute("usersemail", usersemail);
			req.setAttribute("comment", comment);
			Long id = Long.parseLong(req.getParameter("entry"));

			resp.sendRedirect("author/" + req.getSession().getAttribute("blogNick") + "/" + id);
			return;
		}
		
		BlogComment blogComment = new BlogComment();
		
		blogComment.setUsersEMail(usersemail);
		blogComment.setMessage(comment);
		blogComment.setPostedOn(new Date());
		
		Long id = Long.parseLong(req.getParameter("entry"));
		blogComment.setBlogEntry(DAOProvider.getDAO().getBlogEntry(id));
	
		DAOProvider.getDAO().addComment(blogComment);
		resp.sendRedirect("author/" + req.getSession().getAttribute("blogNick") + "/" + id);
	}

}
