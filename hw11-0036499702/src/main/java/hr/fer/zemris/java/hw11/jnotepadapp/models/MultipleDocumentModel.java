package hr.fer.zemris.java.hw11.jnotepadapp.models;

import java.nio.file.Path;

/**
 * Interface for model that is capable of storing multiple {@link SingleDocumentModel} models.
 * @author Alex
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	/**
	 * Creates new empty and untitled document.
	 * @return new {@link SingleDocumentModel}.
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Gets current document from this model.
	 * @return current document.
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads existing document specified by given path into this model.
	 * @param path
	 * @return document
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves changes made to this document in editor.
	 * @param model document to be saved.
	 * @param newPath new location where document should be saved.
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Closes document.
	 * @param model
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds listener to list of listeners.
	 * @param l {@link MultipleDocumentListener}
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes listener from list of listeners.
	 * @param l {@link MultipleDocumentListener}
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Gets number of currently stored documents.
	 * @return number of documents
	 */
	int getNumberOfDocuments();

	/**
	 * Gets document stored under given index.
	 * @param index
	 * @return document at index 
	 */
	SingleDocumentModel getDocument(int index);

}
