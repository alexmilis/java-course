package hr.fer.zemris.java.hw16.trazlica;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;

/**
 * Console that allows user to enter a path to directory he wishes to search. 
 * During the startup of console, vocabulary is created by parsing all documents from given directory.
 * Each document is represented by an instance of {@link IDFVector} that is built during startup.
 * 
 * Supported commands are:
 * 		query arg1 arg2 ... - searches for 10 most similar documents
 * 		type n - writes contents of file under index n in results of last query
 * 		results - prints out results of last query
 * 		exit - exits the console and ends the program
 * 
 * Similarity between documents is calculated based on their tf-idf vectors.
 * TF-IDF (term frequency - inverse document frequency) vectors are chosen 
 * representation of documents in this implementation of search engine.
 * 
 * @author Alex
 *
 */
public class Konzola {
	
	/**
	 * Map that stores word as key and number of documents in which that word appears as value.
	 */
	private static Map<String, Integer> frequencies = new HashMap<>();
	
	/**
	 * Vocabulary that contains all significant words from all files in given directory.
	 */
	private static List<String> vocabulary = new ArrayList<>();
	
	/**
	 * Predefined stop words.
	 */
	private static Set<String> stopWords = new HashSet<>();
	
	/**
	 * Map that stores documents and their {@link IDFVector} representations.
	 */
	private static Map<String, IDFVector> vectors = new HashMap<>();
	
	/**
	 * Map used for storing 10 best matches from last query.
	 */
	private static Map<Integer, Result> results = new LinkedHashMap<>();
	
	/**
	 * Help vector that has calculated values of idf component for each word in vocabulary.
	 */
	private static IDFVector help;
	
	/**
	 * Number of documents in directory.
	 */
	private static double noOfDocs = 0.0;
	
	/**
	 * Section separator in console.
	 */
	private static final String SEPARATOR = new String(new char[50]).replaceAll("\0", "-");
	
	static {
		Path path = Paths.get("hrvatski_stoprijeci.txt");
		try {
			for(String line : Files.readAllLines(path, Charset.forName("UTF-8"))) {
				stopWords.add(line.toLowerCase());
			}
		} catch (IOException e) {
			System.out.println("An error ocured while loading vocabulary, please restart the program");
			System.exit(1);
		}
	}
	
	/**
	 * Function that parses documents and builds vocabulary. 
	 * It also stores words and number of documents that contain them in map frequencies.
	 * In this context, word is every sequence of characters for which
	 * function Character.isAlphabetic() returns true.
	 */
	private static Function<File, Void> parse = new Function<File, Void>(){

		@Override
		public Void apply(File t) {
			try {
				noOfDocs++;
				Set<String> bagOfWords = new HashSet<>();
				for(String line : Files.readAllLines(t.toPath(), Charset.forName("UTF-8"))) {
					String word = "";
					for(char c : line.toCharArray()) {
						if(Character.isAlphabetic(c) || c == '-') {
							word += c;
						} else {
							if(word.isEmpty()) {
								continue;
							}
							
							if(!stopWords.contains(word.toLowerCase())) {
								bagOfWords.add(word.toLowerCase());
							}
							
							word = "";
						}
					}
				}
				
				
				bagOfWords.forEach(s -> {
					if(frequencies.containsKey(s)) {
						frequencies.put(s, frequencies.get(s) + 1);
					} else {
						frequencies.put(s, 1);
						vocabulary.add(s);
					}
				});
				
			} catch (IOException e) {
				return null;
			}
			return null;
		}
		
	};
	
	/**
	 * Function that parses documents and builds {@link IDFVector} for each document.
	 */
	private static Function<File, Void> getVectors = new Function<>() {

		@Override
		public Void apply(File t) {
			try {
				double[] values = new double[vocabulary.size()];
				for(String line : Files.readAllLines(t.toPath())) {
					String word = "";
					for(char c : line.toCharArray()) {
						if(Character.isAlphabetic(c) || c == '-') {
							word += c;
						} else {
							if(word.isEmpty() || stopWords.contains(word.toLowerCase())) {
								word = "";
								continue;
							}
							
							values[vocabulary.indexOf(word.toLowerCase())] += 1;
							word = "";
						}
					}
				}
				
				for(int i = 0; i < values.length; i++) {
					values[i] *= Math.log10(noOfDocs / frequencies.get(vocabulary.get(i)));
				}
				
				vectors.put(t.getAbsolutePath(), new IDFVector(values));
				
			} catch (IOException e) {
				return null;
			}
			return null;
		}
		
	};
	

	/**
	 * Main function that runs the console. 
	 * It requires one argument - path to directory that is being searched.
	 * @param args
	 * 				path to directory
	 */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
			
		while(args.length != 1) {
			System.out.println("Invalid number of arguments, please give only one argument (path to document):");
			args = sc.nextLine().split(" ");
		}

		Path path = Paths.get(args[0]);
				
		tree(path.toFile(), parse);
		tree(path.toFile(), getVectors);
		
		createHelp();
		
		System.out.println("\nSize of dictionary is " + vocabulary.size() + " words.\n");

		String command = "";
		
		while(true){
			System.out.println(SEPARATOR);
			System.out.print("\nEnter command> ");
			String[] parts = sc.nextLine().split(" ");
			command = parts[0].trim();
			
			switch(command) {
			case "query":
				processQuery(parts);
				break;
			case "type":
				showType(parts);
				break;
			case "results":
				showResults();
				break;
			case "exit":
				sc.close();
				System.out.println("End of program.");
				System.exit(0);
			default:
				System.out.println("Invalid command: " + command);
				break;
			}
		}
	}
	
	/**
	 * Method that prints contents of file to console.
	 * Index of file that is being written is given as argument.
	 * Index is used to get path to file from map results.
	 * If index is invalid, it show user error message and continues with console.
	 * @param parts
	 * 				arguments given by user
	 */
	private static void showType(String[] parts) {
		if(parts.length != 2) {
			System.out.println("Invalid arguments for command type!");
			return;
		}
		
		int index;
		try {
			index = Integer.parseInt(parts[1].trim());
		} catch (NumberFormatException ex) {
			System.out.println("Argument given as index cannot be parsed into an integer!");
			return;
		}
		
		if(index < 0 || index > 9) {
			System.out.println("Result with index " + index + " does not exist.");
			return;
		}
		
		Path docPath = results.get(index).docPath;
		System.out.println("Document: " + docPath.toString());
		
		try {
			BufferedReader br = Files.newBufferedReader(docPath, Charset.forName("UTF-8"));
			String line = "";
			do {
				System.out.println(line);
				line = br.readLine();
			} while(line != null);
			br.close();
		} catch(IOException e) {
			System.out.println("An error occured while reading from file: " + docPath.toString());
		}
	}

	/**
	 * Prints to console results of last query.
	 * If no query was yet made, it shows appropriate message to user and returns to console.
	 */
	private static void showResults() {
		if(results.isEmpty()) {
			System.out.println("No query was made yet, no results to show.");
		} else {
			StringBuilder sb = new StringBuilder();
			results.forEach((k, v) -> sb.append("[").append(k).append("]").append(v.toString()).append("\n"));
			System.out.println(sb.toString());
		}
	}

	/**
	 * Creates help vector with values of idf component for each word from vocabulary.
	 */
	private static void createHelp() {
		double[] values = new double[vocabulary.size()];
		for(int i = 0; i < vocabulary.size(); i++) {
			values[i] = Math.log10(noOfDocs / frequencies.get(vocabulary.get(i)));
		}
		
		help = new IDFVector(values);
	}

	/**
	 * Processes query given by user. It parses arguments and creates {@link IDFVector} of query.
	 * If query is valid, it searches for 10 best matches in directory,
	 * otherwise it returns to console after writing appropriate message to user.
	 * 10 best matches are stored in map results for further need.
	 * @param parts
	 * 				query arguments given by user
	 */
	private static void processQuery(String[] parts) {
		List<String> query = new ArrayList<>();
		for(int i = 1; i < parts.length; i++) {
			if(!stopWords.contains(parts[i].toLowerCase())) {
				query.add(parts[i]);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Query is: [");
		query.forEach(s -> sb.append(s).append(","));
		sb.deleteCharAt(sb.length() - 1).append("]\n");
		sb.append("10 best matches:");
		
		System.out.println(sb.toString());
		
		double[] values = new double[vocabulary.size()];
		
		boolean check = true;
		for(String word : query) {
			try {
				int index = vocabulary.indexOf(word);
				values[index] = help.getValues().get(index);
				check = false;
			} catch (IndexOutOfBoundsException ex) {}
		}
		
		if(check) {
			System.out.println("None of words in query is in dictionary, query cannot be executed.");
			return;
		}
		
		IDFVector queryVector = new IDFVector(values);
		List<Result> queryResults = new ArrayList<>();
		
		for(Map.Entry<String, IDFVector> entry : vectors.entrySet()) {
			queryResults.add(new Result(queryVector.sim(entry.getValue()), Paths.get(entry.getKey())));
		}
		
		queryResults.sort((r1, r2) -> (int) (r2.sim * 10000 - r1.sim * 10000));
		
		results.clear();
		sb.delete(0, sb.length());
		
		for(int i = 0; i < 10; i++) {
			Result res = queryResults.get(i);
			if (res.sim == 0) {
				break;
			}
			results.put(i, res);
			sb.append("[").append(i).append("]").append(res.toString()).append("\n");
		}
		
		System.out.println(sb.toString());
	}

	/**
	 * Recursive method that passes all documents in root directory and it's subdirectories.
	 * Function function is applied to each found document.
	 * @param root
	 * 				directory to be searched
	 * @param function
	 * 				function to be applied to documents
	 */
	private static void tree(File root, Function<File, Void> function) {
		if(Files.isDirectory(root.toPath())) {
			for(File child : root.listFiles()) {
				tree(child, function);
			}
		} else if(root.canRead()) {
			function.apply(root);
		}
	}
	
	/**
	 * Class that represents result of query. 
	 * It stores similarity between document and query and path to document.
	 * 
	 * @author Alex
	 *
	 */
	private static class Result {
		
		/**
		 * Similarity between document and query.
		 */
		private double sim;
		
		/**
		 * Absolute path to document.
		 */
		private Path docPath;
		
		/**
		 * Constructor of result.
		 * @param sim
		 * 				similarity between document and query
		 * @param docPath
		 * 				path to document
		 */
		public Result(double sim, Path docPath) {
			super();
			this.sim = sim;
			this.docPath = docPath;
		}
		
		@Override
		public String toString() {
			return "(" + String.format("%.4f", sim) + ") " + docPath.toString();
		}
	}
}
