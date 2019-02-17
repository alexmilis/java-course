package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class used to describe an element with constant int value.
 * @author Alex
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * Read-only int value of this node.
	 */
	private final int value;

	/**
	 * Constructor of class ElementConstantInteger.
	 * @param value value that is stored as this node's value.
	 */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
