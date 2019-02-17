package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Database of {@link StudentRecord}. Reads records from file.
 * @author Alex
 *
 */
public class StudentDatabase {
	
	/**
	 * Map in which records are stored with jmbag as key.
	 */
	private SimpleHashtable<String, StudentRecord> table;
	
	/**
	 * Constructor of this class.
	 * @param lines
	 */
	public StudentDatabase(String[] lines) {
		table = new SimpleHashtable<>(lines.length);
		for(String line : lines) {
			String[] parts = line.split("\\t");
			StudentRecord record = new StudentRecord(parts[0].trim(), parts[1].trim(), parts[2].trim(), Integer.parseInt(parts[3].trim()));
			table.put(parts[0], record);
		}
	}
	
	/**
	 * Gets student record specified by jmbag with complexity O(1).
	 * @param jmbag
	 * @return
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return table.get(jmbag);
	}
	
	/**
	 * Filters database and returns only student records which satisfy filter.
	 * @param filter
	 * @return list of student records.
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> list = new ArrayList<>();
		Iterator<SimpleHashtable.TableEntry<String, StudentRecord>> iterator = table.iterator();
		while(iterator.hasNext()) {
			StudentRecord record = iterator.next().getValue();
			if(filter.accepts(record)) {
				list.add(record);
			}
		}
		return list;
	}
	
	/**
	 * Number of student records in database.
	 * @return
	 */
	public int size() {
		return table.size();
	}
}
