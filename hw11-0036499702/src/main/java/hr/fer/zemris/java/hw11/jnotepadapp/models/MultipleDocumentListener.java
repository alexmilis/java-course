package hr.fer.zemris.java.hw11.jnotepadapp.models;

/**
 * Interface that defines method essetial for observers of {@link MultipleDocumentModel}.
 * @author Alex
 *
 */
public interface MultipleDocumentListener {
	
	/**
	 * Performs an action when current document is changed.
	 * @param previousModel
	 * @param currentModel
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel,
			SingleDocumentModel currentModel);
	
	/**
	 * Performs an action when document is added to {@link MultipleDocumentModel}.
	 * @param model {@link SingleDocumentModel}.
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Performs an action when document is removed from {@link MultipleDocumentModel}.
	 * @param model {@link SingleDocumentModel}.
	 */
	void documentRemoved(SingleDocumentModel model);

}
