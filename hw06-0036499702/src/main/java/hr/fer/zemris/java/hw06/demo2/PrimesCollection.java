package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Collection that generates consecutive prime numbers.
 * @author Alex
 *
 */
public class PrimesCollection implements Iterable<Integer>{
	
	/**
	 * Number of primes to be generated.
	 */
	private int size;

	/**
	 * Constructor of PrimesCollection.
	 * @param size
	 */
	public PrimesCollection(int size) {
		super();
		this.size = size;
	}

	/**
	 * Iterator of this collection.
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Private implementation of iterator for this collection.
	 * @author Alex
	 *
	 */
	private class IteratorImpl implements Iterator<Integer> {

		/**
		 * Index of current prime number.
		 */
		private int currentIndex = 0;
		
		/**
		 * Current prime number. First prime number is 2.
		 */
		private int currentPrime = 2;
		
		/**
		 * Checks if size from collection has already been reached.
		 */
		@Override
		public boolean hasNext() {
			return currentIndex < size;
		}

		/**
		 * Calculates next prime number.
		 */
		@Override
		public Integer next() {
			if(!hasNext()) {
				throw new NoSuchElementException("No next element to iterate");
			}
			
			while(!isPrime(currentPrime)) {
				currentPrime++;
			}
			
			currentIndex++;
			return currentPrime++;
		}
		
		/**
		 * Checks if number is prime.
		 * @param prime number that has to be checked.
		 * @return true if number is prime.
		 */
		private boolean isPrime(int prime) {
			for(int i = 2; i <= Math.sqrt(prime); i++) {
				if(prime % i == 0) {
					return false;
				}
			}
			return true;
		}
		
	}

}
