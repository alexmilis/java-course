package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * Provider for color. It has ability to change color and has to notify listeners when that happens.
 * @author Alex
 *
 */
public interface IColorProvider {
	
	/**
	 * Getter for currently selected color.
	 * @return
	 * 				current color
	 */
	public Color getCurrentColor();
	
	/**
	 * Adds color change listener to this provider's list of listeners.
	 * @param l
	 * 				{@link ColorChangeListener}
	 */
	public void addColorChangeListener(ColorChangeListener l);
	
	/**
	 * Removes color change listener from this provider's list of listeners.
	 * @param l
	 * 				{@link ColorChangeListener}
	 */
	public void removeColorChangeListener(ColorChangeListener l);
	
}
