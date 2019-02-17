package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * Listener for color changes.
 * @author Alex
 *
 */
@FunctionalInterface
public interface ColorChangeListener {
	
	/**
	 * Performs specified action when color is changed.
	 * @param source
	 * 				color provider that caused change
	 * @param oldColor
	 * 				color before change
	 * @param newColor
	 * 				color after change
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);

}
