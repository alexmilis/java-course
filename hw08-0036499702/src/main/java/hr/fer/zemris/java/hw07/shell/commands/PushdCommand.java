package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command pushd pushes current directory on stack in shared data and puts given argument as current directory.
 * @author Alex
 *
 */
public class PushdCommand implements ShellCommand {

	/**
	 * Name of this command.
	 */
	private static final String commandName = "pushd";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command pushd takes one arguments.",
			"It pushes current directory on stack and puts argument as current directory.",
			"In shared data, under key \"cdstack\" it creates a stack (if it does not yet exist).",
			"If argument is not a path to a valid directory, it does nothing.");
	
	
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
			env.writeln("Invalid number of arguments for pushd command");
			return ShellStatus.CONTINUE;
		}
		
		Path p = Paths.get(args[0]);
		p = env.getCurrentDirectory().resolve(p);
		
		
		if(!p.toFile().exists() || !p.toFile().isDirectory()) {
			env.writeln("Given argument is not a valid directory: " + p.toString());
			return ShellStatus.CONTINUE;
		}
		
		Object obj = env.getSharedData("cdstack");
		if(obj == null) {
			Stack<Path> cdstack = new Stack<>();
			cdstack.push(env.getCurrentDirectory());
			env.setCurrentDirectory(p);
			env.setSharedData("cdstack", cdstack);
		} else {
			@SuppressWarnings("unchecked")
			Stack<Path> cdstack = (Stack<Path>) obj;
			cdstack.push(env.getCurrentDirectory());
			env.setCurrentDirectory(p);
			
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
