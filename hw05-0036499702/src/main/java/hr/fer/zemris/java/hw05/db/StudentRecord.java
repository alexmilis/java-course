package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Class that represents a student.
 * @author Alex
 *
 */
public class StudentRecord {
	
	/**
	 * Jmbag of student, it is unique for every student.
	 */
	private String jmbag;
	
	/**
	 * Student's last name.
	 */
	private String lastName;
	
	/**
	 * Student's first name.
	 */
	private String firstName;
	
	/**
	 * Student's final grade.
	 */
	private int finalGrade;
	
	/**
	 * Constructor of this class.
	 * @param jmbag string
	 * @param lastName string
	 * @param firstName string
	 * @param finalGrade int
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = Objects.requireNonNull(jmbag);
		this.lastName = Objects.requireNonNull(lastName);
		this.firstName = Objects.requireNonNull(firstName);
		this.finalGrade = Objects.requireNonNull(finalGrade);
	}

	/**
	 * Hashcode is calculated with field jmbag.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Equals is implemented with field jmbag.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null) {
				return false;
			}
		} else if (!jmbag.equals(other.jmbag)) {
			return false;
		}
		return true;
	}

	/**
	 * Getter  for field jmbag.
	 * @return string jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for field last name;
	 * @return string last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for first name.
	 * @return first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for final grade.
	 * @return iint final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
}
