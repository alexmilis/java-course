package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of {@link IWebWorker} that lists all parameters 
 * currently stored in context and their values.
 * @author Alex
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		context.setMimeType("text/html");
		try {
			context.write("<html><body>");
			context.write("<table>");
			context.write("<tr><th>Parameter</th>");
			context.write("<th>Value</th></tr>");
			for(String name : context.getParameterNames()) {
				context.write("<tr><th>" + name + "</th>");
				context.write("<th>" + context.getParameter(name) + "</th></tr>");
			}
			context.write("</body></html>");
		} catch (IOException ex) {
			System.err.println("An error occured while processing echo parameters!");
		}
		
	}

}
