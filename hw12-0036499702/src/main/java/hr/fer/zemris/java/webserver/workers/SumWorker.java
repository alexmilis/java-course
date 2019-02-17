package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of {@link IWebWorker} that calculates sum of two numbers 
 * and displays numbers and the result in form of a bordered table.
 * If no arguments are given, numbers are set to 1.
 * 
 * @author Alex
 *
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		int a, b;
		
		try{
			a = Integer.parseInt(context.getParameter("a"));
		} catch (Exception ex) {
			a = 1;
		}
		
		try {
			b = Integer.parseInt(context.getParameter("b"));
		} catch (Exception ex) {
			b = 1;
		}
		
		context.setTemporaryParameter("zbroj", Integer.toString(a + b));
		context.setTemporaryParameter("a", Integer.toString(a));
		context.setTemporaryParameter("b", Integer.toString(b));
		
		context.getDispatcher().dispatchRequest("/private/calc.smscr");
	}

}
