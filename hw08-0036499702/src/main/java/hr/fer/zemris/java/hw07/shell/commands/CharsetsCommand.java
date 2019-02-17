package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command charsets lists names of all supported charsets for users Java platform.
 * @author Alex
 *
 */
public class CharsetsCommand implements ShellCommand {

	/**
	 * Name of this command.
	 */
	private static final String commandName = "charsets";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command charsets lists all supported charsets for this Java platform.");
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		for(Charset set : Charset.availableCharsets().values()) {
			env.writeln(set.name());
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
