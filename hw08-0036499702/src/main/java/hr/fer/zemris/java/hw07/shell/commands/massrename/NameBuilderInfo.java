package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * Interface that contains methods for retrieval of information from NameBuilder.
 * @author Alex
 *
 */
public interface NameBuilderInfo {
	
	/**
	 * Getter for string builder.
	 * @return string builder
	 */
	StringBuilder getStringBuilder();
	
	/**
	 * Gets group at given index.
	 * @param index
	 * @return string
	 */
	String getGroup(int index);

}
