package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class used to describe an element with constant double value.
 * @author Alex
 *
 */
public class ElementConstantDouble extends Element {
	
	/**
	 * Read-only double value of this node.
	 */
	private final double value;

	/**
	 * Constructor of class ElementConstantDouble.
	 * @param value value that is stored as this node's value.
	 */
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(value);
	}

}
