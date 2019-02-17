package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

public interface DAO {

	/**
	 * Gets blog entry with id same as id given as argument.
	 * @param id
	 * 				id of {@link BlogEntry}
	 * @return
	 * 				{@link BlogEntry} if such entry exists, null otherwise
	 * @throws DAOException
	 */
	public BlogEntry getBlogEntry(long id) throws DAOException;
	
	/**
	 * Gets user that has nick same as given nick.
	 * @param nick
	 * 				user's nickname
	 * @return
	 * 				user with given nick
	 * @throws DAOException
	 */
	public BlogUser getUser(String nick) throws DAOException;

	/**
	 * Gets a list of all authors registered on the blog.
	 * @return
	 * 				list of users
	 * @throws DAOException
	 */
	public List<BlogUser> getAuthors() throws DAOException;
	
	/**
	 * Registers new user into the database.
	 * @param user
	 * 				{@link BlogUser} that needs to be added to database
	 * @throws DAOException
	 */
	public void registerUser(BlogUser user) throws DAOException;
	
	/**
	 * Adds new blog entry to database.
	 * @param entry
	 * 				{@link BlogEntry} to be added to database
	 * @throws DAOException
	 */
	public void addEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Adds new comment to database.
	 * @param comment
	 * 				{@link BlogComment} to be added to database
	 * @throws DAOException
	 */
	public void addComment(BlogComment comment) throws DAOException;
}