package hr.fer.zemris.java.hw06.demo2;

/**
 * Second example from instructions.
 * @author Alex
 *
 */
public class PrimesDemo2 {

	/**
	 * Second example from instructions.
	 * @param args not needed here.
	 */
	public static void main(String[] args) {
		
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}

}
