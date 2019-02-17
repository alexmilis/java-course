package hr.fer.zemris.java.hw05.db.parser;

import hr.fer.zemris.java.hw05.db.QueryParser;

/**
 * Class used to describe exceptions that occur in {@link QueryParser}.
 * @author Alex
 *
 */
public class ParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParserException() {
		// TODO Auto-generated constructor stub
	}

	public ParserException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ParserException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ParserException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ParserException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
