package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.*;

/**
 * Command-line program MyShell. Able to perform some basic shell operations:
 * 		cat, charsets, copy, help, hexdump, ls, mkdir, tree, symbol, exit
 * User should type in command name and arguments.
 * If user wishes to input more lines, he should use MORELINESSYMBOL at the end of each line.
 * @author Alex
 *
 */

public class MyShell {

	/**
	 * Main method that starts the shell.
	 * @param args command line arguments, not needed here.
	 * @throws ShellIOException if there is an input/output problem.
	 */
	public static void main(String[] args) {
		
		SortedMap<String, ShellCommand> commands = new TreeMap<>();
		
		commands.put("cat", new CatCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("copy", new CopyCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("ls", new LsCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("tree", new TreeCommand());
		commands.put("symbol", new SymbolCommand());
		commands.put("pwd", new PwdCommand());
		commands.put("cd", new CdCommand());
		commands.put("pushd", new PushdCommand());
		commands.put("popd", new PopdCommand());
		commands.put("listd", new ListdCommand());
		commands.put("dropd", new DropdCommand());
		commands.put("rmtree", new RmtreeCommand());
		commands.put("cptree", new CptreeCommand());
		commands.put("massrename", new MassrenameCommand());

		Scanner sc = new Scanner(System.in);

		Environment env = new Environment() {
			
			/**
			 * Default prompt symbol.
			 */
			private char promptSymbol = '>';
			
			/**
			 * Default morelines symbol.
			 */
			private char morelinesSymbol = '\\';
			
			/**
			 * Default multiline symbol.
			 */
			private char multilineSymbol = '|';
			
			private Path currentDirectory = Paths.get(".").toAbsolutePath().normalize();//apsolutno + normaliziraj
			
			private Map<String, Object> sharedData = new HashMap<>();
			
			@Override
			public String readLine() throws ShellIOException {
				while(!sc.hasNext());
				String line = sc.nextLine();
				return line;
			}

			@Override
			public void write(String text) throws ShellIOException {
				System.out.print(text);
			}

			@Override
			public void writeln(String text) throws ShellIOException {
				System.out.println(text);
			}

			@Override
			public SortedMap<String, ShellCommand> commands() {
				return Collections.unmodifiableSortedMap(commands);
			}

			@Override
			public Character getMultilineSymbol() {
				return multilineSymbol;
			}

			@Override
			public void setMultilineSymbol(Character symbol) {
				multilineSymbol = symbol;
			}

			@Override
			public Character getPromptSymbol() {
				return promptSymbol;
			}

			@Override
			public void setPromptSymbol(Character symbol) {
				promptSymbol = symbol;
			}

			@Override
			public Character getMorelinesSymbol() {
				return morelinesSymbol;
			}

			@Override
			public void setMorelinesSymbol(Character symbol) {
				morelinesSymbol = symbol;
			}

			
			//------------------------------------------------------------
			@Override
			public Path getCurrentDirectory() {
				return currentDirectory;
			}

			@Override
			public void setCurrentDirectory(Path path) throws ShellIOException {
				if(path.toFile().exists() && path.toFile().isDirectory()) {
					currentDirectory = path;
				} else {
					throw new ShellIOException("Cannot set " + path + "as current directory because it does not exist.");
				}
			}

			@Override
			public Object getSharedData(String key) {
				return sharedData.get(key);
			}

			@Override
			public void setSharedData(String key, Object value) {
				sharedData.put(key, value);
			}
			
		};
		
		
		
		try {
			env.writeln("Welcome to MyShell v 1.0");
		} catch (ShellIOException e) {
			sc.close();
			return;
		}
		
		while(true) {
			try {
				env.write(env.getPromptSymbol() + " ");
				String line = env.readLine();
				
				String command;
				
				if(line.contains(" ")) {
					command = line.substring(0, line.indexOf(" "));
					line = line.substring(line.indexOf(" "));
				} else {
					command = line;
					line = "";
				}
								
				while(line.endsWith(env.getMorelinesSymbol().toString())) {
					line = line.substring(0, line.length() - 1);
					env.write(env.getMultilineSymbol() + " ");
					line += env.readLine();
				}
				
				if(env.commands().containsKey(command)) {
					if(env.commands().get(command).executeCommand(env, line)
							== ShellStatus.TERMINATE) {
						sc.close();
						return;
					}
				} else {
					env.writeln("Command " + command + " does not exist.");
				}
			} catch (ShellIOException ex) {
				sc.close();
				return;
			}
		}
	}
}
