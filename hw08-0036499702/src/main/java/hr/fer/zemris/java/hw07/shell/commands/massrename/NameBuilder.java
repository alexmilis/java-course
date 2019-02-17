package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * Functional interface that defines method execute that is essential for implementations of NameBuilder.
 * @author Alex
 *
 */
public interface NameBuilder {
	
	/**
	 * Executes command with information from {@link NameBuilderInfo}.
	 * It generates parts of name by writing into string builder provided in arguments.
	 * @param info
	 */
	void execute(NameBuilderInfo info);

}
