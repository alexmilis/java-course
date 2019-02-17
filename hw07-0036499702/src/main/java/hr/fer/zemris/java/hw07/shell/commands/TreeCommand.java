package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command tree prints out tree of given directory.
 * @author Alex
 *
 */
public class TreeCommand implements ShellCommand {
	
	/**
	 * Name of this command.
	 */
	private static final String commandName = "tree";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command tree takes a single argument - directory.",
			"It prints tree of that direcotry to console.",
			"Each directory level shifts output two characters to the right.");

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
			env.writeln("Invalid arguments for command tree: " + arguments);
			return ShellStatus.CONTINUE;
		}
		
		try {
			walkTree(env, new File(args[0]), 0);
		} catch (IOException e) {
			throw new ShellIOException("File doesn't exist or is not a directory: " + arguments);
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
	
	/**
	 * Recursive method used to get to all files contained in directory.
	 * @param env environment used to communicate with user.
	 * @param file file to be added to tree.
	 * @param level level of file in directory.
	 * @throws IOException
	 * @throws ShellIOException
	 */
	private static void walkTree(Environment env, File file, int level) throws IOException, ShellIOException {
		
		String tab = new String(new char[level]).replace("\0", "  ");;
				
		env.writeln(tab + file.getName());
		
		if(file.isDirectory()) {
			for(File child : file.listFiles()) {
				walkTree(env, child, level + 1);
			}
		}
	}

}
