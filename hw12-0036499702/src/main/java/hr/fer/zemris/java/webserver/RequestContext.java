package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Http request sent by {@link SmartHttpServer}.
 * 
 * @author Alex
 *
 */
public class RequestContext {
	
	/**
	 * Output stream in which requests are sent.
	 */
	private OutputStream outputStream;
	
	/**
	 * Charset used to encode requests.
	 */
	private Charset charset;
	
	//write-only
	/**
	 * Encoding for requests, default is utf-8.
	 */
	public String encoding = "UTF-8";
	
	/**
	 * Status code, default is 200.
	 */
	public int statusCode = 200;
	
	/**
	 * Status text, default is "OK".
	 */
	private String statusText = "OK";
	
	/**
	 * Type of file, default is "text/html".
	 */
	private String mimeType = "text/html";
	
	/**
	 * Read-only collection of parameters.
	 */
	private Map<String, String> parameters;
	
	/**
	 * Readable and writable collection of temporary parameters.
	 */
	private Map<String, String> temporaryParameters;
	
	/**
	 * Readable and writable collection of persistent parameters.
	 */
	private Map<String, String> persistentParameters;
	
	/**
	 * Collection of cookies.
	 */
	private List<RCCookie> outputCookies;
	
	/**
	 * Flag for generation of header.
	 */
	private boolean headerGenerated = false;
	
	/**
	 * Default charset for encoding header.
	 */
	private final Charset headerCharset = Charset.forName("ISO-8859-1");
	
	/**
	 * Strin that separates lines in request.
	 */
	private final String lineSeparator = "\r\n";
	
	/**
	 * Length of content, default is null.
	 */
	private Long contentLength = null;
	
	/**
	 * Dispatcher that can be called to dispatch this request.
	 */
	private IDispatcher dispatcher;
	
	/**
	 * Constructor of RequestContext.
	 * @param outputStream
	 * @param parameters
	 * @param persistentParameters
	 * @param outputCookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		super();
		this.outputStream = Objects.requireNonNull(outputStream);
		this.parameters = parameters == null ? 
				new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ?
				new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ?
				new LinkedList<>() : outputCookies;
		this.temporaryParameters = new HashMap<>();
	}
	
	/**
	 * Constructor of RequestContext.
	 * @param outputStream
	 * @param parameters
	 * @param persistentParameters
	 * @param outputCookies
	 * @param temporaryParameters
	 * @param dispatcher
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
	}

	/**
	 * Getter for parameter.
	 * @param name
	 * 				name of parameter that needs to be fetched
	 * @return
	 * 				value of parameter with name name
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Retrieves names of all parameters.
	 * @return
	 * 			read-only set of parameter names
	 */
	public Set<String> getParameterNames(){
		return Collections.unmodifiableSet(parameters.keySet());
	}
	
	/**
	 * Retrieves value of persistent parameter.
	 * @param name
	 * 				name of parameter
	 * @return
	 * 				value of parameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * Retrieves names of all persistent parameters.
	 * @return
	 * 			read-only set of parameter names
	 */
	public Set<String> getPersistentParameterNames(){
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Adds parameter to persistent parameters.
	 * @param name
	 * 				name of parameter to be added
	 * @param value
	 * 				value of parameter
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * Removes parameter from temporary parameters.
	 * @param name
	 * 				name of parameter to be removed
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Retrieves value of parameter.
	 * @param name
	 * 				name of parameter
	 * @return
	 * 				value of parameter
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Retrieves names of all temporary parameters.
	 * @return 
	 * 			read-only set of parameter names
	 */
	public Set<String> getTemporaryParameterNames(){
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * Adds parameter to temporary parameters.
	 * @param name
	 * 				name of parameter to be added.
	 * @param value
	 * 				value of parameter.
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Removes parameter from temporary parameters.
	 * @param name
	 * 				name of parameter to be removed
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * Setter for content length.
	 * @param len
	 * @throws RuntimeException
	 * 					if header is already generated
	 */
	public void setContentLength (long len) {
		if(headerGenerated == false) {
			this.contentLength = len;
		} else {
			throw new RuntimeException("Header already set!");
		}
	}
	
	/**
	 * Getter for dispatcher
	 * @return
	 * 			dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
	/**
	 * Writes data to output stream.
	 * @param data
	 * 				data that needs to be written
	 * @return
	 * 				this request context
	 * @throws IOException
	 */
	public RequestContext write(byte[] data) throws IOException {
		if(!headerGenerated) {
			writeHeader();
		}
		outputStream.write(data);
		return this;
	}
	
	/**
	 * Writes text to output stream using charset.
	 * @param text
	 * 				text that needs to be written
	 * @return
	 * 				this request context
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException {
		if(!headerGenerated) {
			writeHeader();
		}
		outputStream.write(text.getBytes(charset));
		return this;
	}
	
	/**
	 * Writes specified number of bytes from data with offset to output stream.
	 * @param data
	 * 				data to be written
	 * @param offset
	 * 				index of first byte
	 * @param len
	 * 				number of bytes to be written
	 * @return
	 * 				this request context
	 * @throws IOException
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if(!headerGenerated) {
			writeHeader();
		}
		outputStream.write(data, offset, len);
		return this;
	}
	
	/**
	 * Generates and writes header of this request context.
	 * First line is form "HTTP version, statusCode, statusText".
	 * Other lines depend on attributes of request context.
	 * @throws IOException
	 */
	private void writeHeader() throws IOException {
		StringBuilder header = new StringBuilder();
		charset = Charset.forName(encoding);
		
		header.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText).append(lineSeparator)
			.append("Content-Type: ").append(mimeType);
		
		if(mimeType.startsWith("text/")) {
			header.append("; charset=").append(encoding);
		}
		
		header.append(lineSeparator);
		
		if(contentLength != null) {
			header.append("Content-Length: ").append(contentLength).append(lineSeparator);
		} 
				
		for(RCCookie cookie : outputCookies) {
			header.append("Set-Cookie: ").append(cookie.name).append("=").append(cookie.value);
			
			if(cookie.domain != null) {
				header.append("; Domain=").append(cookie.domain);
			}
			
			if(cookie.path != null) {
				header.append("; Path=").append(cookie.path);
			}
			
			if(cookie.maxAge != null) {
				header.append("; Max-Age=").append(cookie.maxAge);
			}
			
			if(cookie.name.equals("sid")) {
				header.append("; HttpOnly");
			}
			
			header.append(lineSeparator);
			
		}
		
//		header.append("Connection: close").append(lineSeparator).append(lineSeparator);
		header.append(lineSeparator);
		outputStream.write(header.toString().getBytes(headerCharset));
		headerGenerated = true;
	}
	
	
	/**
	 * Setter for charset used in encoding.
	 * @param encoding
	 * 					charset
	 * @throws RuntimeException
	 * 					if header is already generated
	 */
	public void setEncoding(String encoding) {
		if(headerGenerated) {
			throw new RuntimeException("Cannot set that property, header is already written");
		}
		this.encoding = encoding;
	}

	/**
	 * Setter for status code.
	 * @param statusCode
	 * @throws RuntimeException
	 * 					if header is already generated
	 */
	public void setStatusCode(int statusCode) {
		if(headerGenerated) {
			throw new RuntimeException("Cannot set that property, header is already written");
		}
		this.statusCode = statusCode;
	}

	/**
	 * Setter for status text.
	 * @param statusText
	 * @throws RuntimeException
	 * 					if header is already generated
	 */
	public void setStatusText(String statusText) {
		if(headerGenerated) {
			throw new RuntimeException("Cannot set that property, header is already written");
		}
		this.statusText = statusText;
	}

	/**
	 * Setter for mime type.
	 * @param mimeType
	 * @throws RuntimeException
	 * 					if header is already generated
	 */
	public void setMimeType(String mimeType) {
		if(headerGenerated) {
			throw new RuntimeException("Cannot set that property, header is already written");
		}
		this.mimeType = mimeType;
	}

	/**
	 * Setter for cookies.
	 * @param outputCookies
	 * 					list of {@link RCCookie}
	 * @throws RuntimeException
	 * 					if header is already generated
	 */
	public void setOutputCookies(List<RCCookie> outputCookies) {
		if(headerGenerated) {
			throw new RuntimeException("Cannot set that property, header is already written");
		}
		this.outputCookies = outputCookies;
	}

	/**
	 * Adds cookie to list of cookies. 
	 * @param rcCookie
	 * 					{@link RCCookie} to be added to list
	 */
	public void addRCCookie(RCCookie rcCookie) {
		outputCookies.add(rcCookie);
	}

	/**
	 * Cookies for {@link RequestContext}.
	 * Has read-only properties name, value, domain, path and maxAge.
	 * @author Alex
	 *
	 */
	public static class RCCookie {
		
		/**
		 * Read-only name of cookie.
		 */
		private String name;
		
		/**
		 * Read-only value of cookie.
		 */
		private String value;
		
		/**
		 * Read-only domain of cookie.
		 */
		private String domain;
		
		/**
		 * Read-only path.
		 */
		private String path;
		
		/**
		 * Read-only maximum age.
		 */
		private Integer maxAge;
		
		/**
		 * Constructor of RCCookie.
		 * @param name
		 * @param value
		 * @param maxAge
		 * @param path
		 * @param domain
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}
		
		/**
		 * Gets name of cookie.
		 * @return
		 * 			name of cookie
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Gets value of cookie.
		 * @return
		 * 			value of cookie
		 */
		public String getValue() {
			return value;
		}
		
		/**
		 * Gets domain of cookie.
		 * @return
		 * 			domain of cookie
		 */
		public String getDomain() {
			return domain;
		}
		
		/**
		 * Gets path.
		 * @return
		 * 			path
		 */
		public String getPath() {
			return path;
		}
		
		/**
		 * Gets maximum age.
		 * @return
		 * 			maximum age
		 */
		public int getMaxAge() {
			return maxAge;
		}
		
	}

}
