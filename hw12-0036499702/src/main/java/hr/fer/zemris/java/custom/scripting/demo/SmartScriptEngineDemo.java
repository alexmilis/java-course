package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demo program for {@link SmartScriptEngine}. 
 * Executes the result of {@link SmartScriptParser} and writes 
 * the outcome to {@link RequestContext}.
 * 
 * @author Alex
 *
 */
public class SmartScriptEngineDemo {

	/**
	 * Main method.
	 * @param args
	 * 				only argument is path to smscr file that should be executed.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		if(args.length != 1) {
			throw new IllegalArgumentException("Excpected 1 argument: path to file");
		}
		
		Path path = Paths.get(args[0]);
		
		if(!Files.isReadable(path)) {
			throw new IllegalArgumentException("File with path " + path + " is not readable");
		}
		
		String documentBody = "";
		
		for(String line : Files.readAllLines(path)) {
			documentBody += line + "\n";
		}
		
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// put some parameter into parameters map
		parameters.put("broj", "4");
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

	}

}
