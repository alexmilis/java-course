package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;

/**
 * Manages all requests that include author of some blog entry. 
 * Author's identity is given in request path by variable NICK.
 * If no other arguments are given, a page with all authors blog entries is rendered.
 * Each title of blog contains a link to page that displays that blog entry and its comments.
 * 
 * Other possible arguments can be new or edit. They are available only if author is logged in.
 * New creates new blog entry.
 * Edit edits one of existing entries. Which entry is edited is determined by 
 * value of id that is passed on the end of request.
 * If author in request path does not match creator of blog entry that should be edited, 
 * error page is shown.
 * 
 * @author Alex
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURL().toString();
		String args = url.substring(url.indexOf("author/") + "author/".length());
		
		int index = args.indexOf("/") == -1 ? args.length() : args.indexOf("/");
		String nick = args.substring(0, index);
		
				
		if(args.equals(nick)) {
			
			req.getSession().setAttribute("blogNick", nick);

			List<BlogEntry> entries;
			try {
				entries = DAOProvider.getDAO().getUser(nick).getEntries();
			} catch (NoResultException ex) {
				entries = null;
			}
			req.setAttribute("entries", entries);
			req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
			return;
			
			
		} else if(args.contains("new")) {
			req.setAttribute("action", "new");
			newEntry(req, resp, nick, args, false);
			return;
			
			
		} else if (args.contains("edit")) {
			newEntry(req, resp, nick, args, true);
			return;
			
			
		} else {
			Long id = Long.parseLong(args.substring(index + 1));
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
			req.setAttribute("entry", entry);
			req.getRequestDispatcher("/WEB-INF/pages/showEntry.jsp").forward(req, resp);
			return;
		}
	}

	/**
	 * Used for creating new blog entries or editing existing ones. Saves updates and changes 
	 * to database and calls appropriate servlet that renders updated page.
	 * @param req
	 * 				servlet request
	 * @param resp
	 * 				servlet response
	 * @param nick
	 * 				nick of author
	 * @param args
	 * 				arguments passed in request path
	 * @param edit
	 * 				true if action is edit, false if action is new
	 * @throws ServletException
	 * @throws IOException
	 */
	private void newEntry(HttpServletRequest req, HttpServletResponse resp, String nick, String args, boolean edit) throws ServletException, IOException {
		
		if(!nick.equals(req.getSession().getAttribute("currentNick"))) {
			req.setAttribute("error", nick + " is not currently logged in and cannot create or edit entries!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		String title = req.getParameter("title");
		String text = req.getParameter("text");
		
		if(title == null && text == null) {
			if(edit) {
				Long id = Long.parseLong(args.substring(args.lastIndexOf("/") + 1));
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
				if(!entry.getCreator().getNick().equals(nick)) {
					req.setAttribute("error", nick + " is not author of blog entry with id " + id);
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}
				req.setAttribute("title", entry.getTitle());
				req.setAttribute("text", entry.getText());
			}
			
			req.getRequestDispatcher("/WEB-INF/pages/new.jsp").forward(req, resp);
			return;
		}
		
		if(title.isEmpty() || text.isEmpty()) {
			req.setAttribute("error", "Not all fields were filled!");
			req.setAttribute("title", title);
			req.setAttribute("text", text);
			req.getRequestDispatcher("/WEB-INF/pages/new.jsp").forward(req, resp);
			return;
		}
		
		BlogEntry entry;

		if(edit) {
			Long id = Long.parseLong(args.substring(args.lastIndexOf("/") + 1));
			entry = DAOProvider.getDAO().getBlogEntry(id);
			entry.setText(text);
			entry.setTitle(title);
			entry.setLastModifiedAt(new Date());
		} else {
			entry = new BlogEntry();
			entry.setCreator(DAOProvider.getDAO().getUser(nick));
			
			Date timestamp = new Date();
			entry.setCreatedAt(timestamp);
			entry.setLastModifiedAt(timestamp);
			
			entry.setTitle(title);
			entry.setText(text);
		}
		
		DAOProvider.getDAO().addEntry(entry);
		req.setAttribute("error", "Entry successfully added!");
		if(edit) {
			resp.sendRedirect("../../" + nick);
		} else {
			resp.sendRedirect("../" + nick);
		}		
	}
}
