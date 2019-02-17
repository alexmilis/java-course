package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that demonstrates manipulating streams.
 * Implements 8 functions that process data from stream.
 * @author Alex
 *
 */
public class StudentDemo {

	/**
	 * Main method that demonstrates work of methods.
	 * @param args not needed here.
	 * @throws IOException if data file cannot be read.
	 */
	public static void main(String[] args) throws IOException {
		
		List<String> lines = Files.readAllLines(
				 Paths.get("./src/main/resources/studenti.txt"),
				 StandardCharsets.UTF_8
				);
		
		List<StudentRecord> records = convert(lines);
		
		long broj = vratiBodovaViseOd25(records);
		System.out.println("Broj studenata sa vise od 25 bodova: " + broj);
		
		long broj5 = vratiBrojOdlikasa(records);
		System.out.println("Broj odlikasa: " + broj5);
		
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		System.out.println("\nOdlikasi:");
		output(odlikasi);
		
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		System.out.println("\nSortirani odlikasi:");
		output(odlikasiSortirano);
		
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		System.out.println("\nJmbagovi studenata koji nisu polozili: ");
		System.out.println(nepolozeniJMBAGovi.toString());
		
		
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		System.out.println("\nPodjela studenata po ocjenama");
		for(Map.Entry<Integer, List<StudentRecord>> entry : mapaPoOcjenama.entrySet()) {
			System.out.println("Studenti s ocjenom " + entry.getKey() + ":");
			output(entry.getValue());
		}
		
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		System.out.println("\nPodjela studenata po ocjenama");
		for(Map.Entry<Integer, Integer> entry : mapaPoOcjenama2.entrySet()) {
			System.out.println("Broj studenti s ocjenom " + entry.getKey() + ": " + entry.getValue());
		}
		
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		System.out.println("\nPodjela studenata prolaz/pad:");
		System.out.println("Prolaz:");
		output(prolazNeprolaz.get(true));
		System.out.println("Pad:");
		output(prolazNeprolaz.get(false));
		
	}

	/**
	 * Converts list of read lines to list of {@link StudentRecord}.
	 * @param lines read from file.
	 * @return list of student records.
	 */
	private static List<StudentRecord> convert(List<String> lines){
		List<StudentRecord> result = new LinkedList<>();
		for(String s : lines) {
			String[] parts = s.split("\t");
			result.add(new StudentRecord(
					parts[0], parts[1], parts[2],
					Double.parseDouble(parts[3]),
					Double.parseDouble(parts[4]),
					Double.parseDouble(parts[5]),
					Integer.parseInt(parts[6])));
		}
		return result;
	}
	
	/**
	 * Counts students that have more than 25 points from MI + ZI + lab.
	 * @param records list of student records.
	 * @return number of students with more than 25 points.
	 */
	public static long vratiBodovaViseOd25(List<StudentRecord> records){
		return records.stream().filter(
				record -> record.getMi() + record.getZi() + record.getLab() > 25
				).count();
	}
	
	/**
	 * Counts students that have grade 5.
	 * @param records list of student records.
	 * @return number of students with grade 5.
	 */
	public static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(record -> record.getOcjena() == 5).count();
	}
	
	/**
	 * Creates a list of students whose grade is 5.
	 * @param records list of student records.
	 * @return list of student records.
	 */
	public static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(record -> record.getOcjena() == 5).collect(Collectors.toList());
	}
	
	/**
	 * Creates sorted list of students whose grade is 5.
	 * @param records list of student records.
	 * @return sorted list of student records.
	 */
	public static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records){
		return records.stream().filter(record -> record.getOcjena() == 5)
				.sorted((r1, r2) -> (int) (r2.getMi() + r2.getZi() + r2.getLab() - r1.getMi() - r1.getZi() - r1.getLab()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Creates list of jmbag's of students who failed.
	 * @param records list of student records.
	 * @return list of jmbag's.
	 */
	public static List<String> vratiPopisNepolozenih(List<StudentRecord> records){
		return records.stream().filter(record -> record.getOcjena() == 1)
				.map(record -> record.getJmbag())
				.sorted((j1, j2) -> j1.compareTo(j2))
				.collect(Collectors.toList());
	}
	
	/**
	 * Sorts students by grade.
	 * @param records list of student records.
	 * @return map of students mapped by grade.
	 */
	public static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records){
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getOcjena));
	}
	
	/**
	 * Counts how many students have which grade.
	 * @param records list of student records.
	 * @return map of grades and number of students that have that grade.
	 */
	public static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records){
		return records.stream().collect(
				Collectors.toMap(
						StudentRecord::getOcjena, 
						StudentRecord::getOne, 
						(v1, v2) -> v1 + v2));
	}
	
	/**
	 * Sorts student who failed or passed.
	 * @param records list of student records.
	 * @return map of students divided by passing or failing.
	 */
	public static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records){
		return records.stream().collect(Collectors.partitioningBy(record -> record.getOcjena() > 1));
	}
	
	/**
	 * Prints out a list.
	 * @param list to be printed.
	 */
	private static <K> void output(List<K> list) {
		for(K object : list) {
			System.out.println(object.toString());
		}
	}
}
