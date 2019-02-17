package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command cat opens given file and writes its content to console.
 * Expected arguments are obligatory path and charset.
 * If there is no argument for charset, default charset will be used.
 * @author Alex
 *
 */
public class CatCommand implements ShellCommand {
	
	/**
	 * Name of this command.
	 */
	private static final String commandName = "cat";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command cat takes one or two arguments.",
			"The first argument is path to file and is mandatory.",
			"The second argument is charset name that shoulf be used to interpret chars from bytes.",
			"If not provided, defalut platform charset should be used.",
			"This command opens given file and writes its contents to console.");
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		
		String[] args;
		
		try {
			args = CommandsUtil.getArgs(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if(args.length < 1 || args.length > 2) {
			env.writeln("Invalid number of arguments for cat command");
			return ShellStatus.CONTINUE;
		}
		
		Path p = Paths.get(args[0]);
		
		if(!p.isAbsolute()) {
			p = env.getCurrentDirectory().resolve(p);		}
		
		Charset charset = args.length == 2 ? Charset.forName(args[1]) : Charset.defaultCharset();
		
		try {
			BufferedReader br = Files.newBufferedReader(p, charset);
			String line = "Reading from file: " + p.toString();
			do {
				env.writeln(line);
				line = br.readLine();
			} while(line != null);
			br.close();
			
		} catch (IOException e) {
			throw new ShellIOException("An error occured while reading from file: " + p.toString());
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
