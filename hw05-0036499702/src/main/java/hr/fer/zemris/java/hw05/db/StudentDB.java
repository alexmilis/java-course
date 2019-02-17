package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class used to demonstrate how database and all implemented classes and interfaces work.
 * @author Alex
 *
 */
public class StudentDB {

	/**
	 * Main method.
	 * @param args not needed.
	 * @throws IOException if file cannot be read.
	 */
	public static void main(String[] args) throws IOException {

		List<String> lines = Files.readAllLines(
				 Paths.get("./src/main/resources/database.txt"),
				 StandardCharsets.UTF_8
				);
		
		StudentDatabase db = new StudentDatabase(lines.toArray(new String[0]));
		Scanner sc = new Scanner(System.in);
		
		while(sc.hasNext()) {
			String line = sc.nextLine();
			
			if(line.equals("exit")) {
				System.out.println("Goodbye!");
				sc.close();
				return;
			}
			String command = line.substring(0, line.indexOf(" "));
			
			if(!command.equals("query")) {
				sc.close();
				throw new IllegalArgumentException("Illegal command: " + command);
			}
			
			QueryParser parser = new QueryParser(line.substring(line.indexOf(" ")));
			
			if(parser.isDirectQuery()) {
				List<StudentRecord> list = new ArrayList<>();
				list.add(db.forJMBAG(parser.getQueriedJMBAG()));
				output(list);
			} else {
				QueryFilter filter = new QueryFilter(parser.getQuery());
				output(db.filter(filter));
			}
		}
		sc.close();
	}
	
	/**
	 * Prints out table of filtered records.
	 * @param list filtered records.
	 */
	private static void output(List<StudentRecord> list) {
		
		if(list.isEmpty()) {
			System.out.println("Records selected: 0");
			return;
		}
		
		int l1 = 0;
		int l2 = 0;
		int l3 = 0;
		int l4 = 3;
		
		for(StudentRecord record : list) {
			if(record.getJmbag().length() > l1) {
				l1 = record.getJmbag().length();
			}
			if(record.getLastName().length() > l2) {
				l2 = record.getLastName().length();
			}
			if(record.getFirstName().length() > l3) {
				l3 = record.getFirstName().length();
			}
		}
		
		l1 += 2;
		l2 += 2;
		l3 += 2;
		
		StringBuilder sb = new StringBuilder();
		String margins = getMargins(l1, l2, l3, l4);
		sb.append(margins);
		
		for(StudentRecord record : list) {
			sb.append("| ").append(record.getJmbag()).append(repeat(l1 - record.getJmbag().length() - 1, " "))
			.append("| ").append(record.getLastName()).append(repeat(l2 - record.getLastName().length() - 1, " "))
			.append("| ").append(record.getFirstName()).append(repeat(l3 - record.getFirstName().length() - 1, " "))
			.append("| ").append(record.getFinalGrade()).append(repeat(l4 - 2, " ")).append("|\n");
		}
		
		sb.append(margins).append("Records selected: " + list.size());
		System.out.println(sb.toString());
		
	}
	
	/**
	 * Returns string that has been repeated n times.
	 * @param n number of repetitions.
	 * @param s string that should be repeated.
	 * @return repeated string.
	 */
	private static String repeat(int n, String s) {
		return new String(new char[n]).replace("\0", s);
	}
	
	/**
	 * Constructs margins of filtered table of student records.
	 * @param l1 size of first column.
	 * @param l2 size of second column.
	 * @param l3 size of third column.
	 * @param l4 size of fourth column.
	 * @return
	 */
	private static String getMargins(int l1, int l2, int l3, int l4) {
		StringBuilder sb = new StringBuilder();
		sb.append("+").append(repeat(l1, "="))
		.append("+").append(repeat(l2, "="))
		.append("+").append(repeat(l3, "="))
		.append("+").append(repeat(l4, "=")).append("+").append("\n");
		return sb.toString();
	}
}
