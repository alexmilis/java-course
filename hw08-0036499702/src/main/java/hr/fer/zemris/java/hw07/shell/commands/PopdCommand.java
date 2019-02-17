package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command popd takes no arguments. It pops a directory from stack in shared data and stores it as current directory.
 * If directory on the top of the stack no longer exists, it is removed from stack, but current directory is not changed.
 * @author Alex
 *
 */
public class PopdCommand implements ShellCommand {
	
	/**
	 * Name of this command.
	 */
	private static final String commandName = "popd";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command popd takes no arguments.",
			"It pops a directory from stack in shared data and stores it as current directory.",
			"If directory on the top of the stack no longer exists, it is removed from stack,",
			"but current directory is not changed.");
	

	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		
		Path p;
		
		try {
			p = ((Stack<Path>) env.getSharedData("cdstack")).pop();
		} catch (EmptyStackException ex) {
			env.writeln("Stack cdstack is empty. Directory is not changed");
			return ShellStatus.CONTINUE;
		}
		
		if(p.toFile().isDirectory() && p.toFile().exists()) {
			env.setCurrentDirectory(p);
		} else {
			env.writeln("Ditectory " + p.toString() + " no longer exists.");
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
