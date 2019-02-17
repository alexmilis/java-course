package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter 
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *  
 * @author marcupic
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPolls() {
		List<Poll> polls = new ArrayList<>();
		
		Connection con = SQLConnectionProvider.getConnection();
		
		try {
			PreparedStatement pst = con.prepareStatement("SELECT id, title, message FROM POLLS");

			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				polls.add(new Poll(rset.getLong(1), rset.getString(2), rset.getString(3)));
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
		
		return polls;
	}

	@Override
	public List<PollOption> getPollOption(long pollID, boolean sortVotes) {
		List<PollOption> options = new ArrayList<>();
		
		Connection con = SQLConnectionProvider.getConnection();
		
		try {
			PreparedStatement pst = con.prepareStatement(
					"SELECT id, optionTitle, optionLink, votesCount FROM POLLOPTIONS WHERE pollID = " 
					+ pollID + (sortVotes ? "ORDER BY votesCount DESC" : ""));

			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				options.add(new PollOption(rset.getLong(1), rset.getString(2), pollID, rset.getString(3), rset.getInt(4)));
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
		
		return options;
	}

	@Override
	public boolean vote(long id) {
		Connection con = SQLConnectionProvider.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement("UPDATE POLLOPTIONS SET VOTESCOUNT = VOTESCOUNT + 1 WHERE ID = " + id);
			pst.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		
		return true;
	}

	@Override
	public List<PollOption> getWinners(long pollID) {
		List<PollOption> winners = new ArrayList<>();
		
		Connection con = SQLConnectionProvider.getConnection();
		
		try {
			PreparedStatement pst = con.prepareStatement(
					"SELECT id, optionTitle, optionLink, votesCount FROM POLLOPTIONS WHERE pollID = " 
					+ pollID +
					"AND votesCount = (SELECT MAX(votesCount) FROM POLLOPTIONS WHERE pollID =" + pollID + ")");

			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				winners.add(new PollOption(rset.getLong(1), rset.getString(2), pollID, rset.getString(3), rset.getInt(4)));
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
		
		return winners;
	}

	@Override
	public Poll getPoll(Long pollID) {
		Connection con = SQLConnectionProvider.getConnection();
		
		try {
			PreparedStatement pst = con.prepareStatement(
					"SELECT * FROM POLLS WHERE id = " + pollID);

			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				return new Poll(rset.getLong(1), rset.getString(2), rset.getString(3));
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
		
		return null;
	}

	
}