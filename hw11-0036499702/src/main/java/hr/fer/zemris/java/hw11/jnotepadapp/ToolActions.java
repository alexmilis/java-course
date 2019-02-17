package hr.fer.zemris.java.hw11.jnotepadapp;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadapp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableJMenu;
import hr.fer.zemris.java.hw11.jnotepadapp.models.MultipleDocumentModel;

/**
 * Class used for initialization of tool actions: 
 * 		uppercase, lowercase, invert case, unique, sort ascending, sort descending
 * It adds actions to menubar and provides localization.
 * 
 * @author Alex
 *
 */
public class ToolActions {
	
	/**
	 * Provider for localization.
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * Model in which documents are stored.
	 */
	private MultipleDocumentModel model;
	
	/**
	 * Reference to JNotepadPP.
	 */
	private JNotepadPP notepad;
	
	/**
	 * Clipboard for storing text. 
	 */
	private String clipboard;
	
	/**
	 * Map in which actions are stored.
	 * 		(key, value) = (action name, action)
	 */
	private Map<String, Action> actions = new HashMap<>();

	/**
	 * Constructor of ToolActions.
	 * @param flp	localization provider
	 * @param model	model that stores documents
	 * @param notepad	reference to notepad
	 */
	public ToolActions(FormLocalizationProvider flp, MultipleDocumentModel model, JNotepadPP notepad) {
		this.flp = flp;
		this.model = model;
		this.notepad = notepad;
		
		createActions();
		createMenus();
	}

	/**
	 * Creates actions, their accelerator and mnemonic keys, and puts actions in map.
	 */
	private void createActions() {

		Action uppercase = new LocalizableAction("uppercase", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				executeCase(String::toUpperCase);
			}
		};
		uppercase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		uppercase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		actions.put("uppercase", uppercase);
		
		
		Action lowercase = new LocalizableAction("lowercase", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				executeCase(String::toLowerCase);
			}
		};
		lowercase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		lowercase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		actions.put("lowercase", lowercase);
		
		
		Action invertcase = new LocalizableAction("invertcase", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Document doc = model.getCurrentDocument().getTextComponent().getDocument();
				int offset = getSelectedText();
				try {
					doc.remove(offset, clipboard.length());
					doc.insertString(offset, changeCase(clipboard), null);
				} catch (BadLocationException e1) {
					JOptionPane.showMessageDialog(notepad, "Failed execution", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		invertcase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		invertcase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		actions.put("invertcase", invertcase);
		
		
		Action ascending = new LocalizableAction("ascending", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				executeSort(true);
			}
		};
		ascending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control G"));
		ascending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
		actions.put("ascending", ascending);
		
		
		Action descending = new LocalizableAction("descending", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				executeSort(false);
			}
		};
		descending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		descending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		actions.put("descending", descending);
	
		
		Action unique = new LocalizableAction("unique", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea editor = model.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();
				int len = Math.abs(
						editor.getCaret().getDot() - editor.getCaret().getMark());
				
				if(len == 0) {
					return;
				}
				
				int offset = Math.min(
						editor.getCaret().getDot(), 
						editor.getCaret().getMark());
				
				try {
					int startLine = editor.getLineOfOffset(offset);
					int endLine = editor.getLineOfOffset(offset + len);
					
					offset = editor.getLineStartOffset(startLine);
					len = editor.getLineEndOffset(endLine);
					
					clipboard = doc.getText(offset, len - offset);
					
					doc.remove(offset, clipboard.length());

					String[] lines = clipboard.split("\n");
					Set<String> set = new LinkedHashSet<>();
					offset = editor.getLineStartOffset(startLine);

					for(int i = 0; i <= endLine - startLine; i++) {
						if(set.add(lines[i])) {
							doc.insertString(offset, lines[i] + "\n", null);
							offset += lines[i].length() + 1;
						}
					}
					
				} catch (BadLocationException e1) {
					JOptionPane.showMessageDialog(notepad, flp.getString("error"), flp.getString("error"), JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		unique.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		unique.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
		actions.put("unique", unique);
	}
	
	
	/**
	 * Creates menus for tool actions.
	 */
	private void createMenus() {
		JMenuBar menubar = notepad.getMenu();
		
		JMenu menuTools = new LocalizableJMenu("tools", flp);
		menubar.add(menuTools);
		
		JMenu menuCase = new LocalizableJMenu("changecase", flp);
		menuTools.add(menuCase);
		
		menuCase.add(new JMenuItem(get("uppercase")));
		menuCase.add(new JMenuItem(get("lowercase")));
		menuCase.add(new JMenuItem(get("invertcase")));
		
		JMenu sortMenu = new LocalizableJMenu("sort", flp);
		menuTools.add(sortMenu);
		
		sortMenu.add(new JMenuItem(get("ascending")));
		sortMenu.add(new JMenuItem(get("descending")));
		
		menuTools.addSeparator();
		menuTools.add(new JMenuItem(get("unique")));
	}
	
	
	/**
	 * Method that switches case of characters depending on argument function.
	 * @param f function used to switch characters.
	 */
	private void executeCase(Function<String, String> f) {
		Document doc = model.getCurrentDocument().getTextComponent().getDocument();
		int offset = getSelectedText();
		try {
			doc.remove(offset, clipboard.length());
			doc.insertString(offset, f.apply(clipboard), null);
		} catch (BadLocationException e1) {
			JOptionPane.showMessageDialog(notepad, "Failed execution", "Error", JOptionPane.ERROR_MESSAGE);
		}
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
	 * Inverts case of characters.
	 * @param text part of text to be inverted.
	 * @return text with inverted cases.
	 */
	private String changeCase(String text) {
		char[] chars = text.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if(Character.isLowerCase(c)) {
				chars[i] = Character.toUpperCase(c);
			} else if(Character.isUpperCase(c)) {
				chars[i] = Character.toLowerCase(c);
			}
		}
			
		return new String(chars);
	}
	
	/**
	 * Help method that sorts lines.
	 * @param ascending true -> sort is ascending
	 * 					false -> sort is descending
	 */
	private void executeSort(boolean ascending) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();
		int len = Math.abs(
				editor.getCaret().getDot() - editor.getCaret().getMark());
		
		if(len == 0) {
			return;
		}
		
		int offset = Math.min(
				editor.getCaret().getDot(), 
				editor.getCaret().getMark());
		
		try {
			int startLine = editor.getLineOfOffset(offset);
			int endLine = editor.getLineOfOffset(offset + len);
			
			offset = editor.getLineStartOffset(startLine);
			len = editor.getLineEndOffset(endLine);
			
			clipboard = doc.getText(offset, len - offset);
			
			doc.remove(offset, clipboard.length());

			String[] lines = clipboard.split("\n");
			
			Arrays.sort(lines, Collator.getInstance(Locale.forLanguageTag(flp.getCurrentLanguage())));

			if(!ascending) {
				List<String> list = Arrays.asList(lines);
				Collections.reverse(list);
				lines = list.toArray(new String[0]);
			}
			
			offset = editor.getLineStartOffset(startLine);

			for(int i = 0; i <= endLine - startLine; i++) {
				doc.insertString(offset, lines[i] + "\n", null);
				offset += lines[i].length() + 1;
			}
			
		} catch (BadLocationException e1) {
			JOptionPane.showMessageDialog(notepad, flp.getString("error"), flp.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Gets action mapped under key name.
	 * @param name	key under which action is mapped
	 * @return	action
	 */
	public Action get(String name) {
		return actions.get(name);
	}
	
	/**
	 * Enables or disables actions depending on variable enabled.
	 * @param enabled	if true -> actions are enabled
	 * 					if false -> actions are disabled
	 */
	public void setAll(boolean enabled) {
		actions.forEach((k, v) -> v.setEnabled(enabled));
	}

}
