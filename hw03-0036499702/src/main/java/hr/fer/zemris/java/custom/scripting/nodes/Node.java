package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 * @author Alex
 *
 */
public class Node {
	
	/**
	 * Collection used to store this node's children.
	 */
	private ArrayIndexedCollection children;

	/**
	 * Method used to add child node to this node. Child can't be null.
	 * @param child node to be added to children.
	 * @throws NullPointerException is child is null.
	 */
	public void addChildNode(Node child) {
		Objects.requireNonNull(child);
		if(children == null) {
			children = new ArrayIndexedCollection();
		}
		children.add(child);
	}
	
	/**
	 * Method used to determine number of this node's children.
	 * @return number of children
	 */
	public int numberOfChildren() {
		if(children == null) {
			return 0;
		}
		return children.size();
	}
	
	/**
	 * Method used to retrieve a child by index.
	 * @param index index of child node to get.
	 * @return child node on said index.
	 * @throws IndexOutOfBoundsException if index is out of bounds.
	 */
	public Node getChild(int index) {
		return (Node) children.get(index);//treba li cast?
	}
}
