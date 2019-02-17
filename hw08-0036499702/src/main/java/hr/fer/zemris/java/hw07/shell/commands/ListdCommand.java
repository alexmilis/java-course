package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.nio.file.Path;

/**
 * Command listd takes no arguments.
 * It lists directories from stack in shared data, starting with directory that was last pushed on stack.
 * If stack is empty, it prints "No stored directories".
 * @author Alex
 *
 */
public class ListdCommand implements ShellCommand {

	/**
	 * Name of this command.
	 */
	private static final String commandName = "listd";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command listd takes no arguments.",
			"It lists directories from stack in shared data, starting with directory that was last pushed on stack.",
			"If stack is empty, it prints \"No stored directories\".");
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {

		@SuppressWarnings("unchecked")
		Stack<Path> cdstack = (Stack<Path>) env.getSharedData("cdstack");
		
		if(cdstack == null || cdstack.isEmpty()) {
			env.writeln("No stored directories.");
			return ShellStatus.CONTINUE;
		}
		
		Object[] paths = cdstack.toArray();
		
		for(int i = paths.length - 1; i >= 0; i--) {
			env.writeln(paths[i].toString());
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
