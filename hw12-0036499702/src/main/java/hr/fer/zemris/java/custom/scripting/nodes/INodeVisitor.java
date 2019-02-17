package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface that defines methods for visiting different types of nodes.
 * @author Alex
 *
 */
public interface INodeVisitor {

	/**
	 * Method that needs to be executed when visiting {@link TextNode}.
	 * @param node
	 * 				{@link TextNode} that is visited
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Method that needs to be executed when visiting {@link ForLoopNode}.
	 * @param node
	 * 				{@link ForLoopNode} that is visited
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Method that needs to be executed when visiting {@link EchoNode}.
	 * @param node
	 * 				{@link EchoNode} that is visited
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Method that needs to be executed when visiting {@link DocumentNode}.
	 * @param node
	 * 				{@link DocumentNode} that is visited
	 */
	public void visitDocumentNode(DocumentNode node);
}
