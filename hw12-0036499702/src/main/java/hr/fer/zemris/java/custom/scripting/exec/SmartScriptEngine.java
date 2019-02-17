package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Executes document that has previously been parsed into a tree by {@link SmartScriptParser}.
 * 
 * @author Alex
 *
 */
public class SmartScriptEngine {
	
	/**
	 * Root of document.
	 */
	private DocumentNode documentNode;
	
	/**
	 * Request context in which result of execution is stored.
	 */
	private RequestContext requestContext;
	
	/**
	 * Specific implementation of stack used for storing values and variables.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * Visitor that visits every node in tree and performs specified action for each type of node.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.out.println("An error occured while writing to request context!");
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String name = node.getVariable().asText();
			multistack.push(name, new ValueWrapper(node.getStartExpression().asText()));
			
			while(multistack.peek(name).numCompare(node.getEndExpression().asText()) <= 0) {
				visitChildren(node);
				multistack.peek(name).add(node.getStepExpression().asText());
			}
			multistack.pop(name);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> stack = new Stack<>();
			for(Element e : node.getElements()) {
				if(e == null) {
					continue;
				}
				if(e instanceof ElementVariable) {
					stack.push(multistack.peek(e.asText()).getValue());
				} else if (e instanceof ElementOperator) {
					operator(stack, e.asText());
				} else if (e instanceof ElementFunction) {
					try {
						function(stack, e.asText());
					} catch (NumberFormatException|java.util.EmptyStackException ex) {
						System.out.println("Invalid arguments!");
					}
				} else {
					stack.push(e.asText());
				}
			}
			
			stack.forEach(obj -> {
				try {
					requestContext.write(obj.toString());
				} catch (IOException e1) {
					System.out.println("An error occured while writing to request context!");
				}
			});
				
			
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			visitChildren(node);
		}
		
		/**
		 * Visits all children of node.
		 * @param node
		 * 				node whose children should be visited.
		 * @param forLoop
		 */
		private void visitChildren(Node node) {
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
			
		}
		
		/**
		 * Executes basic operations +, -, *, /.
		 * @param stack
		 * 				stack on which arguments and result are stored
		 * @param text
		 * 				operator
		 */
		private void operator(Stack<Object> stack, String text) {
			ValueWrapper value = new ValueWrapper(stack.pop());
			switch(text) {
			case "+":
				value.add(stack.pop());
				break;
			case "-":
				value.subtract(stack.pop());
				break;
			case "*":
				value.multiply(stack.pop());
				break;
			case "/":
				value.divide(stack.pop());
				break;
			default:
				break;
			}
			stack.push(value.getValue());
		}
		
		/**
		 * Executes function and pushes result on stack in needed.
		 * @param stack
		 * 				stack on which arguments and results are stored
		 * @param function
		 * 				name of function to be executed
		 */
		private void function(Stack<Object> stack, String function) {
			switch(function.substring(1)) {
			case "sin":
				stack.push(Math.sin(Math.toRadians(getNumber(stack.pop()))));
				break;
			case "decfmt":
				DecimalFormat f = new DecimalFormat(stack.pop().toString());
				double x = getNumber(stack.pop());
				stack.push(f.format(x));
				break;
			case "dup":
				stack.push(stack.peek());
				break;
			case "swap":
				Object a = stack.pop();
				Object b = stack.pop();
				stack.push(a);
				stack.push(b);
				break;
			case "setMimeType":
				requestContext.setMimeType(stack.pop().toString());
				break;
			case "paramGet":
				Object defValue = stack.pop();
				String value = requestContext.getParameter(stack.pop().toString());
				stack.push(value == null ? defValue : value);
				break;
			case "pparamGet":
				Object defValuep = stack.pop();
				String valuep = requestContext.getPersistentParameter(stack.pop().toString());
				stack.push(valuep == null ? defValuep : valuep);
				break;
			case "pparamSet":
				requestContext.setPersistentParameter(stack.pop().toString(), stack.pop().toString());
				break;
			case "pparamDel":
				requestContext.removePersistentParameter(stack.pop().toString());
				break;
			case "tparamGet":
				Object defValuet = stack.pop();
				String valuet = requestContext.getTemporaryParameter(stack.pop().toString());
				stack.push(valuet == null ? defValuet : valuet);
				break;
			case "tparamSet":
				requestContext.setTemporaryParameter(stack.pop().toString(), stack.pop().toString());
				break;
			case "tparamDel":
				requestContext.removeTemporaryParameter(stack.pop().toString());
				break;
			}
		}
		
		/**
		 * Returns double of object o, if possible.
		 * @param o
		 * 			object to be parsed as double
		 * @return
		 * 			double value of o
		 */
		private double getNumber(Object o) {
			return Double.parseDouble(o.toString());
		}
		
	};

	/**
	 * Constructor of SmartScriptEngine.
	 * @param documentNode
	 * @param requestContext
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Starts execution of document.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}