package hr.fer.zemris.java.webserver;

/**
 * {@link FunctionalInterface}
 * Interface towards any object that can process current {@link RequestContext}.
 * 
 * @author Alex
 *
 */
public interface IWebWorker {
	
	/**
	 * Creates content for client and writes it to {@link RequestContext}.
	 * @param context
	 * 					{@link RequestContext} to which content is written
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;

}
