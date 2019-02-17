package hr.fer.zemris.java.hw11.jnotepadapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.zemris.java.hw11.jnotepadapp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableJLabel;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableJMenu;
import hr.fer.zemris.java.hw11.jnotepadapp.models.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadapp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadapp.models.SingleDocumentModel;

/**
 * Simple file editor JNotepad++. It offers additional methods for editing and sorting text. 
 * Supported languages are English, Croatian and German.
 * @author Alex
 *
 */
public class JNotepadPP extends JFrame implements MultipleDocumentListener, CaretListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Model that stores documents.
	 */
	private MultipleDocumentModel model;
	
	/**
	 * Status bar.
	 */
	private StatusBar statusBar;
	
	/**
	 * Provider for localization.
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	/**
	 * Panel for setting toolbar.
	 */
	private JPanel panel;
	
	/**
	 * Instance of class that stores basic actions.
	 */
	private BasicActions basicActions;
	
	/**
	 * Stores tool actions.
	 */
	private ToolActions toolActions;
	
	/**
	 * Menu bar of JNotepadPP.
	 */
	private JMenuBar menubar;		
	
	/**
	 * Constructor.
	 */
	public JNotepadPP() {
		this.model = new DefaultMultipleDocumentModel();
		this.model.addMultipleDocumentListener(this);
		this.statusBar = new StatusBar();

		initGUI();
	}
	
	/**
	 * Initializes GUI of JNotepadPP.
	 */
	private void initGUI() {
		setTitle("JNotepad++");
		
		this.getContentPane().setLayout(new BorderLayout());
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.getContentPane().add(panel, BorderLayout.CENTER);
		panel.add((JTabbedPane) model, BorderLayout.CENTER);
		
		menubar = new JMenuBar();
		
		basicActions = new BasicActions(flp, model, this);
		toolActions = new ToolActions(flp, model, this);
		toolActions.setAll(false);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				basicActions.get("exit").actionPerformed(new ActionEvent(JNotepadPP.this, 0, null));
			}
		});

		this.getContentPane().add(statusBar, BorderLayout.PAGE_END);
		
		createMenus();
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(800, 500);
	}
	

	/**
	 * Creates menus for languages.
	 */
	private void createMenus() {
		
		JMenu langMenu = new LocalizableJMenu("languages", flp);
		menubar.add(langMenu);
		
		langMenu.add(new JMenuItem(croatian));
		langMenu.add(new JMenuItem(english));
		langMenu.add(new JMenuItem(german));
		
		this.setJMenuBar(menubar);
	}
	
	
//-----------------------------LANGUAGES------------------------------------------

	/**
	 * Action that changed language to English.
	 */
	private final Action english = new LocalizableAction("en", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
		
	};
	
	/**
	 * Action that changes language to Croatian.
	 */
	private final Action croatian = new LocalizableAction("hr", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");			
		}
		
	};
	
	/**
	 * Action that changes language to German.
	 */
	private final Action german = new LocalizableAction("de", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");			
		}
		
	};
	

	/**
	 * Shows warning in form of dialog box.
	 * @param text text of warning.
	 */
	public void showWarning(String text) {
		JOptionPane.showMessageDialog(JNotepadPP.this, text, flp.getString("warning"), JOptionPane.WARNING_MESSAGE);
	}

	
	@Override
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		if(currentModel != null) {
			String filename = currentModel.getFilePath() == null ? "untitled" : currentModel.getFilePath().toString();
			this.setTitle(filename + " - JNotepad++");
			currentModel.getTextComponent().getDocument().addDocumentListener(statusBar);
			statusBar.update(currentModel.getTextComponent());
		}
		if(previousModel != null) {
			previousModel.getTextComponent().getDocument().removeDocumentListener(statusBar);
		}
	}
	
	@Override
	public void documentAdded(SingleDocumentModel model) {		
	}
	
	@Override
	public void documentRemoved(SingleDocumentModel model) {
	}
	
	@Override
	public void caretUpdate(CaretEvent e) {
		if(e.getDot() == e.getMark()) {
			basicActions.get("copy").setEnabled(false);
			basicActions.get("cut").setEnabled(false);
			toolActions.setAll(false);
		} else {
			basicActions.get("copy").setEnabled(true);
			basicActions.get("cut").setEnabled(true);
			toolActions.setAll(true);
		}
		
	}

	
	/**
	 * Status bar of this editor. Shows length of document, position of caret, 
	 * number of selected characters, date and time.
	 * @author Alex
	 *
	 */
	private class StatusBar extends JPanel implements DocumentListener, ChangeListener{
		
		/**
		 * Length label that changes its text with language.
		 */
		private LocalizableJLabel length;
		
		/**
		 * Label for number of line.
		 */
		private JLabel ln;
		
		/**
		 * Beginning of text on ln label. 
		 */
		private String lnText = "Ln: ";
		
		/**
		 * Label for number of column.
		 */
		private JLabel col;
		
		/**
		 * Beginning of text on col label.
		 */
		private String colText = "Col: ";
		
		/**
		 * Label for number of selected characters.
		 */
		private JLabel sel;
		
		/**
		 * Beginning of text on sel label.
		 */
		private String selText = "Sel: ";
		
		/**
		 * Date label.
		 */
		private JLabel date;
		
		/**
		 * Time label.
		 */
		private JLabel time;
		
		/**
		 * Date formatter: year/month/day.
		 */
		private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		
		/**
		 * Time formatter: hour:min:sec.
		 */
		private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		/**
		 * Currently active editor.
		 */
		private JTextArea editor;

		/**
		 * If true, window is closing and clock thread needs to be terminated.
		 */
		boolean stop;
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructor. Starts thread that refreshes clock.
		 */
		public StatusBar() {
			initsbGUI();
			stop = true;
			
			Thread t = new Thread(()->{
				while(stop) {
					try {
						Thread.sleep(500);
					} catch(Exception ex) {}
					SwingUtilities.invokeLater(()->{
						updateTime();
					});
				}
			});
			t.setDaemon(true);
			t.start();
		}
		
		/**
		 * Initializes GUI of status bar.
		 */
		private void initsbGUI() {
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
			setLayout(new GridLayout(0, 6));
			
			length = new LocalizableJLabel("length", flp);
			ln = new JLabel(lnText + "0");
			col = new JLabel(colText + "0");
			sel = new JLabel(selText + "0");
			
			date = new JLabel(dateFormat.format(LocalDateTime.now()));
			time = new JLabel(timeFormat.format(LocalTime.now()));
			
			add(length);
			add(ln);
			add(col);
			add(sel);
			add(date);
			add(time);
			
			setSize(getPreferredSize());
		}

		/**
		 * Updates time label every second.
		 */
		private void updateTime() {
			time.setText(timeFormat.format(LocalTime.now()));
			time.repaint();
		}
		
		/**
		 * Updates status bar every time current editor is changed.
		 * @param editor new current editor
		 */
		private void update(JTextArea editor) {
			if(this.editor != null) {
				this.editor.getDocument().removeDocumentListener(this);
				this.editor.getCaret().removeChangeListener(this);
				this.editor.removeCaretListener(JNotepadPP.this);
			}
			this.editor = editor;
			editor.getCaret().addChangeListener(this);
			editor.addCaretListener(JNotepadPP.this);
			JNotepadPP.this.setTitle(this.editor.getName());
			updateCaret();
		}
		
		/**
		 * Updates status bar every time position of caret is changed.
		 */
		private void updateCaret() {
			if(editor == null) {
				return;
			}
			length.setText(length.getLabelName() + Integer.toString(editor.getText().length()));

			int lnNumber = editor.getText().substring(0, editor.getCaretPosition()).split("\n").length;
			ln.setText(lnText + Integer.toString(lnNumber));
			
			int colNumber = editor.getCaretPosition() - 
					editor.getText().substring(0, editor.getCaretPosition()).lastIndexOf("\n");
			col.setText(colText + Integer.toString(colNumber));
			
			sel.setText(selText + Integer.toString(Math.abs(
					editor.getCaret().getDot() - editor.getCaret().getMark())));
		}

		/**
		 * Stops clock thread.
		 */
		private void stop() {
			stop = false;
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			update(editor);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			update(editor);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			update(editor);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			updateCaret();
		}
	
	}
	
	/**
	 * Terminates work of JNotepadPP.
	 */
	public void stopJNotepadPP() {
		statusBar.stop();
		dispose();
	}
	
	/**
	 * Main method. Starts {@link JNotepadPP}.
	 * @param args not needed here.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

	/**
	 * Getter for panel.
	 * @return panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Getter for menu bar.
	 * @return menubar
	 */
	public JMenuBar getMenu() {
		return menubar;
	}

}
