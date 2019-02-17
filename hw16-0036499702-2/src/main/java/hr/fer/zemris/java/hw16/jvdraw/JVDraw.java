package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.nio.file.Path;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.MouseInputAdapter;

import hr.fer.zemris.java.hw16.jvdraw.color.ColorBar;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.components.Actions;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingObjectsListModel;
import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.object.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.tool.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tool.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tool.LineTool;
import hr.fer.zemris.java.hw16.jvdraw.tool.Tool;

/**
 * Simple application similar to paint. 
 * It offers possibility to draw line, circle and filled circle.
 * User picks one of shapes he/she wishes to draw. 
 * First mouse click determines start of line or center of circle.
 * Second mouse click determines end of line or length of radius 
 * (if shape is some kind of circle).
 * 
 * Outlines are drawn with foreground color, and fill is made with background color.
 * Both colors can be chosen by clicking on color chooser icons in toolbar.
 * 
 * @author Alex
 *
 */
/**
 * @author Alex
 *
 */
public class JVDraw extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	/**
	 * State - currently selected drawing tool.
	 */
	private Tool tool;
	
	/**
	 * Contains all actions needed to run this application. It also creates menu and toolbar.
	 */
	private Actions actions;
	
	/**
	 * True if document has been modified since it was last saved.
	 */
	private boolean modified;
	
	/**
	 * 
	 */
	private Path path;

	/**
	 * Constructor.
	 */
	public JVDraw() {		
		initGUI();
	}

	/**
	 * Initializes graphical user interface. Adds actions and listeners where needed.
	 */
	private void initGUI() {
		
		setTitle("JVDraw");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.getContentPane().setLayout(new BorderLayout());
		
		Color foreground = Color.GREEN;
		Color background = Color.YELLOW;
		
		JColorArea fgColorArea = new JColorArea(foreground);
		JColorArea bgColorArea = new JColorArea(background);
		
		ColorBar bar = new ColorBar(fgColorArea, bgColorArea);
		add(bar, BorderLayout.PAGE_END);
		
		JPanel panel = new JPanel(new BorderLayout());
		
		DrawingModel model = new DrawingModelImpl();
		JDrawingCanvas canvas = createCanvas(model);
		panel.add(canvas, BorderLayout.CENTER);
		
		ActionListener action = createToolAction(fgColorArea, bgColorArea, model, canvas);
		createToolbar(fgColorArea, bgColorArea, action, panel);
		
		actions = new Actions(this, model);
		add(actions.createMenuBar(), BorderLayout.PAGE_START);
		
		DrawingObjectsListModel listModel = createListModel(model, canvas);
		JList<GeometricalObject> jlist = createJList(model, listModel);
		
		JScrollPane listScroller = new JScrollPane(jlist);
		panel.add(listScroller, BorderLayout.LINE_END);
		
		getContentPane().add(panel, BorderLayout.CENTER);
		setSize(700, 500);
	}

	private JList<GeometricalObject> createJList(DrawingModel model, DrawingObjectsListModel listModel) {
		JList<GeometricalObject> jlist = new JList<GeometricalObject>(listModel);
		jlist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					GeometricalObject object = jlist.getSelectedValue();
					GeometricalObjectEditor editor = object.createGeometricalObjectEditor();
					
					int response = JOptionPane.showConfirmDialog(JVDraw.this, editor);
					
					if(response == JOptionPane.YES_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
							setModified(true);							
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(JVDraw.this, "Invalid edit! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		
		jlist.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_DELETE:
					model.remove(jlist.getSelectedValue());
					break;
				case KeyEvent.VK_PLUS:
					model.changeOrder(jlist.getSelectedValue(), 1);
					break;
				case KeyEvent.VK_MINUS:
					model.changeOrder(jlist.getSelectedValue(), -1);
					break;
				default:
					break;
				}
				modified = true;
			}
		});
		
		return jlist;
	}

	private DrawingObjectsListModel createListModel(DrawingModel model, JDrawingCanvas canvas) {
		DrawingObjectsListModel listModel = new DrawingObjectsListModel(model);
		listModel.addListDataListener(new ListDataListener() {
			
			@Override
			public void intervalRemoved(ListDataEvent e) {
				canvas.repaint();
			}
			
			@Override
			public void intervalAdded(ListDataEvent e) {
				canvas.repaint();
			}
			
			@Override
			public void contentsChanged(ListDataEvent e) {
				canvas.repaint();
			}
		});
		return listModel;
	}

	/**
	 * Creates actions for toggle button used to select tool.
	 * @param fgColorArea
	 * 				foreground color provider
	 * @param bgColorArea
	 * 				background color provider
	 * @param model
	 * 				model in which geometrical objects are stored
	 * @param canvas
	 * 				jcomponent on which user can draw
	 * @return
	 * 				action that selects tool based on which toggle button was selected
	 */
	private ActionListener createToolAction(JColorArea fgColorArea, JColorArea bgColorArea, DrawingModel model,
			JDrawingCanvas canvas) {
		return a -> {
			modified = true;
			switch(((JToggleButton) a.getSource()).getText()) {
			case "Line":
				tool = new LineTool(fgColorArea, model, canvas);
				break;
			case "Circle":
				tool = new CircleTool(fgColorArea, model, canvas);
				break;
			case "Filled circle":
				tool = new FilledCircleTool(fgColorArea, bgColorArea, model, canvas);
				break;
			default:
				break;
			}
		};
	}

	/**
	 * Creates drawing canvas and adds needed listeners.
	 * @param model
	 * 				model in which geometrical objects are stored 
	 * @return
	 * 				{@link JDrawingCanvas} used for drawing objects
	 */
	private JDrawingCanvas createCanvas(DrawingModel model) {
		JDrawingCanvas canvas = new JDrawingCanvas(this, model);
		
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(tool != null) {
					tool.mouseMoved(e);
				}
			}
		});
		
		canvas.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tool != null) {
					tool.mouseClicked(e);
				} else {
					JOptionPane.showMessageDialog(JVDraw.this, "Select tool!", "No tool selected", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		return canvas;
	}
	
	/**
	 * Creates toolbar that contains two color choosers and 3 shape buttons. 
	 * Shape buttons are mutually exclusive.
	 * @param fgColorArea
	 * 				color chooser for foreground color
	 * @param bgColorArea
	 * 				color chooser for background color
	 * @param action
	 * 				action listener linked to buttons
	 * @param panel 
	 */
	private void createToolbar(JColorArea fgColorArea, JColorArea bgColorArea, ActionListener action, JPanel panel) {
		JToolBar toolbar = new JToolBar("Toolbar");
		toolbar.setFloatable(true);
		
		ButtonGroup group = new ButtonGroup();
		JToggleButton line = new JToggleButton("Line");
		JToggleButton circle = new JToggleButton("Circle");
		JToggleButton filled = new JToggleButton("Filled circle");
		
		line.addActionListener(action);
		circle.addActionListener(action);
		filled.addActionListener(action);
		
		group.add(line);
		group.add(circle);
		group.add(filled);
				
		toolbar.add(fgColorArea);
		toolbar.add(bgColorArea);
		toolbar.addSeparator();
		
		toolbar.add(line);
		toolbar.add(circle);
		toolbar.add(filled);
		
		panel.add(toolbar, BorderLayout.PAGE_START);
	}
	
	
	/**
	 * Getter for path of current document.
	 * @return
	 * 				path to current document if it exists, else <code>null</code>
	 */
	public Path getPath() {
		return path;
	}
	
	/**
	 * Setter for path to current document.
	 * @param path
	 * 				new path to document
	 */
	public void setPath(Path path) {
		this.path = path;
	}
	
	/**
	 * Getter for modified.
	 * @return
	 * 				modified
	 */
	public boolean getModified() {
		return this.modified;
	}
	
	/**
	 * Setter for modified.
	 * @param modified
	 * 				new value of modified
	 */
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
	/**
	 * Getter for currently selected tool.
	 * @return
	 * 				current tool
	 */
	public Tool getTool() {
		return tool;
	}

	/**
	 * Main method that starts the application.
	 * @param args
	 * 				not needed here
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignorable) {}
		
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}
}
