package ljir;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Class that creates and stores all actions used in application {@link JVDraw}.
 * 
 * @author Alex
 *
 */
public class Actions {

	/**
	 * Reference to main frame of application.
	 */
	private Prozor prozor;

	private Map<String, Action> actions = new HashMap<>();

	private Map<String, Integer> nameToIndex = new HashMap<>();

	public Actions(Prozor prozor) {
		super();
		this.prozor = prozor;

		createActions();
		createMenuBar();

		prozor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Creates all actions.
	 */
	private void createActions() {

		Action open = new AbstractAction("open") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = createFileChooser("Open file", new String[] { "ttt" });
				if (fc.showOpenDialog(prozor) != JFileChooser.APPROVE_OPTION) {
					return;
				}

				Path filepath = fc.getSelectedFile().toPath();

				if (nameToIndex.containsKey(filepath.getFileName().toString())) {
					prozor.getTabbed().setSelectedIndex(nameToIndex.get(filepath.getFileName().toString()));
					return;
				}

				List<Artikl> artikli = new ArrayList<>();
				DefaultListModel<Artikl> model = new DefaultListModel<>();

				List<Slice> slices = new ArrayList<>();

				try {
					List<String> lines = Files.readAllLines(filepath);
					lines.forEach(line -> {
						if (!line.isEmpty()) {
							String[] parts = line.split("\t");
							Artikl artikl;
							try {
								artikl = parseLine(parts);
								artikli.add(artikl);
								model.addElement(artikl);
								slices.add(new Slice(artikl.getBroj(), artikl.getBoja()));
							} catch (IllegalArgumentException ex2) {
							}
						}
					});
					
					if(artikli.size() == 0) {
						JOptionPane.showMessageDialog(prozor, "Empty file!", "Cannot open file", JOptionPane.ERROR_MESSAGE);
						return;
					}

					JPanel panel = new JPanel();
					panel.setLayout(new BorderLayout());

					DefaultTableModel tableModel = new DefaultTableModel(transformData(artikli),
							new String[] { "Artikl", "Broj", "Boja" });

					JTable table = new JTable(tableModel);
					table.setEnabled(false);
					table.getColumnModel().getColumn(2).setCellRenderer(new ColorColumnCellRenderer());

					JScrollPane scroll = new JScrollPane(table);

					scroll.setPreferredSize(new Dimension(200, 600));
					panel.add(new PieChart(slices), BorderLayout.CENTER);
					panel.add(scroll, BorderLayout.LINE_END);

					prozor.getTabbed().add(filepath.getFileName().toString(), panel);
					nameToIndex.put(filepath.getFileName().toString(), prozor.getTabbed().getTabCount() - 1);
					prozor.getTabbed().setSelectedIndex(nameToIndex.get(filepath.getFileName().toString()));
				} catch (IOException | RuntimeException ex) {
					JOptionPane.showMessageDialog(prozor, "Cannot open file " + filepath + ", " + ex.getMessage(),
							"Invalid file", 0);
				}
			}
		};
		open.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		open.putValue(Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_O);
		actions.put("open", open);

		Action close = new AbstractAction("close current") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int index = prozor.getTabbed().getSelectedIndex();
					String name = prozor.getTabbed().getTitleAt(index);
	
					prozor.getTabbed().remove(nameToIndex.get(name));
					nameToIndex.remove(name);
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(prozor, "Cannot close file" + ex.getMessage(),
							"Cannot close file", 0);
				}
					
			}
		};
		close.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		close.putValue(Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_C);
		actions.put("close", close);
	}

	private JFileChooser createFileChooser(String title, String[] filters) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(title);
		for (String filter : filters) {
			fc.setFileFilter(new FileNameExtensionFilter(filter, filter));
		}
		return fc;
	}

	public JMenuBar createMenuBar() {
		JMenuBar menubar = new JMenuBar();

		JMenu menu = new JMenu("File");

		menu.add(new JMenuItem(actions.get("open")));
		menu.add(new JMenuItem(actions.get("close")));

		menubar.add(menu);
		return menubar;
	}

	private Artikl parseLine(String[] parts) {
		if (parts.length != 2) {
			throw new IllegalArgumentException("Krivi broj argumenata");
		}
		try {
			return new Artikl(parts[0], Integer.parseInt(parts[1]));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Second argument is not a number");
		}
	}

	class Slice {
		int value;
		Color color;

		public Slice(int value, Color color) {
			this.value = value;
			this.color = color;
		}
	}

	class PieChart extends JComponent {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		List<Slice> slices;

		public PieChart(List<Slice> slices) {
			super();
			this.slices = slices;
		}

		public void paint(Graphics g) {
			Rectangle bounds = getBounds();
			Rectangle piebounds;
			int x, y;
			if (bounds.getWidth() < bounds.getHeight()) {
				piebounds = new Rectangle((int) (bounds.getWidth() * 0.9), (int) (bounds.getWidth() * 0.9));
				x = (int) (bounds.getWidth() * 0.05);
				y = (int) (bounds.getHeight() / 2) - piebounds.height / 2;
			} else {
				piebounds = new Rectangle((int) (bounds.getHeight() * 0.9), (int) (bounds.getHeight() * 0.9));
				y = (int) (bounds.getHeight() * 0.05);
				x = (int) (bounds.getWidth() / 2) - piebounds.width / 2;
			}
			drawPie((Graphics2D) g, piebounds, x, y);
		}

		void drawPie(Graphics2D g, Rectangle area, int x, int y) {
			double total = 0.0D;
			for (int i = 0; i < slices.size(); i++) {
				total += slices.get(i).value;
			}

			double curValue = total / 4;
			int startAngle = 0;
			for (int i = 0; i < slices.size(); i++) {
				startAngle = (int) (curValue * 360 / total);
				int arcAngle = (int) (slices.get(i).value * 360 / total);
				g.setColor(slices.get(i).color);
				g.fillArc(x - area.x, y - area.y, area.width, area.height, startAngle, arcAngle);
				g.setColor(Color.black);
				g.drawArc(x - area.x, y - area.y, area.width, area.height, startAngle, arcAngle);
				curValue += slices.get(i).value;
			}
		}
	}

	private Object[][] transformData(List<Artikl> artikli) {
		Object[][] data = new String[artikli.size()][3];
		for (int i = 0; i < artikli.size(); i++) {
			data[i][0] = artikli.get(i).getIme();
			data[i][1] = Integer.toString(artikli.get(i).getBroj());
			data[i][2] = Integer.toString(artikli.get(i).getBoja().getRGB());
		}
		return data;
	}

	public class ColorColumnCellRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int col) {

			JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
			l.setBackground(Color.decode((String) value));
			return l;

		}
	}
}
