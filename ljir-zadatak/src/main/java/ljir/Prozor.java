package ljir;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class Prozor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Actions actions;
	
	private JPanel panel;
	
	private JTabbedPane tabbed;
	
	public Prozor() {
		initGUI();
	}
	
	private void initGUI() {
		setTitle("title");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//ili to u actions
		
		setLayout(new BorderLayout());
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.getContentPane().add(panel, BorderLayout.CENTER);
		
		tabbed = new JTabbedPane();
		
		panel.add(tabbed, BorderLayout.CENTER);
		
		
		
		setSize(500, 500);
		
		actions = new Actions(this);
		
		setJMenuBar(actions.createMenuBar());
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Prozor().setVisible(true);
		});

	}

	public Actions getActions() {
		return actions;
	}

	public void setActions(Actions actions) {
		this.actions = actions;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JTabbedPane getTabbed() {
		return tabbed;
	}

	public void setTabbed(JTabbedPane tabbed) {
		this.tabbed = tabbed;
	}

	

}
