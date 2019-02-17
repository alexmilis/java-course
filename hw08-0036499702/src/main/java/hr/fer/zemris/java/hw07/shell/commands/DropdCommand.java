package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command dropd takes no arguments.
 * It drops directory from the top of the stack in shared data.
 * "If stack is empty, it prints \"No stored directories\".
 * @author Alex
 *
 */
public class DropdCommand implements ShellCommand {

	/**
	 * Name of this command.
	 */
	private static final String commandName = "dropd";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command dropd takes no arguments.",
			"It drops directory from the top of the stack in shared data.",
			"If stack is empty, it prints \"No stored directories.\".");
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {

		try {
			((Stack<Path>) env.getSharedData("cdstack")).pop();
		} catch (EmptyStackException ex) {
			env.write("No stored directories.");
		}
		
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
