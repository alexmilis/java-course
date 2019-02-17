package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class used to describe text element.
 * @author Alex
 *
 */
public class ElementString extends Element{

	/**
	 * Read-only value that is stored as this node's value.
	 */
	private final String value;

	/**
	 * Constructor of class ElementString.
	 * @param value value that is stored as this node's value.
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}
	
	@Override
	public String asText() {
		return value;
	}
}
