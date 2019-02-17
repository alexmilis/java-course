package hr.fer.zemris.java.custom.scripting.demo.examples3;

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
 * Demo program for {@link SmartScriptEngine} with arguments specified for brojPoziva.smscr.
 * 
 * @author Alex
 *
 */
public class SmartScriptEngineDemoBrojPoziva {

	/**
	 * Main method.
	 * @param args
	 * 				not needed here
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Path path = Paths.get("webroot/scripts/brojPoziva.smscr");
		
		if(!Files.isReadable(path)) {
			throw new IllegalArgumentException("File with path " + path + " is not readable");
		}
		
		String documentBody = "";
		
		for(String line : Files.readAllLines(path)) {
			documentBody += line  + "\n";
		}

		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}

}
