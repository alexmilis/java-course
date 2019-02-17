package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * Singleton class that ensures there is only one instance of DAO in application.
 * Default implementation of DAO is {@link JPADAOImpl}.
 * 
 * @author Alex
 *
 */
public class DAOProvider {

	/**
	 * Instance of DAO.
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Gets instance of DAO stored in this class.
	 * @return
	 * 			instance of DAO
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}