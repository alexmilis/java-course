package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class used to evaluate a postfix expression using a stack.
 * @author Alex
 *
 */

public class StackDemo {
	
	public static final String[] SUPPORTED_OPERATORS = {"+", "-", "/", "*", "%"};
	
	/**
	 * Main method that takes one argument from command line (expression). If possible, evaluates said expression.
	 * @param args expression from command line
	 * @throws IllegalArgumentException if number of arguments is not 1 or expression is invalid
	 * @throws Error if at the end of execution there is more than 1 value on stack
	 */
	public static void main(String[] args) {
		
		try {
			if(args.length != 1) {
				throw new IllegalArgumentException("Program requires 1 argument, but is given " + args.length);
			}		
			
			String[] expression = args[0].split(" ");
			ObjectStack stack = new ObjectStack();
			
			for(String s:expression) {
				try {
					int number = Integer.parseInt(s);
					stack.push(number);
					continue;
				} catch (NumberFormatException ex) {
					opCheck(s);
					if(stack.size() < 2) {
						throw new IllegalArgumentException("Wrong input, too many operators, can't evaluate expression " + args[0]);
					}
					execute(s, stack);
				}
			}
			
			if(stack.size() != 1) {
				throw new Error("An error occurred, stack size is not 1 at the end of expression " + args[0]);
			} else {
				System.out.println("Expression evaluates to " + stack.pop().toString() + ".");
			}
		} catch (IllegalArgumentException ex2) {
			System.err.println(ex2.getMessage());
		} catch (Error e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Method executes postfix expression.
	 * @param s postfix operator
	 * @param stack stack with values
	 * @throws IllegalArgumentException if required to divide by 0
	 */
	private static void execute(String s, ObjectStack stack) {
		int op2 = (int) stack.pop();
		int op1 = (int) stack.pop();
		
		switch(s) {
		case "+":
			stack.push(op1 + op2);
			break;
		case "-":
			stack.push(op1-op2);
			break;
		case "/":
			if(op2 == 0) {
				throw new IllegalArgumentException("Can't divide by 0");
			}
			stack.push((int)(op1/op2));
			break;
		case "*":
			stack.push(op1 * op2);
			break;
		case "%":
			if(op2 == 0) {
				throw new IllegalArgumentException("Can't divide by 0");
			}
			stack.push(op1 % op2);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Method checks if operator is supported. Supported operators are: +, - , /, *, %.
	 * @param s operator
	 * @throws IllegalArgumentException if given operator is not supported
	 */
	private static void opCheck(String s) {
		boolean check = false;
		for(String op: SUPPORTED_OPERATORS) {
			if(op.equals(s)) {
				check = true;
			}
		}
		
		if(!check) {
			throw new IllegalArgumentException("Unsupported operator " + s);
		}
	}
	
}
