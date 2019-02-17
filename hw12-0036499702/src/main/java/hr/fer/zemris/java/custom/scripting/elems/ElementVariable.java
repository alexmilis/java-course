package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class used to describe element variable. Has property name (string).
 * @author Alex
 *
 */
public class ElementVariable extends Element {
	
	/**
	 * Read-only name of this node.
	 */
	private final String name;
	
	/**
	 * Constructor of class ElementVariable.
	 * @param name name that is stored as this node's name.
	 */
	public ElementVariable(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String asText() {
		return name;
	}
}
