package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudentDatabaseTest {
	
	StudentDatabase database;

	@Before
	public void setUp() throws IOException {
		List<String> lines = Files.readAllLines(
				 Paths.get("./src/main/resources/database.txt"),
				 StandardCharsets.UTF_8
				);
		database = new StudentDatabase(lines.toArray(new String[0]));
	}
	
	private class FilterTrue implements IFilter{

		@Override
		public boolean accepts(StudentRecord record) {
			return true;
		}
		
	}
	
	private class FilterFalse implements IFilter{

		@Override
		public boolean accepts(StudentRecord record) {
			return false;
		}
		
	}

	@Test
	public void testTrue() throws IOException {
		List<StudentRecord> list = database.filter(new FilterTrue());
		assertTrue(list.size() == database.size());
	}

	@Test
	public void testFalse() throws IOException {
		List<StudentRecord> list = database.filter(new FilterFalse());
		assertTrue(list.size() == 0);
	}
}
