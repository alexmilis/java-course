package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command hexdump produces hex-output of given file.
 * It does not work with directories.
 * First column is position of data, then follows hex-output of data, and finally original data.
 * @author Alex
 *
 */
public class HexdumpCommand implements ShellCommand {
	
	/**
	 * Number of bytes that can be read at once.
	 */
	private static int BUFFSIZE = 16;

	/**
	 * Name of this command.
	 */
	private static final String commandName = "hexdump";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command hexdump takes a single argument - file name (no directories).",
			"It produces hex-output of that file.",
			"It supports only standard subset of characters, for all other characters a \".\" is printed.");
	
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
			env.writeln("Invalid arguments for command mkdir: " + arguments);
			return ShellStatus.CONTINUE;
		}
		
		Path p = Paths.get(args[0]);
		
		if(p.toFile().isDirectory()) {
			throw new IllegalArgumentException("Cannot execute command hexdump with argument directory" + args[0]);
		}
		
		try {
			InputStream in = new BufferedInputStream(Files.newInputStream(p));
			byte[] buff = new byte[BUFFSIZE];
			
			int r = in.read(buff);
			int counter = 0;
			
			while(r != -1) {
				StringBuilder sb = new StringBuilder();
				
				sb.append(String.format("%08d", (counter++) * 10)).append(": ");
				
				for(int i = 0; i < BUFFSIZE; i++) {
					if(i < r) {
						sb.append(String.format("%02X", buff[i]).toUpperCase());
					} else {
						sb.append("  ");
					}
					
					if(buff[i] < 32 || buff[i] > 127) {
						buff[i] = (byte) '.';
					}
					
					if(i != 7) {
						sb.append(" ");
					} else {
						sb.append("|");
					}
				}
				if(sb.toString().trim().length() != 0) {
					sb.append("| ");
					sb.append(new String(buff, 0, r));
					env.writeln(sb.toString());
					r = in.read(buff);
				}					
			}
			
		} catch (IOException ex) {
			throw new ShellIOException("An error occured while reading file: " + p.toString());
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
