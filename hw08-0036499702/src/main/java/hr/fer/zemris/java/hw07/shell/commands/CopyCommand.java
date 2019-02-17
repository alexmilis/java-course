package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command copy copies a given file to destination.
 * If destination is a directory, copy of the same name as original file is placed in that directory.
 * If destination file already exists, it asks the user can it be overwritten.
 * @author Alex
 *
 */
public class CopyCommand implements ShellCommand {
	
	/**
	 * Name of this command.
	 */
	private static final String commandName = "copy";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command tree takes a two arguments: source file and destination file name.",
			"Is destination file exists, it asks user is it allowed to overwrite it.",
			"If not allowed to overwrite, adds \"-copy\" to file name.",
			"This command works only on files (not directories).",
			"If the second argument is directory, it should assume that user wants to copy ",
			"the original file into that directory using the original file name.");

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
			env.writeln("Invalid arguments for command copy: " + arguments);
			return ShellStatus.CONTINUE;
		}
		
		Path source = Paths.get(args[0]);
		source = env.getCurrentDirectory().resolve(source);
		
		Path dest = Paths.get(args[1]);
		dest = env.getCurrentDirectory().resolve(dest);
		
		if(source.toFile().isDirectory()) {
			env.writeln("Cannot copy a directory: " + source.toString());
		}
		
		if(dest.toFile().isDirectory()) {
			dest = Paths.get(dest.toString() + "//" + source.toFile().getName());
		}
		
		if(dest.toFile().exists()) {
			env.writeln("Destination file already exists. Do you wish to overwrite it? Type yes or no");
			if(env.readLine().trim().equals("no")) {
				dest = Paths.get(dest.toString().substring(0, dest.toString().lastIndexOf('.')) + 
						"-copy" + dest.toString().substring(dest.toString().lastIndexOf('.')));
			}//dodaje copy u ime, whiile ak postoji i kopija??
		}
		
		try {
			InputStream in = new BufferedInputStream(Files.newInputStream(source));
			OutputStream out = new BufferedOutputStream(Files.newOutputStream(dest, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
			byte[] buff = new byte[4096];
			
			int r = in.read(buff);
			while(r != -1) {
				out.write(buff, 0, r);
				out.flush();
				r = in.read(buff);
			}
			
			in.close();
			out.close();
		} catch (IOException ex) {
			throw new ShellIOException("An error occured while copying a file.");
		}
		
		env.writeln("File succesfully copied as " + dest.toString());
		
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
