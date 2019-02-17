package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import java.io.IOException;

/**
 * Tester used to test SmartScriptParser. It parses a document, 
 * than it creates original document body which it parses again 
 * and prints out both so they can be compared.
 * @author Alex
 *
 */
public class SmartScriptTester {

	/**
	 * Main method of this class. Does work that was previously described.
	 * @param args arguments from command line. Not used here.
	 * @throws IOException if file that contains document can't be read.
	 */
	public static void main(String[] args) throws IOException {
		StringLoader load = new StringLoader();
		String docBody = load.loader("document1.txt");
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		System.out.println("Document1");
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); 
		System.out.println();
		
		System.out.println("Document2");
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		System.out.println(createOriginalDocumentBody(document2));
	}

	/**
	 * Method that creates original document body from DocumentNode.
	 * @param root DocumentNode that is the root of document model tree
	 * @return string similar to original string, it must be valid for parsing with SmartScriptParser.
	 */
	private static String createOriginalDocumentBody(Node root) {
		StringBuilder str = new StringBuilder();
		str.append(root.toString());
		
		for(int i = 0, n = root.numberOfChildren(); i < n; i++) {
			str.append(createOriginalDocumentBody(root.getChild(i)));
		}
		
		if(root instanceof ForLoopNode) {
			str.append("{$ END $}");
		}
		
		return str.toString();
	}

}
