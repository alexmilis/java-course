package hr.fer.zemris.java.hw07.shell;

import java.util.List;

/**
 * Interface containing method that need to be implemented in all shell commands.
 * @author Alex
 *
 */
public interface ShellCommand {
	
	/**
	 * Executes command with given environment and arguments.
	 * @param env environment used to communicate with user.
	 * @param arguments arguments used by this command.
	 * @return {@link ShellStatus}  TERMINATE if command was exit.
	 * 								CONTINUE otherwise.
	 * @throws ShellIOException
	 */
	ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException;

	/**
	 * Gets command name.
	 * @return name of command.
	 */
	String getCommandName();

	/**
	 * Gets command description.
	 * @return description of command.
	 */
	List<String> getCommandDescription();

}
