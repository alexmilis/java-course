package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Used for constructing a tree from output of {@link SmartScriptParser} 
 * and reproducing document's approximate original form 
 * using Visitor design pattern.
 * 
 * @author Alex
 *
 */
public class TreeWriter {

	/**
	 * Main method that creates {@link SmartScriptParser} and recreates original form of document.
	 * @param args 
	 * 				only argument is path to file that should be parsed
	 * @throws IOException 
	 * 				if an error occurs while reading or writing
	 */
	public static void main(String[] args) throws IOException {
		
		if(args.length != 1) {
			throw new IllegalArgumentException("Excpected 1 argument: path to file");
		}
		
		Path path = Paths.get(args[0]);
		
		if(!Files.isReadable(path)) {
			throw new IllegalArgumentException("File with path " + path + " is not readable");
		}
		
		String docBody = "";
		
		for(String line : Files.readAllLines(path)) {
			docBody += line;
		}
		
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);

	}
	
	/**
	 * Class used to implement Visitor design pattern. 
	 * Implementation of {@link INodeVisitor}.
	 * 
	 * @author Alex
	 *
	 */
	static class WriterVisitor implements INodeVisitor {
		
		@Override
		public void visitTextNode(TextNode node) {
			System.out.println(node.getText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.println(node.toString());
			visitChildren(node);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.println(node.toString());
			visitChildren(node);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			visitChildren(node);
			System.out.println("{$ END $}");
		}
		
		/**
		 * Visits all children of node.
		 * @param node 
		 * 				node whose children are visited
		 */
		private void visitChildren(Node node) {
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
		
	}
}
