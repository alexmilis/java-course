package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Node that represents a command which generates some textual output dynamically.
 * @author Alex
 *
 */
public class EchoNode extends Node {

	/**
	 * Read-only array of elements. Can't be null.
	 */
	private final Element[] elements;

	/**
	 * Constructor of class EchoNode.
	 * @param elements array of elements.
	 * @throws NullPointerException if elements is null.
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = Objects.requireNonNull(elements);
	}
	
	/**
	 * Getter for array elements;
	 * @return
	 */
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("{$= ");
		for(Element el : elements) {
			if(el == null) break;
			str.append(el.asText()).append(" ");
		}
		str.append("$}");
		return str.toString();
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
