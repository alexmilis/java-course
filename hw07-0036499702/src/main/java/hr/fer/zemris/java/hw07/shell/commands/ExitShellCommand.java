package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command exit terminates the shell.
 * @author Alex
 *
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * Name of this command.
	 */
	private static final String commandName = "exit";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command exit takes no arguments.",
			"It terminates the shell programe.");
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
