package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Base class for all elements. It has only one function that returns string representation of element.
 * @author Alex
 *
 */
public class Element {
	
	/**
	 * Method returns string representation of element. 
	 * Each class that inherits this one has it's own implementation of this method.
	 * @return
	 */
	public String asText() {
		return new String();
	}
}
