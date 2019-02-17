package hr.fer.zemris.java.hw15.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class that models blog comment. Each comment has properties:
 * 			id, blog entry, user's email, message, and time it was posted
 * Id is generated automatically by database.
 * All properties have getters and setters.
 * In database, all instances of this class are stored in table BLOG_COMMENTS.
 * 
 * @author Alex
 *
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**
	 * Generated autoincremented value, primary key.
	 */
	private Long id;
	
	/**
	 * Blog entry to which comment is added.
	 */
	private BlogEntry blogEntry;
	
	/**
	 * E-mail of user who wrote the comment.
	 */
	private String usersEMail;
	
	/**
	 * Message of comment.
	 */
	private String message;
	
	/**
	 * Time and date when comment was posted.
	 */
	private Date postedOn;
	
	/**
	 * Getter for id.
	 * @return
	 * 				id of comment
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Setter for id
	 * @param id
	 * 				Long value of id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for blog entry.
	 * @return
	 * 				blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Setter for blog entry.
	 * @param blogEntry
	 * 				{@link BlogEntry} to which this comment is added
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter for user's e-mail.
	 * @return
	 * 				user's e-mail
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter for user's e-mail.
	 * @param usersEMail
	 * 				e-mail of user who wrote the comment
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter for message.
	 * @return
	 * 				message of comment
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for message.
	 * @param message
	 * 				message of this comment
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for time and date comment was posted on.
	 * @return
	 * 				time and date of posting
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Setter for time and date of posting.
	 * @param postedOn
	 * 				time and date of posting
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}