package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom layout for calculator model. 
 * By default it is divided into 5 rows and 7 columns.
 * Fields from (1, 1) to (1, 5) are merged into one big field under index (1, 1).
 * Components are constrained by instances of {@link RCPosition}.
 * Max number of components is 31.
 * @author Alex
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Number of rows.
	 */
	private final static int ROWS = 5;
	
	/**
	 * Number of columns.
	 */
	private final static int COLUMNS = 7;
	
	/**
	 * Constant for x and y alignment.
	 */
	private final static float ALIGNMENT = 0.5F;
	
	/**
	 * Max number of components.
	 */
	private final static int MAX_COMP = 31;
	
	/**
	 * Size of white space between components in pixels.
	 */
	private int gap;
	
	/**
	 * Map of components and their constraints.
	 */
	private Map<Component, RCPosition> components;
	
	/**
	 * Constructor of CalcLayout.
	 * @param gap size of gap between components in pixels.
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
		this.components = new HashMap<>();
	}
	
	/**
	 * Constructor that creates CalcLayout with gap set to 0.
	 */
	public CalcLayout() {
		this(0);
	}
	
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int width = 0;
		int height = 0;
		for(Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			if(entry.getValue().equals(new RCPosition(1, 1))) {
				if((entry.getKey().getPreferredSize().getWidth()-4*gap) / 5 > width) {
					width = (int) ((entry.getKey().getPreferredSize().getWidth()-4*gap) / 5);
				}
			} else {
				if(entry.getKey().getPreferredSize().getWidth() > width) {
					width = (int) entry.getKey().getPreferredSize().getWidth();
				}
			}
			
			if(entry.getKey().getPreferredSize().getHeight() > height) {
				height = (int) entry.getKey().getPreferredSize().getHeight();
			}
		}
		
		width = width*COLUMNS + gap*(COLUMNS-1);
		height = height*ROWS + gap*(ROWS-1);
		
		return new Dimension(parent.getInsets().left + width + parent.getInsets().right,
				parent.getInsets().top + height + parent.getInsets().bottom);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		int width = 0;
		int height = 0;
		for(Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			if(entry.getValue().equals(new RCPosition(1, 1))) {
				if((entry.getKey().getMinimumSize().getWidth()-4*gap) / 5 > width) {
					width = (int) ((entry.getKey().getMinimumSize().getWidth()-4*gap) / 5);
				}
			} else {
				if(entry.getKey().getMinimumSize().getWidth() > width) {
					width = (int) entry.getKey().getMinimumSize().getWidth();
				}
			}
			
			if(entry.getKey().getMinimumSize().getHeight() > height) {
				height = (int) entry.getKey().getMinimumSize().getHeight();
			}
		}
		
		width = width*COLUMNS + gap*(COLUMNS-1);
		height = height*ROWS + gap*(ROWS-1);
		
		return new Dimension(parent.getInsets().left + width + parent.getInsets().right,
				parent.getInsets().top + height + parent.getInsets().bottom);
	}

	@Override
	public void layoutContainer(Container parent) {
		
		int width = (parent.getWidth() - gap*(COLUMNS-1)) / COLUMNS;
		int height = (parent.getHeight() - gap*(ROWS-1)) / ROWS;
		
		for(Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Component comp = entry.getKey();
			RCPosition rc = entry.getValue();
			if(rc.equals(new RCPosition(1, 1))) {
				comp.setSize(width*5 + gap*4, height);
			} else {
				comp.setSize(width, height);
			}
			
			comp.setLocation((rc.getColumn()-1) * (width+gap),
					(rc.getRow()-1) * (height+gap));
			
			if(!rc.equals(new RCPosition(1, 1))) {
				comp.setBackground(Color.BLUE);
			}
			
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null || constraints == null) {
			throw new CalcLayoutException("Component and constraint cannot be null!");
		}
		
		if(components.size() == MAX_COMP) {
			throw new CalcLayoutException("Max number of components is already in layout!");
		}
		
		RCPosition constraint;
		
		if(constraints instanceof RCPosition) {
			constraint = (RCPosition) constraints;
		} else if (constraints instanceof String){
			constraint = getRC((String) constraints);
		} else {
			throw new CalcLayoutException("Invalid object given as constraint: " + constraints.toString());
		}
		
		if(components.containsKey(comp)) {
			throw new CalcLayoutException("Component already exists at constraint: " + constraint.toString());
		}
		
		if(components.containsValue(constraint)) {
			throw new CalcLayoutException("This component is already in layout: " + comp.toString());
		}
		
		if(constraint.getRow() < 1 || constraint.getRow() > ROWS) {
			throw new CalcLayoutException("Index of row can be only from interval [1, 5], requested: " + constraint.getRow());
		}
		
		if(constraint.getColumn() < 1 || constraint.getColumn() > COLUMNS) {
			throw new CalcLayoutException("Index of column can be only from interval [1, 7], requested: " + constraint.getColumn());
		}
		
		if(constraint.getRow() == 1 && constraint.getColumn() > 1 && constraint.getColumn() < 6) {
			throw new CalcLayoutException("In first row, only columns that can be accessed are 1, 6 and 7. Requested: " + constraint.getColumn());
		}
		
		components.put(comp, constraint);
	}
	
	/**
	 * Adds components whose constraint is given in form of string.
	 * It parses given string into new {@link RCPosition} and delegates further work to previous method.
	 * @param comp component to be added.
	 * @param constraints 	string representation of constraints.ž
	 * 						must be in form "r,s".
	 */
	public RCPosition getRC(String constraints) {
		try {
			int r = Integer.parseInt(constraints.substring(0, constraints.indexOf(",")));
			int s = Integer.parseInt(constraints.substring(constraints.indexOf(",") + 1));
			
			return new RCPosition(r, s);
		} catch(NumberFormatException ex) {
			throw new CalcLayoutException("Invalis string given as constraint: " + constraints);
		}
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		int width = 0;
		int height = 0;
		for(Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			if(entry.getValue().equals(new RCPosition(1, 1))) {
				if((entry.getKey().getMaximumSize().getWidth()-4*gap) / 5 > width) {
					width = (int) ((entry.getKey().getMaximumSize().getWidth()-4*gap) / 5);
				}
			} else {
				if(entry.getKey().getMaximumSize().getWidth() > width) {
					width = (int) entry.getKey().getMaximumSize().getWidth();
				}
			}
			
			if(entry.getKey().getMaximumSize().getHeight() > height) {
				height = (int) entry.getKey().getMaximumSize().getHeight();
			}
		}
		
		width = width*COLUMNS + gap*(COLUMNS-1);
		height = height*ROWS + gap*(ROWS-1);
		
		return new Dimension(target.getInsets().left + width + target.getInsets().right,
				target.getInsets().top + height + target.getInsets().bottom);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return ALIGNMENT;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return ALIGNMENT;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

}
