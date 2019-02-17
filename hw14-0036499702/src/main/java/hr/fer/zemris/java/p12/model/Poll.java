package hr.fer.zemris.java.p12.model;

/**
 * Model used to store information about a poll. It has attributes pollID, title and message.
 * @author Alex
 *
 */
public class Poll {
	
	/**
	 * Title of poll.
	 */
	private String title;
	
	/**
	 * ID of poll.
	 */
	private long pollID;
	
	/**
	 * Message of poll.
	 */
	private String message;

	/**
	 * Constructor.
	 * @param pollID
	 * @param title
	 * @param message
	 */
	public Poll(long pollID, String title, String message) {
		this.pollID = pollID;
		this.title = title;
		this.message = message;
	}

	/**
	 * Gets title of poll.
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title of poll.
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets id of poll.
	 * @return
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Sets id of poll.
	 * @param pollID
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}
	
	/**
	 * Gets message of poll.
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets message of poll.
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
