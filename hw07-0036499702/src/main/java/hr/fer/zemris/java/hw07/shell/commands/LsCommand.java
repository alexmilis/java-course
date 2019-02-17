package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command ls writes a directory listing of given directory.
 * Output consists of four columns: file attributes, size, date and time of creation, and name of file.
 * @author Alex
 *
 */
public class LsCommand implements ShellCommand {

	/**
	 * Name of this command.
	 */
	private static final String commandName = "ls";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command ls takes a single argument - directory.",
			"It writes a directory listing (not recursive) to console.",
			"Output consists of four columns.",
			"First column indicates if current object is directory(d), readable(r), writable(w) and executable(x).",
			"Second column contains object size in bytes that is right aligned and occupies 10 characters.",
			"Follows file creation date/time and finally file name.");
	
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
			env.writeln("Invalid arguments for command ls: " + arguments);
			return ShellStatus.CONTINUE;
		}
		
		File dir = new File(args[0]);
		
		if(!dir.isDirectory()) {
			throw new IllegalArgumentException("File " + arguments + " is not a directory");
		}
		
		for(File child : dir.listFiles()) {
			try {
				env.writeln(getFileAttributes(child));
			} catch (IOException e) {
				throw new ShellIOException("An error occured while reading from file: " + dir.toString());
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
	
	/**
	 * Creates a string representation of each file's attributes.
	 * Output consists of four columns: file attributes, size, date and time of creation, and name of file.
	 * @param file file to be processed.
	 * @return one row of input which contains all attributes.
	 * @throws IOException
	 */
	private String getFileAttributes(File file) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(file.isDirectory() ? "d" : "-");
		sb.append(file.canRead() ? "r" : "-");
		sb.append(file.canWrite() ? "w" : "-");
		sb.append(file.canExecute() ? "x " : "- ");
		
		sb.append(String.format("%10d ", file.length()));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		BasicFileAttributeView faView = Files.getFileAttributeView(file.toPath(), BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		
		sb.append(formattedDateTime).append(file.getName());
		
		return sb.toString();
	}

}
