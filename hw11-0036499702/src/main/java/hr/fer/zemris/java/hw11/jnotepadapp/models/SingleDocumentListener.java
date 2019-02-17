package hr.fer.zemris.java.hw11.jnotepadapp.models;

/**
 * Interface for observers of class {@link SingleDocumentModel}.
 * @author Alex
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * Performs an action when document has been modified.
	 * @param model
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Performs an action when documents path has been modified.
	 * @param model
	 */
	void documentFilePathUpdated(SingleDocumentModel model);

}
