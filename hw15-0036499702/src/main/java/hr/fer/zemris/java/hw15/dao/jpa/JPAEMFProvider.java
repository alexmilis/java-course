package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Singleton class that ensures there is only one instance of {@link EntityManagerFactory}.
 * 
 * @author Alex
 *
 */
public class JPAEMFProvider {

	/**
	 * Instance of entity manager factory.
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * Getter for entity manager factory.
	 * @return
	 * 				stored entity manager factory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Setter for entity manager factory.
	 * @param emf
	 * 				entity manager factory
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}