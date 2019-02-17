package hr.fer.zemris.java.hw07.shell;

import java.util.Collections;
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
