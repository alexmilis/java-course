package hr.fer.zemris.java.gui.layouts;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Example from instructions. 
 * Uncomment lines from 38 to 46 to get panel with max number of components.
 * @author Alex
 *
 */
public class Example {

	/**
	 * Main method.
	 * @param args not needed here.
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			JFrame f = new JFrame();
			f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			f.setSize(600, 400);
			
			JPanel p = new JPanel(new CalcLayout(3));
			
			p.add(new JLabel("x"), new RCPosition(1,1));
			p.add(new JLabel("y"), new RCPosition(2,3));
			p.add(new JLabel("z"), new RCPosition(2,7));
			p.add(new JLabel("w"), new RCPosition(4,2));
			p.add(new JLabel("a"), new RCPosition(4,5));
			p.add(new JLabel("b"), new RCPosition(4,7));
			
			//comment lines from 30 to 35 if you wish to uncomment this section
//			p.add(new JLabel("x"), new RCPosition(1,1));
//			p.add(new JLabel("x"), new RCPosition(1,6));
//			p.add(new JLabel("x"), new RCPosition(1,7));
//
//			for(int i = 2; i < 6; i++) {
//				for(int j = 1; j < 8; j++) {
//					p.add(new JLabel("x"), new RCPosition(i, j));
//				}
//			}
			
			f.add(p);
			
			f.setVisible(true);
		});

	}

}
