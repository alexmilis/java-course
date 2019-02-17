package hr.fer.zemris.java.servlets;

import java.util.Calendar;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web listener used to track start time of application for page appinfo.jsp.
 * @author Alex
 *
 */
@WebListener
public class AppInfoServlet implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		arg0.getServletContext().setAttribute("start", Calendar.getInstance());
	}

}
