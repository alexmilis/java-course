package hr.fer.zemris.java.p12.model;

/**
 * Model used to store data about poll option.
 * It has atributes: 
 * 			id, optionName, pollId, link, votes
 * @author Alex
 *
 */
public class PollOption {
	
	/**
	 * Id of poll option.
	 */
	private long id;
	
	/**
	 * Name of poll option.
	 */
	private String optionName;
	
	/**
	 * ID of poll this option belong to.
	 */
	private long pollID;
	
	/**
	 * Link to representative song of this option.
	 */
	private String link;
	
	/**
	 * Number of votes this option has.
	 */
	private int votes;
	
	/**
	 * Constructor.
	 * @param id
	 * @param optionName
	 * @param pollID
	 * @param link
	 * @param votes
	 */
	public PollOption(long id, String optionName, long pollID, String link, int votes) {
		this.id = id;
		this.optionName = optionName;
		this.pollID = pollID;
		this.link = link;
		this.votes = votes;
	}

	/**
	 * Gets id of option.
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets id of option.
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets name of option.
	 * @return
	 */
	public String getOptionName() {
		return optionName;
	}

	/**
	 * Sets name of option.
	 * @param optionName
	 */
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	/**
	 * Gets id of poll.
	 * @return
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Sets pollID.
	 * @param pollID
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Gets link of this option.
	 * @return
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Sets link.
	 * @param link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Gets number of votes.
	 * @return
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * Sets number of votes.
	 * @param votes
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}
	
}
