package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Class used for initializing tables Polls and PollOptions in database votingDB.
 * It takes arguments for establishing connection and initializing data source from file dbsettings.properties.
 * If tables don't exist, it creates them and fills them with data from files polls.txt and pollOptions.txt.
 * 
 * @author Alex
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {
	
	/**
	 * SQL statement for creating table Polls.
	 */
	private final String pollStatement = "CREATE TABLE Polls\r\n" + 
			" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n" + 
			" title VARCHAR(150) NOT NULL,\r\n" + 
			" message CLOB(2048) NOT NULL\r\n" + 
			")";
	
	/**
	 * SQL statement for creating table pollOption.
	 */
	private final String pollOptionStatement = "CREATE TABLE PollOptions\r\n" + 
			" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n" + 
			" optionTitle VARCHAR(100) NOT NULL,\r\n" + 
			" optionLink VARCHAR(150) NOT NULL,\r\n" + 
			" pollID BIGINT,\r\n" + 
			" votesCount BIGINT,\r\n" + 
			" FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + 
			")";
	
	/**
	 * Path to file that contains data needed to fill table Polls.
	 */
	private String pollsPath;
	
	/**
	 * Path to file that contains data needed to fill table PollOptions.
	 */
	private String pollOptionPath;
	
	/**
	 * Map that stores existing poll titles and matching pollIDs.
	 * 			(key, value) = (poll title, pollID)
	 */
	private Map<String, Long> IDmap;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		Properties config = new Properties();
		try {
			config.load(sce.getServletContext().getResourceAsStream("/WEB-INF/dbsettings.properties"));
		} catch (IOException e2) {
			throw new RuntimeException("Properties file does not exist in specified location");
		}
		
		pollsPath = sce.getServletContext().getRealPath("/WEB-INF/polls.txt");
		pollOptionPath = sce.getServletContext().getRealPath("/WEB-INF/pollOptions.txt");

		String connectionURL = null;
		String user = null;
		try {
			String dbName = config.getProperty("name");
			user = config.getProperty("user");
			connectionURL = "jdbc:derby://" + config.getProperty("host") + ":" + 
					config.getProperty("port") + "/" + dbName + ";user=" + user +
					";password=" + config.getProperty("password");
		} catch (MissingResourceException ex) {
			throw new RuntimeException("Missing properties essential for establishing connection.");
		}
		
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Error occured while initializing pool.", e1);
		}
		
		cpds.setJdbcUrl(connectionURL);
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		
		IDmap = new HashMap<>();
		Connection con = null;
		
		try {
			con = cpds.getConnection();
			
			checkTable(con, "Polls", user);
			fillIDmap(con);
			checkTable(con, "PollOptions", user);
			
		} catch (SQLException e) {
			throw new RuntimeException("An error occured while accessing database and initializing tables");
		}
	}
	
	/**
	 * Fills IDmap if it is empty.
	 * @param con
	 * 				connection to database
	 * @throws SQLException
	 */
	private void fillIDmap(Connection con) throws SQLException {
		if(!IDmap.isEmpty()) {
			return;
		}
		
		PreparedStatement pst = null;
		pst = con.prepareStatement("SELECT id, title FROM POLLS");
		ResultSet result = pst.executeQuery();
		
		while(result.next()) {
			IDmap.put(result.getString(2), result.getLong(1));
		}
		
	}

	/**
	 * Creates table if it does not exist and fills it with data if it is empty.
	 * @param con
	 * 				connection to database.
	 * @param name
	 * 				name of table to be checked
	 * @param user 
	 * 				name of user which manages database
	 * @throws SQLException
	 */
	private void checkTable(Connection con, String name, String user) throws SQLException {
		ResultSet res = con.getMetaData().getTables(null, user.toUpperCase(), name.toUpperCase(), null);
		PreparedStatement pst = null;
					
		if(!res.next()) {	
			createTable(con, name);
		}
		
		pst = con.prepareStatement("SELECT count(*) FROM " + name);
		ResultSet resultSize = pst.executeQuery();
		
		if (resultSize.next()){
			if(resultSize.getInt(1) == 0) {
				fillTable(con, name);
			}
		}
		
		try { if(pst != null) pst.close(); } catch(SQLException ex) {}		
	}

	/**
	 * Fills table with data from files. Redirects actual work to specified methods.
	 * @param con
	 * 				connection to database
	 * @param name
	 * 				name of table to be filled
	 * @throws SQLException
	 */
	private void fillTable(Connection con, String name) throws SQLException {
		if(name.equals("Polls")) {
			fillPolls(con);
		} else {
			fillPollOptions(con);
		}
	}
	
	/**
	 * Fills table PollOptions with data from file pollOptions.txt.
	 * @param con
	 * 				connection to database
	 * @throws SQLException
	 */
	private void fillPollOptions(Connection con) throws SQLException {
		PreparedStatement pst = null;

		Path polls = Paths.get(pollOptionPath);
		List<String> lines = null;
		try {
			lines = Files.readAllLines(polls);
		} catch (IOException e) {
			throw new RuntimeException("File with data does not exist!");
		}
		
		for(String line : lines) {
			System.out.println(line);
			String[] parts = line.split("\t");
			String title = parts[0];
			
			pst = con.prepareStatement(
				"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)", 
				Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, parts[1]);
			pst.setString(2, parts[2]);
			pst.setString(3, IDmap.get(title).toString());
			pst.setString(4, "0");
			
			pst.executeUpdate();
		}
	}

	/**
	 * Fills table Polls with data from file polls.txt.
	 * @param con
	 * 				connection to database
	 * @throws SQLException
	 */
	private void fillPolls(Connection con) throws SQLException {
		PreparedStatement pst = null;

		Path polls = Paths.get(pollsPath);
		List<String> lines = null;
		try {
			lines = Files.readAllLines(polls);
		} catch (IOException e) {
			throw new RuntimeException("File with data does not exist!");
		}
		
		for(String line : lines) {
			String[] parts = line.split("\t");
			pst = con.prepareStatement(
				"INSERT INTO Polls (title, message) values (?,?)", 
				Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, parts[0]);
			pst.setString(2, parts[1]);
			
			pst.executeUpdate();
			
			ResultSet rset = pst.getGeneratedKeys();
			
			try {
				if(rset != null && rset.next()) {
					long newID = rset.getLong(1);
					IDmap.put(parts[0], newID);
				}
			} finally {
				try { rset.close(); } catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Creates table using prepared statements pollStatement and pollOptionStatement.
	 * @param con
	 * 				connection to database
	 * @param name
	 * 				name of table to be created
	 * @throws SQLException
	 */
	private void createTable(Connection con, String name) throws SQLException {
		PreparedStatement pst = null;
		
		pst = con.prepareStatement(name.equals("Polls") ? pollStatement : pollOptionStatement
					, Statement.RETURN_GENERATED_KEYS);

		pst.executeUpdate();
		
		try { if(pst != null) pst.close(); } catch(SQLException ex) {}	
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}