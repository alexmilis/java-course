package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Util class for commands. Contains method for extracting arguments.
 * @author Alex
 *
 */
public class CommandsUtil {
	
	/**
	 * Characters that can be parsed between "".
	 */
	private final static String PARSE = "\\\\\"";
	
	/**
	 * Method for extracting arguments from line.
	 * @param arguments string to be parsed.
	 * @return string[] args.
	 * @throws IllegalArgumentException if arguments are invalid.
	 */
	public static String[] getArgs(String arguments) {
		
		List<String> args = new ArrayList<>();
		
		String arg = "";
		boolean cond = false;
		
		for(int i = 0; i < arguments.length(); i++) {
			String s = arguments.substring(i, i + 1);
			if(arg.length() == 0) {
				if(s.equals("\"")) {
					cond = true;
				} else {
					arg += s;
				}
			} else {
				if(cond && s.equals("\\")) {
					if(PARSE.contains(arguments.substring(i + 1, i + 2))) {
						arg += arguments.substring(i + 1, i + 2);
						i++;
						continue;
					}
				}
				if(s.equals("\"")) {
					if(arg.trim().length() != 0) {
						args.add(arg.trim());
					}
					arg = "";
					cond = !cond;
				} else {
					if(s.equals(" ") && cond == false) {
						if(arg.length() != 0) {
							args.add(arg.trim());
						}
						arg = "";
					} else {
						arg += s;
					}
				}
			}
		}
		
		if(arg.trim().length() != 0) {
			args.add(arg.trim());
		}
		
		if(!arguments.endsWith("\"") && cond == true) {
			throw new IllegalArgumentException("Argument invalid, \" not closed: " + arguments);
		}
		
		return args.toArray(new String[0]);
	}

}
