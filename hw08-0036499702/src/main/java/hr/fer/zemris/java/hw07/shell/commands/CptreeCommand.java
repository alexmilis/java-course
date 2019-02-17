package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
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
 * Command cptree takes two arguments - paths to directories.
 * It copies one directory and all its contents into another directory.
 * First argument is path to existing directory that should be copied.
 * If second argument is not an existing directory, but its parent directory exists,
 * then first directory is copied into that parent directory under name of second directory.
 * @author Alex
 *
 */
public class CptreeCommand implements ShellCommand {

	/**
	 * Name of this command.
	 */
	private static final String commandName = "cptree";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command cptree takes two arguments - paths to directories.",
			"It copies one directory and all its contents into another directory.",
			"First argument is path to existing directory that should be copied.",
			"If second argument is not an existing directory, but its parent directory exists,",
			"then first directory is copied into that parent directory under name of second directory.");
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		String[] args;
		
		try {
			args = CommandsUtil.getArgs(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if(args.length != 2) {
			env.writeln("Invalid number of arguments for cptree command");
			return ShellStatus.CONTINUE;
		}
		
		Path sourcepath = Paths.get(args[0]);
		sourcepath = env.getCurrentDirectory().resolve(sourcepath);
		File source = sourcepath.toFile();
		
		Path destpath = Paths.get(args[1]);
		destpath = env.getCurrentDirectory().resolve(destpath);
		File dest = destpath.toFile();
		
		if(!source.isDirectory()) {
			env.writeln("Source file is not a directory: " + sourcepath.toString());
			return ShellStatus.CONTINUE;
		}
		
		if(dest.isDirectory() && dest.exists()) {
			destpath = destpath.resolve(sourcepath.getFileName());
		} else 
			if(!dest.getParentFile().exists()) {
			env.writeln("Destination directory is invalid: " + destpath.toString());
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.createDirectory(destpath);
		} catch (IOException e) {
			throw new ShellIOException("An error occured while creating destination directory.");
		}
				
		for(File child : source.listFiles()) {
			walkTree(child, destpath);
		}		
		
		env.writeln("Directory " + sourcepath.toString());
		env.writeln(" succeccfully copied to directory " + destpath.toString());
		
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
	 * Recursive method used to copy to all files contained in directory.
	 * @param source file to be copied
	 * @param destpath path to destination directory
	 * @throws ShellIOException 
	 */
	private static void walkTree(File source, Path destpath) throws ShellIOException {		
		if(source.isDirectory()) {
			destpath = destpath.resolve(source.getName());
			try {
				Files.createDirectory(destpath);
			} catch (IOException e) {
				throw new ShellIOException("An error occured while creating directory: " + destpath.toString());
			}
				
			for(File child : source.listFiles()) {
				walkTree(child, destpath);
			}
		} else {
			try {
				Files.copy(source.toPath(), destpath);
			} catch (IOException e) {
				throw new ShellIOException("An error occured while copying a file: " + source.getPath().toString());
			}
		}
	}

}
