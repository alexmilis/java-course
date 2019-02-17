package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Node that represents a command which generates some textual output dynamically.
 * @author Alex
 *
 */
public class ForLoopNode extends Node {
	
	/**
	 * Variable used in this for loop. Can't be null.
	 */
	private ElementVariable variable;
	
	/**
	 * Starting expression of this loop. Can't be null.
	 */
	private Element startExpression;
	
	/**
	 * End expression of this loop. Can't be null.
	 */
	private Element endExpression;
	
	/**
	 * Step expression. Can be null.
	 */
	private Element stepExpression;//can be null
	
	
	/**
	 * Constructor of class ForLoopNode.
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 * @throws NullPointerException if variable, startExpression or endExpression is null. 
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = Objects.requireNonNull(variable);
		this.startExpression = Objects.requireNonNull(startExpression);
		this.endExpression = Objects.requireNonNull(endExpression);
		this.stepExpression = stepExpression;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("{$ FOR ").append(variable.asText()).append(" ")
			.append(startExpression.asText()).append(" ")
			.append(endExpression.asText()).append(" ");
		if(stepExpression != null) {
			str.append(stepExpression.asText()).append(" ");
		}
		str.append("$}");
		return str.toString();
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}

	public ElementVariable getVariable() {
		return variable;
	}

	public Element getStartExpression() {
		return startExpression;
	}

	public Element getEndExpression() {
		return endExpression;
	}

	public Element getStepExpression() {
		return stepExpression;
	}
	
	
	
}
