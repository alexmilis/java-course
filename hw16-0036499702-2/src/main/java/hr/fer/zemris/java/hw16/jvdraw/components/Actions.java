package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.object.Circle;
import hr.fer.zemris.java.hw16.jvdraw.object.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.object.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.object.Line;
import hr.fer.zemris.java.hw16.jvdraw.object.visitor.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.object.visitor.GeometricalObjectPainter;

/**
 * Class that creates and stores all actions used in application {@link JVDraw}.
 * @author Alex
 *
 */
public class Actions {
	
	/**
	 * Reference to main frame of application.
	 */
	private JVDraw draw;
	
	/**
	 * Model that stores geometrical objects.
	 */
	private DrawingModel model;
	
	/**
	 * Map in which actions are stored.
	 * 			(key, value) = (action name, action)
	 */
	private Map<String, Action> actions = new HashMap<>();

	/**
	 * Constructor of Actions. It also creates menu that is added to {@link JVDraw}.
	 * @param draw
	 * 				reference to main frame of application
	 * @param model
	 * 				model that stores geometrical objects
	 */
	public Actions(JVDraw draw, DrawingModel model) {
		super();
		this.draw = draw;
		this.model = model;
		
		createActions();
		createMenuBar();
		
		draw.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				actions.get("exit").actionPerformed(new ActionEvent(draw, 0, null));
			}
		});
		
	}

	/**
	 * Creates all actions.
	 */
	private void createActions() {
		
		Action open = new AbstractAction("open") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = createFileChooser("Open file", new String[]{"jvd"});
				if(fc.showOpenDialog(draw) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				
				Path filepath = fc.getSelectedFile().toPath();
				
				try {
					List<String> lines = Files.readAllLines(filepath);
					lines.forEach(line -> {
						String[] parts = line.split(" ");
						model.add(readObject(parts[0], getArgs(parts)));
					});
				} catch (IOException|RuntimeException ex) {
					JOptionPane.showMessageDialog(
							draw, "Cannot open file " + filepath + ", " + ex.getMessage(), "Invalid file", 0);
				}
			}
		};
		open.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		open.putValue(Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_O);
		actions.put("open", open);
		
		Action saveas = new AbstractAction("saveas") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = createFileChooser("Save file as", new String[]{"jvd"});
				if(fc.showOpenDialog(draw) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				
				Path filepath = Paths.get(fc.getSelectedFile().toPath() + ".jvd");
				draw.setPath(filepath);
				save();
			}
		};
		saveas.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveas.putValue(Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_A);
		actions.put("saveas", saveas);
		
		Action save = new AbstractAction("save") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Path filepath = draw.getPath();
				if(filepath == null) {
					saveas.actionPerformed(e);
				} else {
					save();					
				}
			}
		};
		save.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		save.putValue(Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_S);
		actions.put("save", save);
		
		Action export = new AbstractAction("export") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {				
				JFileChooser fc = createFileChooser("Export file", new String[]{"jpg", "png", "gif"});
				if(fc.showOpenDialog(draw) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				
				String extension = fc.getFileFilter().getDescription();  
				Path filepath = fc.getSelectedFile().toPath();				
				if (!filepath.endsWith(extension)) {
					filepath = Paths.get(filepath.toString() + "." + extension);
				}
				
				export(filepath, extension);
			}			
		};
		export.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		export.putValue(Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_E);
		actions.put("export", export);
		
		Action exit = new AbstractAction("exit") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {					
				if(draw.getModified()) {
					Object value = JOptionPane.showConfirmDialog(
							draw, "Save file before exit?", "Save modified file", JOptionPane.YES_NO_CANCEL_OPTION);
					if(value.equals(JOptionPane.CANCEL_OPTION)) {
						return;
					} else if(value.equals(JOptionPane.YES_OPTION)) {
						save.actionPerformed(e);					
					}
				}
				draw.dispose();
			}

			
		};
		exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		exit.putValue(Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_X);
		actions.put("exit", exit);
	}
	
	/**
	 * Exports drawn image to one of following formats: jpg, png, gif.
	 * @param filepath
	 * 				path to file in which image should be exported
	 * @param extension
	 * 				selected extension of file
	 */
	private void export(Path filepath, String extension) {
		GeometricalObjectBBCalculator calculator = new GeometricalObjectBBCalculator();
		for(int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(calculator);
		}
		
		Rectangle box = calculator.getBoundingBox();
		BufferedImage image = new BufferedImage(
				 box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g = image.createGraphics();
		g.translate(-box.x, -box.y);
		
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
		for(int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(painter);
		}
				
		g.dispose();
		File file = filepath.toFile();
		try {
			ImageIO.write(image, extension, file);
			JOptionPane.showMessageDialog(
					draw, "Export successful!", "Export success", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					draw, "An error occured during export!", "Export failed", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	/**
	 * Saves drawn document to file to which {@link JVDraw} current path variable points to.
	 * Those files have extension jvd and can later be opened in this application.
	 */
	private void save() {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(draw.getPath().toFile())));
	 
			for (int i = 0; i < model.getSize(); i++) {
				bw.write(model.getObject(i).getSaveLine());
				bw.newLine();
				bw.flush();
			}
		 
			bw.close();
			draw.setModified(false);
			JOptionPane.showMessageDialog(
					draw, "Successfully saved to file " + draw.getPath(), "Save info", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(
					draw, "Cannot save to file " + draw.getPath(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Parses line from file and returns {@link GeometricalObject} that corresponds. 
	 * If arguments are invalid, it throws an exception.
	 * @param shape
	 * 				string name of geometrical object
	 * @param args
	 * 				int array that contains arguments needed to create geometrical object
	 * @return
	 * 				{@link GeometricalObject}
	 */
	private GeometricalObject readObject(String shape, int[] args) {
		switch(shape) {
		case "LINE":
			return new Line(
				new Point(args[0], args[1]), 
				new Point(args[2], args[3]), 
				new Color(args[4], args[5], args[6]));
		case "CIRCLE":
			return new Circle(
				new Point(args[0], args[1]), 
				args[2], 
				new Color(args[3], args[4], args[5]));
		case "FCIRCLE":
			return new FilledCircle(
				new Point(args[0], args[1]), 
				args[2], 
				new Color(args[3], args[4], args[5]), 
				new Color(args[6], args[7], args[8]));
		default:
			throw new RuntimeException("No such geometrical object exists: " + shape);
		}
	}
	
	/**
	 * Parses string array and transforms it to int array.
	 * @param parts
	 * 				string array
	 * @return
	 * 				int array
	 */
	private int[] getArgs(String[] parts) {
		int size = parts.length - 1;
		int[] args = new int[size];
		for(int i = 0; i < size; i++) {
			args[i] = Integer.parseInt(parts[i + 1]);
		}
		return args;
	}

	/**
	 * Creates file chooser with given title and file filters.
	 * @param title
	 * 				title of dialog
	 * @param filters
	 * 				string array of file filters
	 * @return
	 * 				file chooser with set title and file filters
	 */
	private JFileChooser createFileChooser(String title, String[] filters) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(title);
		for (String filter : filters) {
			fc.setFileFilter(new FileNameExtensionFilter(filter, filter));
		}
		return fc;
	}
	
	/**
	 * Creates menubar with actions.
	 * @return
	 * 				menubar with actions
	 */
	public JMenuBar createMenuBar() {
		JMenuBar menubar = new JMenuBar();
		
		JMenu menu = new JMenu("File");
		
		menu.add(new JMenuItem(actions.get("open")));
		menu.add(new JMenuItem(actions.get("save")));
		menu.add(new JMenuItem(actions.get("saveas")));
		
		menu.addSeparator();
		menu.add(new JMenuItem(actions.get("export")));
		menu.add(new JMenuItem(actions.get("exit")));
		
		menubar.add(menu);	
		return menubar;
	}
}
