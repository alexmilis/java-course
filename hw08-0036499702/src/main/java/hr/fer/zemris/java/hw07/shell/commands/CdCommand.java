package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command cd changes current directory.
 * @author Alex
 *
 */
public class CdCommand implements ShellCommand {

	/**
	 * Name of this command.
	 */
	private static final String commandName = "cd";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command cd takes one arguments.",
			"It changes current directory to directory given as argument.");
	
	
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
			env.writeln("Invalid number of arguments for cat command");
			return ShellStatus.CONTINUE;
		}
		
		Path p = Paths.get(args[0]);
		
		
		p = env.getCurrentDirectory().resolve(p);

		if(p.toFile().isDirectory()) {
			env.setCurrentDirectory(p);
		} else {
			env.writeln("Argument is not a directory: " + p.toString());
		}		
		
		env.writeln("Directory successfully changed to: " + p.toString());
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
