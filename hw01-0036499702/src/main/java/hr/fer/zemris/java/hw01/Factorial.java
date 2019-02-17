package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Razred Factorial omogucava korisnicima da izracunaju faktorijel cijelog broja 
 * unesenog preko tipkovnice
 * 
 * Program se zavrsava kad korisnik unese 'kraj'
 * 
 * @author Alex
 *
 */

public class Factorial {
	
	/**
	 * Staticka metoda koja racuna faktorijel zadanog cijelog broja
	 * 
	 * @param n mora biti cijeli broj iz intervala [1, 20]
	 * @return vraca cijeli broj koji je jednak n!
	 */
	
	public static int calculateFactorial(int n) {
		int factorial = 1;
		
		for(int i = 1; i <= n; i++) {
			factorial *= i;
		}
		
		return factorial;
	}
	
	/**
	 * Staticka metoda koja provjerava ispravnost unesenog broja prije nego sto se
	 * pozove metoda za izracun faktorijela i generira ispis
	 * 
	 * @param s je String koji smo procitali sa tipkovnice (ulaza)
	 * @return ispis nakon obavljene provjere i izracuna, vraca odgovarajuce 
	 * poruke ako uneseni niz znakova nije bio cijeli broj iz intervala [0, 20]
	 */
	
	public static String factorials(String s) {
		
		StringBuilder str = new StringBuilder();
		
		try {
			int n = Integer.parseInt(s);
			
			if(n < 1 || n > 20) {
				str.append("'").append(n).append("' nije broj u dozvoljenom rasponu");
			} else {
				str.append(n).append("! = ").append(calculateFactorial(n));
			}
		} catch(NumberFormatException ex) {
			if(s.equals("kraj")) {
				str.append("Doviđenja.");
			} else {
				str.append("'").append(s).append("' nije cijeli broj");
			}
		}
		
		return str.toString();
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("Unesite broj > ");
			String s = sc.next();
						
			String output = factorials(s);
			System.out.println(output);
			if(output.equals("Doviđenja.")) {
				sc.close();
				return;
			}
		}
		
		
	}

}
