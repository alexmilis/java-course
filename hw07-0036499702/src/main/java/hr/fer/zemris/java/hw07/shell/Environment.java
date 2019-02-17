package hr.fer.zemris.java.hw07.shell;

import java.util.SortedMap;

/**
 * Interface used by program {@link MyShell} and commands to communicate with user.
 * @author Alex
 *
 */
public interface Environment {
	
	/**
	 * Reads a line of user input from shell.
	 * @return line.
	 * @throws ShellIOException if line cannot be read.
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes given text to shell.
	 * @param text.
	 * @throws ShellIOException if text cannot be written.
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes given line of text to shell.
	 * @param text.
	 * @throws ShellIOException if text cannot be written.
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Returns all commands supported by this shell, mapped by their names.
	 * @return unmodifiable sorted map of commands.
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Gets multiline symbol.
	 * @return multiline symbol.
	 */
	Character getMultilineSymbol();

	/**
	 * Sets multiline symbol to new symbol.
	 * @param symbol to be set as multiline symbol.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Gets prompt symbol.
	 * @return prompt symbol.
	 */
	Character getPromptSymbol();

	/**
	 * Sets prompt symbol to new symbol.
	 * @param symbol to be set as prompt symbol.
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Gets morelines symbol.
	 * @return morelines symbol.
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets morelines symbol to new symbol.
	 * @param symbol to be set as morelines symbol.
	 */
	void setMorelinesSymbol(Character symbol);

}
