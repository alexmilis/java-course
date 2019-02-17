package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of {@link IWebWorker} that changes the color of index2.html 
 * and gives the client information about changing color.
 * @author Alex
 *
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgcolor = context.getParameter("bgcolor");
		
		try{
			Color.decode("#" + bgcolor);
			context.setPersistentParameter("bgcolor", "#" + bgcolor);
			generateHtml(context, true);
		} catch (NumberFormatException ex) {
			generateHtml(context, false);
		}
		
	}

	/**
	 * Generates html request that gives client information if the color has changed or not.
	 * @param context
	 * 				{@link RequestContext}
	 * @param update
	 * 				true if color is changed
	 */
	private void generateHtml(RequestContext context, boolean update) {
		context.setMimeType("text/html");
		
		try {
			context.write("<html><body>");
			context.write("<p><a href=\"/index2.html\">index2.html</a></p>");
			context.write("<p>Color is ");
			if(!update) {
				context.write("not ");
			}
			context.write("updated.</p>");
			context.write("</body></html>");
		} catch (IOException e) {
			System.err.println("An exception occured while generating html for change of color!");
		}
		
		
	}

}
