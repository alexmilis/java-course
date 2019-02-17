package hr.fer.zemris.java.hw11.jnotepadapp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadapp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableJMenu;
import hr.fer.zemris.java.hw11.jnotepadapp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadapp.models.SingleDocumentModel;

/**
 * Class used for initialization of basic actions: 
 * 		create, close, copy, cut, exit, info, load, paste, save, saveAs.
 * It adds actions to menubar and floating toolbar. It also provides localization.
 * 
 * @author Alex
 *
 */
public class BasicActions {
	
	/**
	 * Localization provider.
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * Model used to store documents.
	 */
	private MultipleDocumentModel model;
	
	/**
	 * Reference to JNotepadPP.
	 */
	private JNotepadPP notepad;
	
	/**
	 * Clipboard for functions copy/cut/paste. 
	 */
	private String clipboard;
	
	/**
	 * Map used for storing actions.
	 * 		(key, value) = (action name, action)
	 */
	private Map<String, Action> actions = new HashMap<>();
	
	
	/**
	 * Constructor that initializes basic actions.
	 * @param flp	localization provider.
	 * @param model	model that stores documents.
	 * @param notepad	reference to notepad.
	 */
	public BasicActions(FormLocalizationProvider flp, MultipleDocumentModel model, JNotepadPP notepad) {
		this.flp = flp;
		this.model = model;
		this.notepad = notepad;
		
		createActions();
		createMenus();
		createToolbars(notepad.getPanel());
	}
	
	
	/**
	 * Creates actions and sets their accelerator and mnemonic keys. It puts all actions into map.
	 */
	private void createActions() {
		
		Action create = new LocalizableAction("create", flp) {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				model.createNewDocument();
			}
		};
		create.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		create.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		actions.put("create", create);
		
		
		Action close = new LocalizableAction("close", flp) {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getCurrentDocument() == null) {
					notepad.showWarning(flp.getString("noDocSelected"));
					return;
				}
				
				model.closeDocument(model.getCurrentDocument());
			}
		};
		close.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		close.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		actions.put("close", close);
		
	
		Action copy = new LocalizableAction("copy", flp) {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				textToClipboard(false);
			}
		};
		copy.setEnabled(false);
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		actions.put("copy", copy);
		
		
		Action cut = new LocalizableAction("cut", flp) {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				textToClipboard(true);
			}
		};
		cut.setEnabled(false);
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		actions.put("cut", cut);
		
		
		Action exit = new LocalizableAction("exit", flp) {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0, imax = model.getNumberOfDocuments(); i < imax; i++) {
					if(exitClose()) {
						return;
					}
				}
				notepad.stopJNotepadPP();
			}
		};
		exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		actions.put("exit", exit);
		
		
		Action info = new LocalizableAction("info", flp) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getCurrentDocument() == null) {
					notepad.showWarning("No document available for operation info!");
					return;
				}
				String text = model.getCurrentDocument().getTextComponent().getText();
				StringBuilder sb = new StringBuilder();
				
				
				String name = "untitled";
				if(model.getCurrentDocument().getFilePath() != null) {
					name = model.getCurrentDocument().getFilePath().getFileName().toString();
				}
				sb.append(flp.getString("info1")).append(name).append(" ")
				.append(flp.getString("info2")).append(text.length()).append(" " + flp.getString("info3"))
				.append(text.replaceAll("\\s", "").length()).append(" " + flp.getString("info4"))
				.append(text.split("\n").length).append(" " + flp.getString("info5"));
				
				JOptionPane.showMessageDialog(notepad, sb.toString(), flp.getString("info6"), JOptionPane.INFORMATION_MESSAGE);
				
			}
		};
		info.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		info.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		actions.put("info", info);
		
		
		Action load = new LocalizableAction("load", flp) {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Open file");
				if(fc.showOpenDialog(notepad) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				
				Path filepath = fc.getSelectedFile().toPath();
				model.loadDocument(filepath);
			}
		};
		load.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		load.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		actions.put("load", load);
		
		
		Action paste = new LocalizableAction("paste", flp) {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				int position = model.getCurrentDocument().getTextComponent().getCaretPosition();
				try {
					model.getCurrentDocument().getTextComponent().getDocument().insertString(position, clipboard, null);
				} catch (BadLocationException e1) {
					JOptionPane.showMessageDialog(notepad, "Invalid location for paste", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		};
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		actions.put("paste", paste);
		
		
		Action save = new LocalizableAction("save", flp) {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				SingleDocumentModel current = model.getCurrentDocument();
				if(current == null) {
					notepad.showWarning(flp.getString("noDocSelected"));
					return;
				} if(current.getFilePath() == null) {
					get("saveAs").actionPerformed(e);
				} else {
					model.saveDocument(current, current.getFilePath());
				}
			}
		};
		save.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		save.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		actions.put("save", save);
		
		
		Action saveAs = new LocalizableAction("saveas", flp) {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getCurrentDocument() == null) {
					notepad.showWarning(flp.getString("noDocSelected"));
					return;
				}
				
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle(flp.getString("saveasFCTitle"));
				fc.setApproveButtonText("save");
				if(fc.showOpenDialog(notepad) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				
				Path filepath = fc.getSelectedFile().toPath();
				model.saveDocument(model.getCurrentDocument(), filepath);
			}
		};
		saveAs.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveAs.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		actions.put("saveAs", saveAs);
	}
	
	/**
	 * Creates menus for basic actions.
	 */
	private void createMenus() {
		JMenuBar menubar = notepad.getMenu();
		
		JMenu menuFile = new LocalizableJMenu("file", flp);
		menubar.add(menuFile);
		
		menuFile.add(new JMenuItem(get("close")));
		menuFile.add(new JMenuItem(get("create")));
		menuFile.add(new JMenuItem(get("exit")));
		menuFile.add(new JMenuItem(get("info")));
		menuFile.add(new JMenuItem(get("load")));
		menuFile.add(new JMenuItem(get("save")));
		menuFile.add(new JMenuItem(get("saveAs")));
		
		JMenu menuEdit = new LocalizableJMenu("edit", flp);
		menubar.add(menuEdit);
		
		menuEdit.add(new JMenuItem(get("copy")));
		menuEdit.add(new JMenuItem(get("cut")));
		menuEdit.add(new JMenuItem(get("paste")));
	}

	/**
	 * Creates floatable toolbar.
	 * @param panel panel to which toolbar should be added.
	 */
	private void createToolbars(JPanel panel) {
		JToolBar toolbar = new JToolBar("Options");
		toolbar.setFloatable(true);
		
		toolbar.add(new JButton(get("create")));
		toolbar.add(new JButton(get("exit")));
		toolbar.add(new JButton(get("load")));
		toolbar.add(new JButton(get("save")));
		toolbar.add(new JButton(get("saveAs")));
		toolbar.add(new JButton(get("close")));
		toolbar.addSeparator();
		
		toolbar.add(new JButton(get("copy")));
		toolbar.add(new JButton(get("cut")));
		toolbar.add(new JButton(get("paste")));
		toolbar.addSeparator();
		
		toolbar.add(new JButton(get("info")));
		
		panel.add(toolbar, BorderLayout.PAGE_START);
		
	}
		
	/**
	 * Help method that closes and saves (if needed) a document.
	 * @return true if closing has been canceled.
	 */
	private boolean exitClose() {
		if(model.getCurrentDocument().isModified()) {
			Object value = JOptionPane.showConfirmDialog(notepad, flp.getString("exitClose1"), 
					flp.getString("exitClose2"), JOptionPane.YES_NO_CANCEL_OPTION);
			if(value.equals(JOptionPane.CANCEL_OPTION)) {
				return true;
			} else if(value.equals(JOptionPane.YES_OPTION)) {
				try {
					model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
				} catch(JNotepadPPException ex) {
					return true;
				}
			}
		}
		
		model.closeDocument(model.getCurrentDocument());
		return false;
	}
		
		
	/**
	 * Gets selected text and stores it to clipboard.
	 * @param tools if true, text is stored to tools clipboard.
	 * @return offset of selected text
	 */
	private int getSelectedText() {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();
		int len = Math.abs(
				editor.getCaret().getDot() - editor.getCaret().getMark());
		if(len == 0) {
			return -1;
		}
		int offset = Math.min(
				editor.getCaret().getDot(), 
				editor.getCaret().getMark());
		try {
			clipboard = doc.getText(offset, len);
			return offset;
		} catch (BadLocationException e) {
			JOptionPane.showMessageDialog(notepad, "Invalid selection", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return -1;
	}
	
	/**
	 * Gets selected text, stores it to clipboard, and if action was cut, removes text from editor.
	 * @param cut if true text is removed from editor
	 */
	private void textToClipboard(boolean cut) {
		Document doc = model.getCurrentDocument().getTextComponent().getDocument();

		int offset = getSelectedText();

		if(cut) {
			try {
				doc.remove(offset, clipboard.length());
			} catch (BadLocationException e) {
				JOptionPane.showMessageDialog(notepad, "Invalid selection", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Getter for actions.
	 * @param name	name of action to get.
	 * @return	action
	 */
	public Action get(String name) {
		return actions.get(name);
	}
}
