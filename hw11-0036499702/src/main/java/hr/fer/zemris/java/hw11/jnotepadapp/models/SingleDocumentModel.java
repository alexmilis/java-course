package hr.fer.zemris.java.hw11.jnotepadapp.models;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Model that represents single document.
 * @author Alex
 *
 */
public interface SingleDocumentModel {
	
	/**
	 * Editor in which document is shown and edited.
	 * @return
	 */
	JTextArea getTextComponent();

	/**
	 * Gets path of document, can be null.
	 * @return path
	 */
	Path getFilePath();

	/**
	 * Sets path of document, can be null.
	 * @param path
	 */
	void setFilePath(Path path);

	/**
	 * Checks if document has been modified.
	 * @return true if modified.
	 */
	boolean isModified();

	/**
	 * Sets documents parameter modified.
	 * @param modified
	 */
	void setModified(boolean modified);

	/**
	 * Adds listener to list of listeners.
	 * @param l {@link SingleDocumentListener}
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes listener from list of listeners.
	 * @param l {@link SingleDocumentListener}.
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}

