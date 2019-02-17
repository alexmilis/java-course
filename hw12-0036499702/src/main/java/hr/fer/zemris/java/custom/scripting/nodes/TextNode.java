package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node that represents a piece of textual data.
 * @author Alex
 *
 */
public class TextNode extends Node {
	
	/**
	 * Read-only text of this node.
	 */
	private final String text;

	/**
	 * Constructor of class TextNode
	 * @param text text of this node.
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}	
	
	/**
	 * Getter for field text.
	 * @return
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return getText();
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
