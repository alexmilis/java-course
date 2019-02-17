package hr.fer.zemris.java.hw15.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Class that models blog user. It has attributes:
 * 			id, first name, last name, nick, e-mail, password hash, and list of entries
 * Id is generated automatically by database.
 * All properties have getters and setters.
 * 
 * All instances of this class are stored in database in table BLOG_USERS.
 * 
 * @author Alex
 *
 */
@NamedQueries({
	@NamedQuery(name="BlogUser.select",query="select user from BlogUser as user where user.nick=:n"),
})

@Entity
@Table(name="blog_users")
@Cacheable(true)
public class BlogUser {
	
	/**
	 * Generated autoincremented value, primary key.
	 */
	private Long id;
	
	/**
	 * First name of blog user.
	 */
	private String firstName;
	
	/**
	 * Last name of blog user.
	 */
	private String lastName;
	
	/**
	 * Nick user has chosen for his blog.
	 */
	private String nick;
	
	/**
	 * User's e-mail.
	 */
	private String email;
	
	/**
	 * Hash value of user's password.
	 */
	private String passwordHash;
	
	/**
	 * List of blog entries created by this user.
	 */
	private List<BlogEntry> entries;
	
	/**
	 * Getter for entries created by this user.
	 * @return
	 * 				list of {@link BlogEntry}
	 */
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("createdAt")
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Setter for list of blog entries made by this user.
	 * @param entries
	 * 				list of {@link BlogEntry}
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	/**
	 * Generated autoincremented value, primary key.
	 * @return id
	 */
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	/**
	 * Setter for id of blog user.
	 * @param id
	 * 				Long value of id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Getter for user's first name.
	 * @return
	 * 				first name of user
	 */
	@Column(length=100, nullable=false)
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Setter for user's first name.
	 * @param firstName
	 * 				first name of blog user
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Getter for user's last name.
	 * @return
	 * 				last name of blog user
	 */
	@Column(length=100, nullable=false)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Setter for user's last name.
	 * @param lastName
	 * 				last name of blog user
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Getter for user's nick.
	 * @return
	 * 				nick of blog user
	 */
	@Column(length=100, nullable=false, unique=true)
	public String getNick() {
		return nick;
	}
	
	/**
	 * Setter for user's nick.
	 * @param nick
	 * 				nick of blog user
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Getter for user's e-mail.
	 * @return
	 * 				e-mail of blog user
	 */
	@Column(length=100, nullable=false, unique=true)
	public String getEmail() {
		return email;
	}
	
	/**
	 * Setter for user's e-mail.
	 * @param email
	 * 				e-mail of blog user
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Getter for hash value of user's password.
	 * @return
	 * 				hash value of user's password
	 */
	@Column(length=100, nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Setter for hash value of user's password.
	 * @param passwordHash
	 * 				new hash value of user's password
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BlogUser other = (BlogUser) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

}
