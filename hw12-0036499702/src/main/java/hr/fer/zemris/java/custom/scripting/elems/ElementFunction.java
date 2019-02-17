package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class used to describe function element.
 * @author Alex
 *
 */
public class ElementFunction extends Element {

	/**
	 * Read-only name of this node.
	 */
	private final String name;

	/**
	 * Constructor of class ElementFunction.
	 * @param name name that is stored as this node's name.
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String asText() {
		return name;
	}
}
