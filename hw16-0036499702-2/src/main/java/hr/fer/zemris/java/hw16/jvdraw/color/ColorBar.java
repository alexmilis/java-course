package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Status bar at the bottom of application. 
 * It dynamically shows selected foreground and background colors min rgb form.
 * 
 * @author Alex
 *
 */
public class ColorBar extends JLabel implements ColorChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Provider for foreground color.
	 */
	private IColorProvider foreground;
	
	/**
	 * Provider for background color.
	 */
	private IColorProvider background;
	
	/**
	 * Constructor of color bar.
	 * @param foreground
	 * 				provider for foreground color
	 * @param background
	 * 				provider for background color
	 */
	public ColorBar(IColorProvider foreground, IColorProvider background) {
		super();
		this.foreground = foreground;
		this.background = background;
		
		this.foreground.addColorChangeListener(this);
		this.background.addColorChangeListener(this);
		
		setText(toString());
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Foreground color: ").append(foreground.toString());
		sb.append(" background color: ").append(background.toString());
		return sb.toString();
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		setText(toString());
	}
}
