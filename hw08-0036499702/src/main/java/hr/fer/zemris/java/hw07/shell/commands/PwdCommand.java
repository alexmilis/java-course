package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command pwd prints out current directory.
 * @author Alex
 *
 */
public class PwdCommand implements ShellCommand {

	/**
	 * Name of this command.
	 */
	private static final String commandName = "pwd";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command pwd takes no arguments.",
			"It prints out absolute path of current directory.");
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		env.writeln(env.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
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
