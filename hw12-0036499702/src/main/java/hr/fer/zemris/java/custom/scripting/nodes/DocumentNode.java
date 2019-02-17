package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node that represents an entire document.
 * @author Alex
 *
 */
public class DocumentNode extends Node {

	@Override
	public String toString() {
		return "";
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
