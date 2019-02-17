package hr.fer.zemris.java.hw01;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

/**
 * Razred Rectangle sadrzi metode za izracun opsega i povrsine pravokutnika.
 * 
 * @author Alex
 *
 */

public class Rectangle {
	
	/**
	 * Metoda area vraca povrsinu pravokutnika.
	 * 
	 * @param w sirina
	 * @param h visina
	 * @return povrsina pravokutnika
	 */
	public static double area(double w, double h) {
		return w * h;
	}
	
	
	/**
	 * Metoda perimeter vraca opseg pravokutnika
	 * 
	 * @param w sirina
	 * @param h visina
	 * @return opseg pravokutnika
	 */
	public static double perimeter(double w, double h) {
		return 2 * w + 2 * h;
	}
	
	
	/**
	 * Metoda sluzi za unos sirine. Unos se ponavlja sve dok se ne unese zadovoljavajuci argument.
	 * @return sirina pravokutnika
	 */
	public static double getWidth(Scanner sc) {
		while(true) {
			System.out.print("Unesite širinu > ");
			String s = sc.next();
			try {
				double w = Double.parseDouble(s);
				
				if(w <= 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
					continue;
				}
				
				return w;
			} catch (Exception e) {
				System.out.println("'" + s + "' se ne može protumačiti kao broj.");
				continue;
			}
		}
	}
	
	
	/**
	 * Metoda sluzi za unos visine. Unos se ponavlja sve dok se ne unese zadovoljavajuci argument.
	 * @return visina pravokutnika
	 */
	public static double getHeight(Scanner sc) {
		while(true) {
			System.out.print("Unesite visinu > ");
			String s = sc.next();
			try {
				double h = Double.parseDouble(s);
				
				if(h <= 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
					continue;
				}

				return h;
			} catch (Exception e) {
				System.out.println("'" + s + "' se ne može protumačiti kao broj.");
				continue;
			}
		}
	}
	
	
	/**
	 * Metoda generira ispis trazen u zadatku. Poziva metode area i perimeter tijekom svog izvodenja.
	 * @param w sirina pravokutnika
	 * @param h visina pravokutnika
	 */
	public static void output(double w, double h) {
		StringBuilder str = new StringBuilder();
		NumberFormat formatter = new DecimalFormat("#0.0"); //string format
		
		str.append("Pravokutnik širine ").append(formatter.format(w)).append(" i visine ")
			.append(formatter.format(h)).append(" ima površinu ").append(formatter.format(area(w, h)))
			.append(" te opseg ").append(formatter.format(perimeter(w, h))).append(".");
		
		System.out.println(str.toString());
	}
	
	public static void main(String[] args) {
		
		double w, h;
		
		if(args.length == 0) {
			
			Scanner sc = new Scanner(System.in);
			
			w = getWidth(sc);
			h = getHeight(sc);
			
			sc.close();
			output(w, h);
			return;

		} else if (args.length == 2) {
			
			try {
				w = Double.parseDouble(args[0]);
				
				if(w <= 0) {
					System.out.println("Unijeli ste negativnu vrijednost kao širinu. Program ce završiti s izvođenjem.");
					return;
				}
			} catch (Exception ex) {
				System.out.println("Argument koji ste naveli kao širinu nije broj. Program će završiti s izvođenjem.");
				return;
			}
			
			try {
				h = Double.parseDouble(args[1]);
				
				if(h <= 0) {
					System.out.println("Unijeli ste negativnu vrijednost kao visinu. Program ce završiti s izvođenjem.");
					return;
				}
			} catch (Exception ex) {
				System.out.println("Argument koji ste naveli kao visinu nije broj. Program će završiti s izvođenjem.");
				return;
			}
			
			output(w, h);
			
		} else {
			System.out.println("Nije predan odgovarajući broj argumenata. Program će završiti s izvođenjem.");
		}
	}
}
