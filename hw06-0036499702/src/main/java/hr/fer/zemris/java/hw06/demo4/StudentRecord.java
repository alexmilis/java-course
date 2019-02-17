package hr.fer.zemris.java.hw06.demo4;

/**
 * Class that represents a student.
 * @author Alex
 *
 */
public class StudentRecord {
	
	/**
	 * Jmbag of student. It is unique for each student.
	 */
	private String jmbag;
	
	/**
	 * Last name of student.
	 */
	private String prezime;
	
	/**
	 * First name of student.
	 */
	private String ime;
	
	/**
	 * Points from MI.
	 */
	private double mi;
	
	/**
	 * Points from ZI.
	 */
	private double zi;
	
	/**
	 * Points from lab.
	 */
	private double lab;
	
	/**
	 * Final grade.
	 */
	private int ocjena;
	
	/**
	 * Constructor of student record.
	 * @param jmbag
	 * @param prezime
	 * @param ime
	 * @param mi
	 * @param zi
	 * @param lab
	 * @param ocjena
	 */
	public StudentRecord(String jmbag, String prezime, String ime, double mi, double zi, double lab, int ocjena) {
		super();
		this.jmbag = jmbag;
		this.prezime = prezime;
		this.ime = ime;
		this.mi = mi;
		this.zi = zi;
		this.lab = lab;
		this.ocjena = ocjena;
	}

	/**
	 * Getter for jmbag.
	 * @return string jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for prezime.
	 * @return string prezime
	 */
	public String getPrezime() {
		return prezime;
	}

	/**
	 * Getter for ime.
	 * @return string ime
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * Getter for points from MI.
	 * @return double points
	 */
	public double getMi() {
		return mi;
	}

	/**
	 * Getter for points from ZI.
	 * @return double points
	 */
	public double getZi() {
		return zi;
	}

	/**
	 * Getter for points from lab.
	 * @return double points
	 */
	public double getLab() {
		return lab;
	}

	/**
	 * Getter for ocjena.
	 * @return int ocjena
	 */
	public int getOcjena() {
		return ocjena;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(jmbag).append("\t")
		.append(prezime).append("\t")
		.append("\t").append(ime)
		.append("\t").append(mi)
		.append("\t").append(zi)
		.append("\t").append(lab)
		.append("\t").append(ocjena);
		return sb.toString();
	}
	
	/**
	 * Help function used in {@link StudentDemo} for method vratiBrojStudenataPoOcjenama.
	 * @return
	 */
	public int getOne() {
		return 1;
	}
}
