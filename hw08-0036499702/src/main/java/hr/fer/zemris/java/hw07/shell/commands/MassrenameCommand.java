package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.commands.massrename.*;

/**
 * Command massrename takes multiple arguments: source, destination, subcommand, mask and others.
 * It is used for operations of mass copying or renaming.
 * It can copy/rename only files stored directly in source directory.
 * Possible subcommands are: filter, groups, show, execute.
 * Mask is a pattern used to determine which files should be copied/renamed.
 * Filter lists files that match mask.
 * Groups lists groups of files that passed the filter.
 * Show lists all files and their new names/names of copies.
 * Execute executes the command.
 * @author Alex
 *
 */
public class MassrenameCommand implements ShellCommand {

	/**
	 * Name of this command.
	 */
	private static final String commandName = "massrename";
	
	/**
	 * Description of this command.
	 */
	private static final List<String> commandDescription = List.of(
			"Command massrename takes multiple arguments: source, destination, subcommand, mask and others.",
			"It is used for operations of mass copying or renaming.",
			"It can copy/rename only files stored directly in source directory.",
			"Subcommand determines which step of operation should be done.",
			"Possible subcommands are: filter, groups, show, execute.",
			"Mask is a pattern used to determine which files should be copied/renamed.",
			"Filter lists files that match mask.",
			"Groups lists groups of files that passed the filter.",
			"Show lists all files and their new names/names of copies.",
			"Execute executes the command.",
			"Other arguments are needed only for subcommands show and execute.",
			"That argument is another regex that determines destination name.");
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		String[] args;
		
		try {
			args = CommandsUtil.getArgs(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if(args.length < 4) {
			env.writeln("Invalid arguments for command massrename: " + arguments);
			return ShellStatus.CONTINUE;
		}
		
		Path sourcepath = Paths.get(args[0]);
		sourcepath = env.getCurrentDirectory().resolve(sourcepath);
		
		Path destpath = Paths.get(args[1]);
		destpath = env.getCurrentDirectory().resolve(destpath);
		
		String command = args[2];
		Pattern mask = Pattern.compile(args[3], Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		int groupNo = mask.matcher("").groupCount();
		
		switch(command) {
		case "filter":
			filter(sourcepath, mask, env);
			break;
			
		case "groups":
			group(sourcepath, mask, env, groupNo);
			break;
			
		case "show":
			if(args.length != 5) {
				env.writeln("Invalid arguments for command massrename show: " + arguments);
				return ShellStatus.CONTINUE;
			}
				
			show(sourcepath, mask, env, groupNo, args[4]);
			break;

		case "execute":
			if(args.length != 5) {
				env.writeln("Invalid arguments for command massrename show: " + arguments);
				return ShellStatus.CONTINUE;
			}
			
			execute(sourcepath, destpath, mask, env, groupNo, args[4]);
			
			env.writeln("Files from " + sourcepath.toString() + " successfully copied/renamed.");
			break;
			
		default:
			env.writeln("Invalid massrename command: " + command);
			return ShellStatus.CONTINUE;
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
	 * Method that lists all files that passed the filter.
	 * @param sourcepath path to source directory.
	 * @param mask pattern to match
	 * @param env environment used for communication with console
	 * @throws ShellIOException
	 */
	private void filter(Path sourcepath, Pattern mask, Environment env) throws ShellIOException {
		for(String name : sourcepath.toFile().list()) {
			if(mask.matcher(name).matches()) {
				env.writeln(name);
			}
		}
	}
	
	/**
	 * Method that lists all files and their groups that passed the filter.
	 * @param sourcepath path to source directory.
	 * @param mask pattern to match
	 * @param env environment used for communication with console
	 * @param groupNo number of groups in mask
	 * @throws ShellIOException
	 */
	private void group(Path sourcepath, Pattern mask, Environment env, int groupNo) throws ShellIOException {
		for(String name : sourcepath.toFile().list()) {
			if(mask.matcher(name).matches()) {
				StringBuilder sb = new StringBuilder();
				sb.append(name);
				
				Matcher m = mask.matcher(name);
				m.matches();

				for(int i = 0; i <= groupNo; i++) {
					sb.append(" ").append(i).append(": ").append(m.group(i));	
				}
				
				env.writeln(sb.toString());
			}
		}
	}
	
	/**
	 * Method that lists all files and their new names/names of their copies.
	 * @param sourcepath path to source directory.
	 * @param mask pattern to match
	 * @param env environment used for communication with console
	 * @param groupNo number of groups in mask
	 * @param expression pattern for creating new name/name of copy
	 * @throws ShellIOException
	 */
	private void show(Path sourcepath, Pattern mask, Environment env, int groupNo, String expression) throws ShellIOException {
		NameBuilderParser parser;
		try{
			parser = new NameBuilderParser(expression);
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid expression for command massrename: " + expression);
			return;
		}
		NameBuilder builder = parser.getNameBuilder();
		
		for(String name : sourcepath.toFile().list()) {
			if(mask.matcher(name).matches()) {
				StringBuilder sb = new StringBuilder();
				sb.append(name).append(" => ");
				
				Map<Integer, String> groups = new HashMap<>();

				Matcher m = mask.matcher(name);
				m.matches();
				for(int i = 0; i <= groupNo; i++) {
					groups.put(i, m.group(i));
				}
				
				NameBuilderInfo info = new NameBuilderInfo() {
					
					@Override
					public StringBuilder getStringBuilder() {
						return sb;
					}

					@Override
					public String getGroup(int index) {
						return groups.get(index);
					}
					
				};
				
				builder.execute(info);
				env.writeln(info.getStringBuilder().toString());
			}
		}
	}
	
	/**
	 * Method that finally executes command and renames/copies files.
	 * @param sourcepath path to source directory.
	 * @param destpath path to destination directory.
	 * @param mask pattern to match
	 * @param env environment used for communication with console
	 * @param groupNo number of groups in mask
	 * @param expression pattern for creating new name/name of copy
	 * @throws ShellIOException
	 */
	private void execute(Path sourcepath, Path destpath, Pattern mask, Environment env, int groupNo, String expression) throws ShellIOException {
		NameBuilderParser parser;
		try{
			parser = new NameBuilderParser(expression);
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid expression for command massrename: " + expression);
			return;
		}
		NameBuilder builder = parser.getNameBuilder();
		
		boolean same = sourcepath.equals(destpath);//provjeri moze li equals
		
		for(File f : sourcepath.toFile().listFiles()) {
			String name = f.getName();
			if(mask.matcher(name).matches()) {
				Map<Integer, String> groups = new HashMap<>();
				Matcher m = mask.matcher(name);
				m.matches();
				for(int i = 0; i <= groupNo; i++) {
					groups.put(i, m.group(i));
				}
				
				StringBuilder sb = new StringBuilder();
				NameBuilderInfo info = new NameBuilderInfo() {
					
					@Override
					public StringBuilder getStringBuilder() {
						return sb;
					}

					@Override
					public String getGroup(int index) {
						return groups.get(index);
					}
					
				};
				
				builder.execute(info);
				String newName = info.getStringBuilder().toString();
				
				if(same) {
					f.renameTo(new File(newName));
				} else {
					try {
						Files.move(f.toPath(), destpath.resolve(newName));
					} catch (IOException e) {
						throw new ShellIOException("An error occured while copying a file: " + f.toString());
					}
				}
			}
		}
	}
}
