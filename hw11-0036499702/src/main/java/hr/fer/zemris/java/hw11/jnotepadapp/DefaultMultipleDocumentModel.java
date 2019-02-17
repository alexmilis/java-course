package hr.fer.zemris.java.hw11.jnotepadapp;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadapp.models.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadapp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadapp.models.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadapp.models.SingleDocumentModel;

/**
 * Default implementation of {@link MultipleDocumentModel}. 
 * It is able to store multiple {@link SingleDocumentModel}.
 * It is used as workspace for {@link JNotepadPP}.
 * Each document has its own tab. Each tab has a name and an icon.
 * Unmodified document has green icon, if it is modified, icon changes to red.
 * @author Alex
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel, ChangeListener {
	
	/**
	 * Default name given to new documents.
	 */
	public static final String NEWFILE = "untitled";
	
	/**
	 * Counter for untitled documents opened in this session.
	 */
	private int noOfUntitled;
	
	/**
	 * Green icon.
	 */
	private ImageIcon green;
	
	/**
	 * Red icon.
	 */
	private ImageIcon red;
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * List of documents currently stored in this model.
	 */
	private List<SingleDocumentModel> documents;
	
	/**
	 * Currently active document.
	 */
	private SingleDocumentModel current;
	
	/**
	 * List of listeners.
	 */
	private List<MultipleDocumentListener> listeners;
		
	
	/**
	 * Constructor of {@link DefaultMultipleDocumentModel}.
	 */
	public DefaultMultipleDocumentModel() {
		super();
		this.documents = new ArrayList<>();
		this.listeners = new LinkedList<>();
		this.noOfUntitled = 0;
		this.green = getIcon(false);
		this.red = getIcon(true);
		this.addChangeListener(this);
		initGUI();
	}
	
	/**
	 * Initializes GUI of this class.
	 */
	private void initGUI() {
		addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(!documents.isEmpty()) {
					current = documents.get(getSelectedIndex());
				}
			}
		});
	}

	
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	
	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDoc = new DefaultSingleDocumentModel(null, "");
		addNewTab(newDoc);
		setSelectedIndex(documents.size() - 1);
		return newDoc;
	}

	
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if(!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(this, "An error occured while reading from file " + path.toAbsolutePath(), 
					"Error", JOptionPane.ERROR_MESSAGE);
			throw new JNotepadPPException();
		}
		
		int index = hasPath(path);
		if(index >= 0) {
			setCurrent(documents.get(index));
			setSelectedIndex(index);
			return current;
		}
		
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "An error occured while reading from file " + path.toAbsolutePath(), 
					"Error", JOptionPane.ERROR_MESSAGE);
			throw new JNotepadPPException();
		}
		
		String text = new String(bytes, StandardCharsets.UTF_8);
		SingleDocumentModel doc = new DefaultSingleDocumentModel(path, text);
		
		addNewTab(doc);		
		return doc;
	}

	
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if(newPath == null) {
			newPath = getPath(model);
			if(newPath == null) {
				throw new JNotepadPPException("Cancel");
			}
		}
		while(Files.exists(newPath) && newPath != model.getFilePath()) {
			Object value = JOptionPane.showConfirmDialog(model.getTextComponent(), "Overwrite file " + newPath.getFileName().toString());
			if(value.equals(JOptionPane.NO_OPTION)) {
				newPath = getPath(model);
				if(newPath == null) {
					throw new JNotepadPPException("Cancel");
					//TODO
				}
			} else if (value.equals(JOptionPane.CANCEL_OPTION)) {
				return;
			} else {
				if(Files.isWritable(newPath)) {
					break;
				}
			}
		} 
		
		model.setFilePath(newPath);
		try {
			Files.newOutputStream(
					newPath, StandardOpenOption.CREATE)
					.write(model.getTextComponent().getText().getBytes());
		} catch (IOException e) {
			throw new JNotepadPPException("An error occured while saving file " + newPath);
		}
		
		model.setModified(false);
	}

	
	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		documents.remove(model);
		remove(index);
		
		if(!documents.isEmpty()) {
			setCurrent(documents.get(documents.size() - 1));
			setSelectedIndex(documents.size() - 1);
		} else {
			setCurrent(null);
		}
	}

	
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		Objects.requireNonNull(l);
		if(!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	
	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		if(listeners.contains(l)) {
			listeners.remove(l);
		}
	}

	
	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}
	
	
	@Override
	public SingleDocumentModel getDocument(int index) {
		if(index > documents.size() - 1) {
			throw new JNotepadPPException("Document with index " + index + " does not exist.");
		}
		return documents.get(index);
	}
	

	/**
	 * Sets given document as current document and notifies listeners.
	 * @param model {@link SingleDocumentModel}
	 */
	private void setCurrent(SingleDocumentModel model) {
		if(model == null) {
			return;
		}
		
		SingleDocumentModel c = current;
		current = model;
		listeners.forEach(l -> l.currentDocumentChanged(c, model));
	}
	
	/**
	 * Adds new tab to this component.
	 * @param model {@link SingleDocumentModel}
	 */
	private void addNewTab(SingleDocumentModel model) {		
		model.addSingleDocumentListener(new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setIconAt(getSelectedIndex(), model.isModified() ? red : green);
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
			}
		});
		
		String filename = model.getFilePath() == null ? 
					NEWFILE + noOfUntitled++ : 
					model.getFilePath().getFileName().toString();
		
		addTab(filename, green, new JScrollPane(model.getTextComponent()));
		documents.add(model);
		setCurrent(model);
		setSelectedIndex(documents.indexOf(model));
	}
	
	
	/**
	 * Requests path to which file should be saved until user gives a valid path.
	 * @param model {@link SingleDocumentModel}
	 * @return valid path
	 */
	private Path getPath(SingleDocumentModel model) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save file as");
		fc.setApproveButtonText("Save");
		if(fc.showOpenDialog(model.getTextComponent()) != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		
		return fc.getSelectedFile().toPath();
	}
	
	/**
	 * Checks if given path already exists in stored documents.
	 * @param path
	 * @return true if path already exists
	 */
	private int hasPath(Path path) {
		int index = -1;
		if(path == null) {
			return index;
		}
		for(int i = 0; i < documents.size(); i++) {
			if(documents.get(i).getFilePath() != null) {
				if(documents.get(i).getFilePath().equals(path)) {
					index = i;
					break;
				}
			}
		}
		return index;
	}

	/**
	 * Function used in initialization of this class to load icons.
	 * @param modified boolean true -> red icon, false -> green icon
	 * @return image icon
	 */
	private ImageIcon getIcon(boolean modified) {
		String name = "icons";
		name += modified ? "/red.png" : "/green.png";	
		try (InputStream in = DefaultMultipleDocumentModel.class.getResourceAsStream(name);){
			if(in == null) {
				throw new JNotepadPPException("Unable to load icon! In is null");
			}
			byte[] bytes = in.readAllBytes();
			
			return new ImageIcon(new ImageIcon(bytes).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		} catch (IOException e) {
			throw new JNotepadPPException("Unable to load icon!");
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		try {
			setCurrent(getDocument(getSelectedIndex()));
			String tooltip = current.getFilePath() == null ? "untitled" : current.getFilePath().getFileName().toString();
			setToolTipTextAt(getSelectedIndex(), tooltip);
		} catch (JNotepadPPException|IndexOutOfBoundsException ignorable) {
		}
	}
}
