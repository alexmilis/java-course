package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of {@link IWebWorker} that sets background color 
 * of html file and calls dispatching of request that will execute 
 * file /private/home.smscr.
 * 
 * @author Alex
 *
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String background = context.getPersistentParameter("bgcolor");
		if(background == null) {
			background = "7F7F7F";
		}
		
		context.setTemporaryParameter("background", background);
		context.getDispatcher().dispatchRequest("/private/home.smscr");
	}

}
