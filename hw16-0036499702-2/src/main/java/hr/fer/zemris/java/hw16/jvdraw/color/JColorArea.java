package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * Implementation of {@link IColorProvider}. 
 * It is used as color chooser for foreground and background colors in application {@link JVDraw}.
 * 
 * @author Alex
 *
 */
public class JColorArea extends JComponent implements IColorProvider, MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Currently selected color.
	 */
	private Color selectedColor;
	
	/**
	 * List of color change listeners.
	 */
	private List<ColorChangeListener> colorListeners = new LinkedList<>();
	
	/**
	 * Constructor of JColorArea.
	 * @param selectedColor
	 * 				default color
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		addMouseListener(this);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.drawRect(0, 0, 15, 15);
		g.fillRect(0, 0, 15, 15);
		super.paintComponent(g);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		if(!colorListeners.contains(l)) {
			colorListeners.add(l);
		}
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		colorListeners.remove(l);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Color color = JColorChooser.showDialog(this, "Choose color", selectedColor);
		
		if(color == null)
			return;
		
		changeColor(color);
	}

	private void changeColor(Color color) {
		Color old = selectedColor;
		this.selectedColor = color;
		colorListeners.forEach(l -> l.newColorSelected(this, old, color));
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(20, 20);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(15, 15);
	}
	
	@Override
	public String toString() {
		StringJoiner stringJoiner = new StringJoiner(", ", "(", ")");
		
		stringJoiner.add(Integer.toString(selectedColor.getRed()));
		stringJoiner.add(Integer.toString(selectedColor.getGreen()));
		stringJoiner.add(Integer.toString(selectedColor.getBlue()));
		
		return stringJoiner.toString();
	}

}
