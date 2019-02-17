package hr.fer.zemris.java.gui.calc.Calculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.CalcValueListener;
import hr.fer.zemris.java.gui.calc.Calculator.operations.BinaryOperations;
import hr.fer.zemris.java.gui.calc.Calculator.operations.IOperation;
import hr.fer.zemris.java.gui.calc.Calculator.operations.Operations;
import hr.fer.zemris.java.gui.calc.Calculator.operations.UnaryOperations;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Simple calculator. It implements basic mathematical operations, 
 * trigonometric operations and support for working with stack.
 * Only form of input is by clicking buttons. 
 * Each selected digit is added to the end of number that is showed on display.
 * Button "clr" clears current value stored.
 * Button "rst" resets whole calculator to starting state.
 * Checkbox "Inv" is used to get inverse of functions, if such exists.
 * @author Alex
 *
 */
public class Calculator extends JFrame implements CalcValueListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@link CalcModel} of this calculator.
	 */
	private CalcModelImpl model;
	
	/**
	 * Flag that is true if checkbox "Inv" is selected.
	 */
	private boolean isInverse;
	
	/**
	 * Label that shows state of calculator (current value or operand).
	 */
	private JLabel display;
	
	/**
	 * Stack used to implement buttons "push" and "pop".
	 */
	private Stack<Double> stack;
	
	/**
	 * List of commands that have inverse function.
	 */
	private static final String INVERSIBLE = "sin, cos, tan, ctg, log, ln, x^n";
	
	/**
	 * Map that associates buttons with operations that don't require any arguments.
	 */
	private static final Map<String, IOperation> OPERATIONS = new HashMap<>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("=", Operations.EQUALS);
			put("clr", Operations.CLR);
			put("res", Operations.RES);
			put("push", Operations.PUSH);
			put("pop", Operations.POP);
			put("+/-", Operations.SWAP);
		}
	};
	
	/**
	 * Map that associates buttons with operations that require one argument.
	 */
	private static final Map<String, DoubleUnaryOperator> UNARY = new HashMap<>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("1/x", UnaryOperations.ONE_DIVIDED_BY_X);
			put("log", UnaryOperations.LOG);
			put("ln", UnaryOperations.LN);
			put("sin", UnaryOperations.SIN);
			put("cos", UnaryOperations.COS);
			put("tan", UnaryOperations.TAN);
			put("ctg", UnaryOperations.CTG);
			put("ilog", UnaryOperations.POW_TEN);
			put("iln", UnaryOperations.POW_TWO);
			put("isin", UnaryOperations.ASIN);
			put("icos", UnaryOperations.ACOS);
			put("itan", UnaryOperations.ATAN);
			put("ictg", UnaryOperations.ACTG);
		}
	};
	
	/**
	 * Map that associates buttons with operations that require two argument.
	 */
	private static final Map<String, DoubleBinaryOperator> BINARY = new HashMap<>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("x^n", BinaryOperations.POW);
			put("ix^n", BinaryOperations.ROOT);
			put("+", BinaryOperations.ADD);
			put("-", BinaryOperations.SUB);
			put("*", BinaryOperations.MUL);
			put("/", BinaryOperations.DIV);
		}
	};
	
	/**
	 * Map that associates buttons with their locations.
	 */
	private static final Map<String, String> buttons = new TreeMap<>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("=", "1,6");
			put("clr", "1,7");
			
			put("1/x", "2,1");
			put("sin", "2,2");
			put("7", "2,3");
			put("8", "2,4");
			put("9", "2,5");
			put("/", "2,6");
			put("res", "2,7");

			put("log", "3,1");
			put("cos", "3,2");
			put("4", "3,3");
			put("5", "3,4");
			put("6", "3,5");
			put("*", "3,6");
			put("push", "3,7");

			put("ln", "4,1");
			put("tan", "4,2");
			put("1", "4,3");
			put("2", "4,4");
			put("3", "4,5");
			put("-", "4,6");
			put("pop", "4,7");
			
			put("x^n", "5,1");
			put("ctg", "5,2");
			put("0", "5,3");
			put("+/-", "5,4");
			put(".", "5,5");
			put("+", "5,6");

		}
	};
	
	/**
	 * Constructor.
	 */
	public Calculator() {
		super();
		this.model = new CalcModelImpl();
		this.isInverse = false;
		this.stack = new Stack<>();
		
		model.addCalcValueListener(this);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(450, 250);
		setLocation(20, 20);
		setTitle("Calculator");
		setBackground(Color.WHITE);
		initGUI();
	}

	/**
	 * Method that initializes graphic user interface of calculator.
	 */
	private void initGUI() {
		getContentPane().setLayout(new CalcLayout(5));
		
		display = new JLabel("0.0");
		display.setSize(100, 20);
		display.setOpaque(true);
		display.setBackground(Color.ORANGE);
		display.setForeground(Color.BLACK);
		display.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
		add(display, "1,1");
		
		ActionListener action = a -> {
			
			JButton b = (JButton)a.getSource();
			String text = b.getText();
			
			Thread t = new Thread(() -> {
				String operation = text;
				if(operation.equals("=")) {
					executePendingOperation();
				} else if(operation.matches("\\d")) {
					model.insertDigit(Integer.parseInt(operation));
				} else if (operation.equals(".")) {
					model.insertDecimalPoint();
				} else {				
					if(INVERSIBLE.contains(operation) && isInverse) {
						operation = "i" + operation;
					}
					
					if(OPERATIONS.containsKey(operation)) {
						OPERATIONS.get(operation).execute(model, this);
					} else if(UNARY.containsKey(operation)) {
						model.setValue(UNARY.get(operation).applyAsDouble(model.getValue()));
						executePendingOperation();
					} else {
						executePendingOperation();
						model.setActiveOperand(model.getValue());
						model.clear();
						model.setPendingBinaryOperation(BINARY.get(operation));
					}
				}
			});
			
			t.start();
				
		};
		
		addButtons(action);
		
		JCheckBox inverse = new JCheckBox("Inv");
		inverse.setForeground(Color.BLACK);
		inverse.addActionListener(a -> isInverse = !isInverse);
		add(inverse, "5,7");
		
		setSize(getPreferredSize());
		
	}

	/**
	 * Method that adds buttons to calculator.
	 * @param action {@link ActionListener} that needs to be added as listener.
	 */
	private void addButtons(ActionListener action) {
		buttons.forEach((text, rc) -> {
			JButton b = new JButton(text);
			b.setPreferredSize(new Dimension(70, 70));
			b.setOpaque(true);
			b.setBackground(Color.BLUE);
			b.setForeground(Color.BLACK);
			b.addActionListener(action);
			
			add(b, rc);
		});
	}
	
	/**
	 * Method that executes pending operation and clears what is needed from model.
	 */
	private void executePendingOperation() {
		if(model.getPendingBinaryOperation() != null) {
			double newValue = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
			model.clearAll();
			model.setValue(newValue);
		}
	}

	@Override
	public void valueChanged(CalcModel model) {
		display.setText(model.getValue() + "");		
	}
	
	/**
	 * Main method that starts the calculator.
	 * @param args not needed here.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Calculator calc = new Calculator();
				calc.setVisible(true);
			}
		});
	}

	/**
	 * Getter for stack.
	 * @return stack that contains double values.
	 */
	public Stack<Double> getStack() {
		return stack;
	}
}
