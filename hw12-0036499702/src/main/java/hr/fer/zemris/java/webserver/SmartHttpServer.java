package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.Properties;
import java.util.Random;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import hr.fer.zemris.java.webserver.workers.BgColorWorker;
import hr.fer.zemris.java.webserver.workers.CircleWorker;
import hr.fer.zemris.java.webserver.workers.HelloWorker;
import hr.fer.zemris.java.webserver.workers.Home;
import hr.fer.zemris.java.webserver.workers.SumWorker;

/**
 * Simple server. Properties of server are found in file server.properties.
 * @author Alex
 *
 */
public class SmartHttpServer {
	
	/**
	 * Address which server listens.
	 */
	private String address;
	
	/**
	 * Domain name of this server.
	 */
	private String domainName;
	
	/**
	 * Port on which server listens.
	 */
	private int port;
	
	/**
	 * Number of worker thread.
	 */
	private int workerThreads;
	
	/**
	 * Duration of user sessions in seconds.
	 */
	private int sessionTimeout;
	
	/**
	 * Map of mime types and their output for requests.
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	
	/**
	 * Main thread of this server.
	 */
	private ServerThread serverThread;
	
	/**
	 * Thread pool in which {@link ClientWorker} are submitted.
	 */
	private ExecutorService threadPool;
	
	/**
	 * Root document of this server.
	 */
	private Path documentRoot;
	
	/**
	 * Flag for running server thread.
	 */
	private boolean run = true;
	
	/**
	 * Map of workers. 
	 * Contains: {@link BgColorWorker}, {@link CircleWorker},
	 * 			{@link HelloWorker}, {@link Home}, {@link SumWorker}.
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<>();
	
	/**
	 * Map of user sessions. Each pair is of form:
	 * 			sid : session entry
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<>();
	
	/**
	 * Random used for generating new sid.
	 */
	private Random sessionRandom = new Random();

	/**
	 * Constructor of SmartHttpServer.
	 * Reads configuration values from configFileName.
	 * @param configFileName
	 * 				path to configuration file
	 */
	public SmartHttpServer(String configFileName) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(configFileName));
		} catch (IOException ex) {
			System.err.println("Unable to load server properties!");
			System.exit(1);
		}
		
		this.address = prop.getProperty("server.address");
		this.domainName = prop.getProperty("server.domainName");
		this.port = Integer.parseInt(prop.getProperty("server.port"));
		this.workerThreads = Integer.parseInt(prop.getProperty("server.workerThreads"));
		this.sessionTimeout = Integer.parseInt(prop.getProperty("session.timeout"));

		Properties mimeProp = new Properties();
		try {
			mimeProp.load(new FileInputStream(prop.getProperty("server.mimeConfig")));
		} catch (IOException e) {
			System.err.println("Unable to load mime properties!");
			System.exit(1);
		}
		
		for(String name : mimeProp.stringPropertyNames()) {
			this.mimeTypes.put(name, mimeProp.getProperty(name));
		}
		
		this.serverThread = new ServerThread();
		this.documentRoot = Paths.get(prop.getProperty("server.documentRoot"));
		
		Properties workersProp = new Properties();
		try {
			workersProp.load(new FileInputStream(prop.getProperty("server.workers")));
		} catch (IOException e) {
			System.err.println("Unable to load workers properties!");
			System.exit(1);
		}
		
		for(String path : workersProp.stringPropertyNames()) {
			if(this.workersMap.containsKey(path)) {
				throw new IllegalArgumentException("Path " + path + "is already mapped!");
			}
			Class<?> referenceToClass;
			try {
				referenceToClass = this.getClass().getClassLoader().loadClass(workersProp.getProperty(path));
				@SuppressWarnings("deprecation")
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				this.workersMap.put(path, iww);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				System.err.println("Unable to load workers, class not found!");
				System.exit(1);
			}
		}
		
		Thread t = new Thread() {
			@Override
			public void run() {
				while(run) {
					for(SessionMapEntry entry : sessions.values()) {
						if(entry.validUntil < new Date().getTime()) {
							sessions.remove(entry.sid);
						}
					}
					
					try {
						sleep(300000);
					} catch (InterruptedException e) {
					}
				}
			}
		};
		
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Starts server thread and initializes thread pool.
	 */
	protected synchronized void start() {
		if(!serverThread.isAlive()) {
			serverThread.start();
		}
		
		threadPool = Executors.newFixedThreadPool(workerThreads, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
		});
	}

	/**
	 * Stops server thread and shuts down thread pool.
	 */
	protected synchronized void stop() {
		run = false;
		threadPool.shutdown();
	}

	/**
	 * Main thread in {@link SmartHttpServer}.
	 * @author Alex
	 *
	 */
	protected class ServerThread extends Thread {
		
		@Override
		public void run() {
			ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(address, port));
				
				List<Future<?>> clients = new ArrayList<>();
				
				while(run) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					clients.add(threadPool.submit(cw));
				}
				
				serverSocket.close();
				
			} catch (IOException e) {
				System.err.println("An error occured while working with sockets!");
				System.exit(1);
			}			
		}
	}

	/**
	 * Client of {@link SmartHttpServer}. Implements {@link Runnable} and {@link IDispatcher}.
	 * Constructs {@link RequestContext} and sends it to output stream from socket.
	 * 
	 * @author Alex
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		
		/**
		 * Socket from which input and output stream are retrieved.
		 */
		private Socket csocket;
		
		/**
		 * Input stream.
		 */
		private PushbackInputStream istream;
		
		/**
		 * Output stream.
		 */
		private OutputStream ostream;
		
		/**
		 * Version of http protocol.
		 */
		private String version;
		
		/**
		 * Method invoked in header.
		 */
		private String method;
		
		/**
		 * Host parameter.
		 */
		private String host;
		
		/**
		 * Map of parameters.
		 */
		private Map<String, String> params = new HashMap<String, String>();
		
		/**
		 * Map of temporary parameters.
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		
		/**
		 * Map of perm parameters.
		 */
		private Map<String, String> permParams = new HashMap<String, String>();
		
		/**
		 * List of cookies {@link RCCookie}.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		
		/**
		 * Session id.
		 */
		@SuppressWarnings("unused")
		private String SID;
		
		/**
		 * Current request context.
		 */
		private RequestContext context = null;

		/**
		 * Constructor of ClientWorker.
		 * @param csocket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				
				List<String> header = readLines();
				
				if(header == null || header.size() < 1) {
					sendError(ostream, 400, "invalid header");
					return;
				}
				
				String[] firstLine = header.get(0).split(" ");
				method = firstLine[0];
				String requestedPath = firstLine[1];
				version = firstLine[2];
				
				if(!method.equals("GET") || 
						(!version.equals("HTTP/1.1") && 
								!version.equals("HTTP/1.0"))){
					sendError(ostream, 400, "invalid header");
					return;
				}
				
				analyzeHeader(header);
				
				String sidCandidate = checkSession(header);
				
				if(context == null) {
					context = new RequestContext(ostream, params, permParams, 
								outputCookies);
				}
				
				if(sidCandidate == null) {
					sidCandidate = newSID();
					newSession(sidCandidate);
				} else {
					SessionMapEntry entry = sessions.get(sidCandidate);
					if(entry == null) {
						newSession(sidCandidate);
					} else if(!host.equals(entry.host)){
						newSession(sidCandidate);
					} else if (entry.validUntil < new Date().getTime()) {
						newSession(sidCandidate);
					} else {
						entry.validUntil = new Date().getTime() + sessionTimeout * 1000;
					}
				}
				
				
				permParams = sessions.get(sidCandidate).map;
				
				int index = requestedPath.indexOf("?");
								
				String path = requestedPath;
				
				if(index != -1) {
					path = requestedPath.substring(0, index);
					String paramString = requestedPath.substring(index + 1);
					parseParameters(paramString);
				}
				
				internalDispatchRequest(path, true);
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		
		/**
		 * Creates new session and adds corresponding cookie to context.
		 * @param sidCandidate
		 * 				sid of new session, key for mapping to sessions
		 */
		private synchronized void newSession(String sidCandidate) {
			String rchost = host == null ? domainName : host;
			sessions.put(sidCandidate, new SessionMapEntry(sidCandidate, rchost,
					new Date().getTime() + sessionTimeout * 1000, new ConcurrentHashMap<>()));
			context.addRCCookie(new RCCookie("sid", sidCandidate, null, rchost, "/"));
		}
		
		/**
		 * Generates new random sid.
		 * @return
		 * 				sid - string of 20 random upper case letters
		 */
		private String newSID() {
			String newsid = "";
			String letters = "ABCDEFGHIJKLMNOPQRSTUVWYZ";
			
			for(int i = 0; i < 20; i++) {
				newsid += letters.charAt(sessionRandom.nextInt(letters.length()));
			}
			
			return newsid;
		}

		/**
		 * Checks if session is specified as parameter of header.
		 * @param header 
		 * 				header of request
		 * @return
		 * 				sid - if it is specified
		 * 				null - otherwise
		 */
		private String checkSession(List<String> header) {
			for(String line : header) {
				if(!line.startsWith("Cookie:")) {
					continue;
				}
				
				if(line.contains("sid")) {
					int index = line.indexOf(";") == -1 ? line.length() : line.indexOf(";");
					return line.substring(line.indexOf("=") + 1, index);
				}
			}
			return null;
			
		}

		/**
		 * Reports error to client.
		 * @param cos
		 * 				output stream
		 * @param statusCode
		 * 				status code of error
		 * @param statusText
		 * 				status text of error
		 * @throws IOException
		 */
		private void sendError(OutputStream cos, 
			int statusCode, String statusText) throws IOException {

			cos.write(
				("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
				"Server: simple java server\r\n"+
				"Content-Type: text/plain;charset=UTF-8\r\n"+
				"Content-Length: 0\r\n"+
				"Connection: close\r\n"+
				"\r\n").getBytes(StandardCharsets.US_ASCII)
			);
			cos.flush();

		}

		/**
		 * Extracts extension and returns corresponding mime type.
		 * @param requestedFile
		 * 				file whose mime type is to be determined
		 * @return
		 * 				mime type of requestedFile
		 */
		private String extractExtension(Path requestedFile) {
			String ext = requestedFile.toString().substring(
					requestedFile.toString().lastIndexOf('.') + 1);
			String mime = mimeTypes.get(ext);
			return mime == null ? "application/octet-stream" : mime;
		}

		/**
		 * Parses parameters from urlPath. 
		 * Parameters are in form: name=value
		 * and pairs are separated by '&'.
		 * @param paramString
		 * 				string to be parsed
		 */
		private void parseParameters(String paramString) {
			for(String s : paramString.split("&")) {
				int index = s.indexOf("=");
				params.put(s.substring(0, index), s.substring(index + 1));
			}
		}

		/**
		 * Checks if host is specified in header.
		 * @param header
		 */
		private void analyzeHeader(List<String> header) {
			
			for(String s : header) {
				if(s.startsWith("Host")) {
					int index = s.indexOf(":") + 1;
					int index2 = s.indexOf(":", index) != -1 ? s.indexOf(":", index) : s.length();
					host = s.substring(index, index2).trim();
					return;
				}
			}
			
			host = domainName;
		}

		/**
		 * Automate for reading header.
		 * @return 
		 * 				list of lines from header
		 * @throws IOException
		 */
		private List<String> readLines() throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
	l:		while(true) {
				int b = istream.read();
				if(b==-1) return null;
				if(b!=13) {
					bos.write(b);
				}
				switch(state) {
				case 0: 
					if(b==13) { state=1; } else if(b==10) state=4;
					break;
				case 1: 
					if(b==10) { state=2; } else state=0;
					break;
				case 2: 
					if(b==13) { state=3; } else state=0;
					break;
				case 3: 
					if(b==10) { break l; } else state=0;
					break;
				case 4: 
					if(b==10) { break l; } else state=0;
					break;
				}
			}
			
			String text = new String(bos.toByteArray());
			List<String> lines = new ArrayList<>();
			
			for(String s : text.split("\n")) {
				lines.add(s);
			}
			return lines;
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Internal dispatching of request for SmartHttpServer. 
		 * Analyzes given urlPath and produces request body.
		 * @param urlPath
		 * 				path to analyze
		 * @param directCall
		 * 				true if method is called by client
		 * 				false if method is called by server
		 * @throws Exception
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception{
			
			context = new RequestContext(ostream, params, permParams, 
							outputCookies, tempParams, this);
			
			if(urlPath.startsWith("/ext/")) {
				Class<?> referenceToClass;
				int index = urlPath.indexOf("?") == -1 ? urlPath.length() : urlPath.indexOf("?");
				String name = urlPath.substring(5, index);
				try {
					referenceToClass = this.getClass().getClassLoader().
							loadClass("hr.fer.zemris.java.webserver.workers." + name);
					@SuppressWarnings("deprecation")
					Object newObject = referenceToClass.newInstance();
					IWebWorker iww = (IWebWorker) newObject;
					iww.processRequest(context);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					System.err.println("Unable to load worker!");
					System.exit(1);
				}
			}
			
			if(directCall && urlPath.startsWith("/private")) {
				sendError(ostream, 404, "Not found");
			}
			
			if(workersMap.containsKey(urlPath.toString())) {
				workersMap.get(urlPath.toString()).processRequest(context);
				return;
			}
			
			Path requestedFile = documentRoot.resolve(Paths.get(urlPath.substring(1)));

			if(!requestedFile.startsWith(documentRoot)) {
				sendError(ostream, 403, "forbidden");
				return;
			}
			
			if(!Files.exists(requestedFile)) {
				sendError(ostream, 404, "not found");
				return;
			}
			
			String mime = extractExtension(requestedFile);
			
			if(mime.equals("smscr")) {
				String documentBody = "";
				for(String line : Files.readAllLines(requestedFile)) {
					documentBody += line + "\n";
				}
				
				SmartScriptEngine engine = new SmartScriptEngine(
						new SmartScriptParser(documentBody).getDocumentNode(), context);
				engine.execute();
				
				ostream.flush();
				ostream.close();
							
			} else {
				
				context.setMimeType(mime);
				context.setContentLength(requestedFile.toFile().length());
				
				try(InputStream fis = Files.newInputStream(requestedFile)){
					byte[] buf = new byte[1024];
					while(true) {
						int r = fis.read(buf);
						if(r < 1)
							break;
						context.write(buf, 0, r);
					}
				}
				ostream.flush();
			}
		}
	}
	
	
	/**
	 * Entry for map of sessions.
	 * @author Alex
	 *
	 */
	private static class SessionMapEntry {
		
		/**
		 * Session id.
		 */
		private String sid;
		
		/**
		 * Host of session.
		 */
		private String host;
		
		/**
		 * Time until which session is valid in milliseconds.
		 */
		private long validUntil;
		
		/**
		 * Map of parameters.
		 */
		private Map<String, String> map;
		
		/**
		 * Constructor of SessionMapEntry.
		 * @param sid
		 * @param host
		 * @param validUntil
		 * @param map
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}
	}
	
	/**
	 * Main method, starts the server.
	 * @param args
	 * 				only argument is path to configuration file
	 */
	public static void main(String[] args) {
		new SmartHttpServer(args[0]).start();
	}


}
