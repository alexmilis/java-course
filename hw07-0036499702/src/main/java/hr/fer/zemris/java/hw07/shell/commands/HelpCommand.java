package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command help gives a list of commands or a description of specified command.
 * @author Alex
 *
 */
public class HelpCommand implements ShellCommand {

	/**
	 * Name of this command.
	 */
	private static final String commandName = "help";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command help takes one or no arguments.",
			"If no arguments are given, it lists all supported commands.",
			"If started with single argument, it prints name and the description of selected command.",
			"If no such command exists, it prints appropriate error message.");
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		
		String[] args;
		try {
			args = CommandsUtil.getArgs(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		}	
		
		if(args.length != 1 && args.length != 0) {
			env.writeln("Invalid arguments for command help: " + arguments);
			return ShellStatus.CONTINUE;
		}
		
		if(args.length == 0) {
			for(Map.Entry<String, ShellCommand> entry : env.commands().entrySet()) {
				env.writeln(entry.getValue().getCommandName());
			}
		} else {
			if(env.commands().containsKey(args[0])) {
				for(String line : env.commands().get(args[0]).getCommandDescription()) {
					env.writeln(line);
				}
			} else {
				env.writeln("Command " + args[0] + " does not exist.");
			}
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
