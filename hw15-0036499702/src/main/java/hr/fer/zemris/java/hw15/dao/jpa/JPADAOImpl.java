package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Implementation of DAO based on JPA used by blog application to communicate with the database in which data is stored.
 * Database has 3 tables:
 * 		BLOG_USERS, BLOG_ENTRIES, BLOG_COMMENTS
 * Each of these tables is modeled by one of the classes in package hr.fer.zemris.java.hw15.models:
 * 			{@link BlogUser}, {@link BlogEntry}, {@link BlogComment}
 *  
 * @author Alex
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser getUser(String nick) throws DAOException {
		BlogUser user = (BlogUser) JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.select")
												.setParameter("n", nick).getSingleResult();
		return user;
	}

	@Override
	public List<BlogUser> getAuthors() throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogUser> authors = JPAEMProvider.getEntityManager()
			.createNativeQuery("select * from BLOG_USERS as u", BlogUser.class).getResultList();
		return authors;
	}

	@Override
	public void registerUser(BlogUser user) throws DAOException {
		JPAEMProvider.getEntityManager().persist(user);
	}

	@Override
	public void addEntry(BlogEntry entry) throws DAOException {
		JPAEMProvider.getEntityManager().persist(entry);
	}

	@Override
	public void addComment(BlogComment comment) throws DAOException {
		JPAEMProvider.getEntityManager().persist(comment);
	}

}