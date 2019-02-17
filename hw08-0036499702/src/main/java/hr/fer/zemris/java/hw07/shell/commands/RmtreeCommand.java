package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command rmtree takes one arguments - path to an existing directory.
 * It deletes that directory and all its contents.
 * @author Alex
 *
 */
public class RmtreeCommand implements ShellCommand {
	
	/**
	 * Name of this command.
	 */
	private static final String commandName = "rmtree";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command rmtree takes one arguments - path to an existing directory.",
			"It deletes that directory and all its contents.");
	

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
			env.writeln("Invalid number of arguments for rmtree command");
			return ShellStatus.CONTINUE;
		}
		
		Path p = Paths.get(args[0]);
		p = env.getCurrentDirectory().resolve(p);
		
		if(!p.toFile().exists() || !p.toFile().isDirectory()) {
			env.writeln("Given argument is not a valid directory: " + p.toString());
			return ShellStatus.CONTINUE;
		}
		
		walkTree(p.toFile());
		
		if(!Files.exists(p)) {
			env.writeln("Directory successfully deleted: " + p.toString());
		} else {
			env.writeln("An error occured, directory is not deleted.");
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
	 * @param file file to be deleted.
	 */
	private static void walkTree(File file) {
						
		if(file.isDirectory()) {
			for(File child : file.listFiles()) {
				walkTree(child);
			}
		}
		
		file.delete();
	}

}
