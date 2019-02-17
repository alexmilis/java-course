package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command symbol prints out symbol for given argument if there is only one argument.
 * If there are two arguments, it changes the symbol for that argument to new symbol.
 * @author Alex
 *
 */
public class SymbolCommand implements ShellCommand {

	/**
	 * Name of this command.
	 */
	private static final String commandName = "symbol";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command symbol takes one or two arguments.",
			"The first argument is name of symbol (PROMPT, MORELINES or MULTILINE).",
			"If there is only one argument, is prints out the character used for that symbol.",
			"The second argument is new character that should be used as symbol.");
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {

		String[] args;
		try {
			args = CommandsUtil.getArgs(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if(args.length != 1 && args.length != 2) {
			env.writeln("Invalid arguments for command symbol: " + arguments);
			return ShellStatus.CONTINUE;
		}
		
		switch(args[0].toUpperCase()) {
		case "PROMPT":
			if(args.length == 1) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol().toString() + "'.");
			} else {
				env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" + args[1] + "'.");
				env.setPromptSymbol(args[1].charAt(0));
			}
			break;
		case "MORELINES":
			if(args.length == 1) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol().toString() + "'.");
			} else {
				env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '" + args[1] + "'.");
				env.setMorelinesSymbol(args[1].charAt(0));
			}
			break;
		case "MULTILINE":
			if(args.length == 1) {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol().toString() + "'.");
			} else {
				env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to '" + args[1] + "'.");
				env.setMultilineSymbol(args[1].charAt(0));
			}
			break;
		default:
			env.writeln("Symbol " + args[0] + " does not exist.");
			break;
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
