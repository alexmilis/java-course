package hr.fer.zemris.java.hw11.jnotepadapp;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.zemris.java.hw11.jnotepadapp.models.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadapp.models.SingleDocumentModel;

/**
 * Default implementation of {@link SingleDocumentModel}.
 * Contains path of document and editor in which document is currently shown.
 * @author Alex
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * Path of document.
	 */
	private Path filepath;
	
	/**
	 * True if document has been modified.
	 */
	private boolean modified;
	
	/**
	 * Text area in which document is edited.
	 */
	private JTextArea editor;
	
	/**
	 * List of listeners.
	 */
	private List<SingleDocumentListener> listeners;
	
	
	/**
	 * Constuctor of {@link SingleDocumentModel}.
	 * @param filepath path of document.
	 * @param text text of document.
	 */
	public DefaultSingleDocumentModel(Path filepath, String text) {
		super();
		this.filepath = filepath;
		this.editor = new JTextArea(text);
		this.editor.setName(filepath == null ? "untitled" : filepath.toString());
		editor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);;
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);;
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);;
			}
			
		});
		this.listeners = new LinkedList<>();
	}

	@Override
	public JTextArea getTextComponent() {
		return editor;
	}

	@Override
	public Path getFilePath() {
		return filepath;
	}

	@Override
	public void setFilePath(Path path) {
		this.filepath = path;
		notifyPathListeners();
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		notifyModificationListeners();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		Objects.requireNonNull(l);
		if(!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		if(listeners.contains(l)) {
			listeners.remove(l);
		}
	}
	
	/**
	 * Notifies listeners that path has been modified.
	 */
	private void notifyPathListeners() {
		for(SingleDocumentListener l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}
	
	/**
	 * Notifies listeners that text has been modified.
	 */
	private void notifyModificationListeners() {
		for(SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}

}
