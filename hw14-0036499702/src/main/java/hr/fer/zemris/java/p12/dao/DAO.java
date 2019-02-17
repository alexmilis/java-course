package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {
	
	/**
	 * Gets polls that are currently in table Polls.
	 * @return
	 * 				list of {@link Poll}.
	 */
	List<Poll> getPolls();
	
	/**
	 * Gets poll that has id same as argument pollID.
	 * @param pollID
	 * 				id of the poll
	 * @return
	 * 				poll with given id if such exists, otherwise null
	 */
	Poll getPoll(Long pollID);
	
	/**
	 * Gets poll options for poll specified by pollID.
	 * @param pollID
	 * 				id of poll for which options are requested
	 * @param sortVotes
	 * 				flag - if true, sort options by number of votes
	 * @return
	 * 				list of {@link PollOption}
	 */
	List<PollOption> getPollOption(long pollID, boolean sortVotes);
	
	/**
	 * Registers vote for option with id equal to given id. Updates table pollOptions.
	 * @param id
	 * 				id of option that gets the vote
	 * @return 
	 * 				true if vote is successfull
	 * 				false if poll option with given id does not exist
	 */
	boolean vote(long id);
	
	/**
	 * Gets one or multiple winners of poll specifies by pollID.
	 * @param pollID
	 * 				id of poll
	 * @return
	 * 				list of {@link PollOption} winners	
	 */
	List<PollOption> getWinners(long pollID);

	
}