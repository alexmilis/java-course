package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command mkdir makes a directory of given path (if such does not yet exist).
 * @author Alex
 *
 */
public class MkdirCommand implements ShellCommand {

	/**
	 * Name of this command.
	 */
	private static final String commandName = "mkdir";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command mkdir takes a single argument: directory name.",
			"It creates appropriate directory structure");
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {

		String[] args;
		try {
			args = CommandsUtil.getArgs(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if(args.length != 1) {
			env.writeln("Invalid arguments for command mkdir: " + arguments);
			return ShellStatus.CONTINUE;
		}
		
		try {
			Path p = Paths.get(args[0]);
			if(Files.exists(p)) {
				env.writeln("Directory " + args[0] + " already exists.");
				return ShellStatus.CONTINUE;
			}
			Files.createDirectory(p);
		} catch (IOException e) {
			throw new ShellIOException("An error occured while creating directory: " + args[0]);
		}
		
		env.writeln("Directory successfully created: " + args[0]);
		
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
