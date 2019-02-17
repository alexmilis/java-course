package hr.fer.zemris.java.webserver;

/**
 * {@link FunctionalInterface}
 * Implementations of this interface can send {@link RequestContext}.
 * 
 * @author Alex
 *
 */
public interface IDispatcher {
	
	/**
	 * Constructs and sends {@link RequestContext} based on given file.
	 * @param urlPath
	 * 					path to file
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
	
}
