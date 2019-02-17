package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Class used to build and configure LSystem.
 * @author Alex
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Dictionary that connects chars with commands.
	 *  key -> char
	 *  value -> command
	 */
	private Dictionary commands;
	
	/**
	 * Dictionary that stores productions.
	 *  key -> char
	 *  value -> production
	 */
	private Dictionary productions;
	
	/**
	 * Length of a unit.
	 */
	private double unitLength;
	
	/**
	 * Scaler for unit length.
	 */
	private double unitLengthScaler;
	
	/**
	 * Turtle's starting point.
	 */
	private  Vector2D origin;
	
	/**
	 * Angle that turtle's direction closes with the axis.
	 */
	private double angle;
	
	/**
	 * Starting axiom of LSystem.
	 */
	private String axiom;
	
	
	/**
	 * Constructor of class LSystemBuilder. Sets default values.
	 */
	public LSystemBuilderImpl() {
		super();
		this.commands = new Dictionary();
		this.productions = new Dictionary();
		this.unitLength = 0.1;
		this.unitLengthScaler = 1;
		this.origin = new Vector2D(0, 0);
		this.angle = 0;
		this.axiom = "";
	}
	
	
	/**
	 * Method that builds LSystem.
	 * @return LSystem
	 */
	@Override
	public LSystem build() {
		return new LSystemPrivate();
	}

	/**
	 * Method that configures LSystemBuilder from text.
	 * @param arg0 array of strings, each line represents one action
	 * @throws LSystemBuilderImplException if action is invalid
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for(String line : arg0) {
			if(line.isEmpty()) {
				continue;
			}
			
			while(line.contains("  ")) {
				line = line.replace("  ", " ");
			}
			
			String keyword = line.trim().substring(0, line.indexOf(" "));
			line = line.trim().substring(line.indexOf(" ") + 1, line.length());
			String[] parts = line.split(" ");
			
			try {
				switch(keyword) {
				
				case "origin":
					checkPartsLength(parts, 2, keyword);
					this.setOrigin(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
					break;
					
				case "angle":
					checkPartsLength(parts, 1, keyword);
					this.setAngle(Double.parseDouble(parts[0]));
					break;
					
				case "unitLength":
					checkPartsLength(parts, 1, keyword);
					this.setUnitLength(Double.parseDouble(parts[0]));
					break;
					
				case "unitLengthDegreeScaler":
					if(parts.length == 3) {
						this.setUnitLengthDegreeScaler(Double.parseDouble(parts[0]) / Double.parseDouble(parts[2]));
					} else if(String.join("", parts).contains("/")) {
						String str = String.join("", parts);
						this.setUnitLengthDegreeScaler(Double.parseDouble(str.substring(0, str.indexOf("/"))) /
								Double.parseDouble(str.substring(str.indexOf("/") + 1)));
					} else {
						checkPartsLength(parts, 1, keyword);
						this.setUnitLengthDegreeScaler(Double.parseDouble(parts[0]));
					}
					break;
					
				case "command":
					if(parts.length == 3) {
						this.registerCommand(parts[0].charAt(0), parts[1] + " " + parts[2]);
					} else {
						checkPartsLength(parts, 2, keyword);
						this.registerCommand(parts[0].charAt(0), parts[1]);
					}
					break;
					
				case "axiom":
					checkPartsLength(parts, 1, keyword);
					this.setAxiom(parts[0]);
					break;
					
				case "production":
					checkPartsLength(parts, 2, keyword);
					this.registerProduction(parts[0].charAt(0), parts[1]);
					break;
					
				default:
					throw new LSystemBuilderImplException("Invalid keyword: " + parts[0]);
				}
			} catch (NumberFormatException ex) {
				throw new LSystemBuilderImplException("Invalid arguments, cannot be parsed: " + line);
			}
				
		}
		return this;
	}

	/**
	 * Stores command with its key in the dictionary commands.
	 * @param arg0 char key
	 * @param arg1 string that needs to be parsed into a command
	 * @return LSystemBuilder
	 * @throws LSystemBuilderImplException if arguments are invalid
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		String action;
		Command command = null;
		
		if(!arg1.contains(" ")) {
			if(arg1.equals("push")) {
				command = new PushCommand();
			} else if (arg1.equals("pop")) {
				command = new PopCommand();
			} else {
				throw new LSystemBuilderImplException("Invalid command: " + arg1);
			}
			return this;
		} else {
			action = arg1.substring(0, arg1.indexOf(" "));
			
			switch(action) {
			case "draw":
				command = new DrawCommand(getArgument(arg1));
				break;
			case "skip":
				command = new SkipCommand(getArgument(arg1));
				break;
			case "scale":
				command = new ScaleCommand(getArgument(arg1));
				break;
			case "rotate":
				command = new RotateCommand(getArgument(arg1));
				break;
			case "color":
				Color color = Color.decode("#" + arg1.substring(arg1.indexOf(" "), arg1.length()).trim().toLowerCase());
				command = new ColorCommand(color);
				break;
			default:
				throw new LSystemBuilderImplException("Invalid command: " + action);
			}
		}
		
		this.commands.put(arg0, command);
		return this;
	}
	
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		this.productions.put(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double arg0) {
		this.angle = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String arg0) {
		this.axiom = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		this.origin = new Vector2D(arg0, arg1);
		return this;
	}

	/**
	 * 
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		this.unitLength = arg0;
		return this;
	}

	/**
	 * Setter for unit length scaler.
	 * @param arg0 unit length scaler
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		this.unitLengthScaler = arg0;
		return this;
	}
	
	/**
	 * Class that generates and draws LSystem based on configuration given to LSystemBuilderImpl.
	 * @author Alex
	 *
	 */
	private class LSystemPrivate implements LSystem{

		/**
		 * Draws LSystem by executing commands from dictionary.
		 * @param arg0 how many levels of productions are in LSystem
		 * @param arg1 painter
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			Context ctx = new Context();
			TurtleState state = new TurtleState(origin, vectorFromAngle(), Color.BLACK, unitLength * Math.pow(unitLengthScaler, arg0 - 1));
			ctx.pushState(state);
			
			char[] chars = generate(arg0).toCharArray();
			for(char c : chars) {
				if(!commands.contains(c)) {
					continue;
				}
				Command command = (Command) commands.get(c);
				command.execute(ctx, arg1);
			}
		}

		/**
		 * Generates textual representation of LSystem using productions from dictionary.
		 * @param arg0 how many levels of productions are in LSystem
		 */
		@Override
		public String generate(int arg0) {
			String output = axiom;
			for(int i = 0; i < arg0 - 1; i++) {
				String[] parts = output.split("");
				for(int j = 0; j < parts.length; j++) {
					if(productions.contains(parts[j].charAt(0))) {
						parts[j] = (String) productions.get(parts[j].charAt(0));
					}
				}
				output = String.join("", parts);
			}
			return output;
		}
		
	}

	/**
	 * Help method that parses string into an argument.
	 * @param arg string that needs to be turned into a double
	 * @return double number
	 * @throws LSystemBuilderImplException if number cannot be parsed.
	 */
	private double getArgument(String arg) {
		try {
			return Double.parseDouble(arg.substring(arg.indexOf(" "), arg.length()).trim());
		} catch (NumberFormatException ex) {
			throw new LSystemBuilderImplException("Argument in command cannot be parsed as double: " + arg);
		}
	}

	/**
	 * Help method that checks if there is exact number of arguments needed for a command.
	 * @param parts array with arguments
	 * @param n expected number of arguments
	 * @param action name of command
	 * @throws LSystemBuilderImplException if number of arguments doesn't equal expected
	 */
	private void checkPartsLength(String[] parts, int n, String action) {
		if(parts.length != n) {
			throw new LSystemBuilderImplException("Invalid number of arguments for action " + action + " :" + parts.length + "s" + parts[0] + "a" + parts[1]);
		}
		
		if((action.equals("unitLengthDegreeScaler") && n == 3) && !parts[1].equals("/")) {
			throw new LSystemBuilderImplException("Invalid arguments for " + action);
		}
	}
	
	/**
	 * Help method that finds vector of direction of given angle.
	 * @return vector of direction
	 */
	private Vector2D vectorFromAngle() {
		return (new Vector2D(1, 0)).rotated(this.angle);
	}

}
