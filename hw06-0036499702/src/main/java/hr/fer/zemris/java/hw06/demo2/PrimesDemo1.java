package hr.fer.zemris.java.hw06.demo2;

/**
 * First example from instructions.
 * @author Alex
 *
 */
public class PrimesDemo1 {

	/**
	 * First example from instructions.
	 * @param args not needed here.
	 */
	public static void main(String[] args) {

		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for(Integer prime : primesCollection) {
		 System.out.println("Got prime: "+prime);
		}
		
	}

}
