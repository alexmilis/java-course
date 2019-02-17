package hr.fer.zemris.java.hw15.web.init;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw15.dao.jpa.JPAEMFProvider;

/**
 * Class used to set parameters at the initialization and destruction of servlet context.
 * It creates and sets {@link EntityManagerFactory} in {@link JPAEMFProvider}.
 * 
 * @author Alex
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");  
		sce.getServletContext().setAttribute("my.application.emf", emf);
		JPAEMFProvider.setEmf(emf);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		EntityManagerFactory emf = (EntityManagerFactory)sce.getServletContext().getAttribute("my.application.emf");
		if(emf!=null) {
			emf.close();
		}
	}
}