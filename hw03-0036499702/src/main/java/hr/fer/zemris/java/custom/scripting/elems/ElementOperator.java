package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class used to describe operator element.
 * @author Alex
 *
 */
public class ElementOperator extends Element {
	
	/**
	 * Read only symbol of this node.
	 */
	private final String symbol;

	/**
	 * Constructor of class ElementOperator.
	 * @param symbol symbol that is stored as this node's symbol.
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}
}
