package hr.fer.zemris.java.custom.collections;

/**
 * Class which is used to describe basic methods 
 * that must be implemented in collection of any kind.
 * @author Alex
 *
 */

public class Collection {
	
	/**
	 * Protected constructor which is used only by 
	 * constructors of classes that extend this class
	 */
	protected Collection(){
	}
	
	/**
	 * Determines whether collection is empty or not.
	 * @return true - collection is empty
	 */
	public boolean isEmpty() {
		return size() == 0 ? true : false;
	}
	
	/**
	 * Determines the size of the collection.
	 * @return size
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds object value to the collection.
	 * @param value object that should be added
	 */
	public void add(Object value) {
	}
	
	/**
	 * Removes object value from collection.
	 * @param value object that should be removed
	 * @return true if object is removed
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Converts collection to an array of objects.
	 * @return array of elements from collection
	 * @throws UnsupportedOperationException is not overridden
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Does specified action for every element in collection.
	 * @param processor processor which is used to carry out said action
	 */
	public void forEach(Processor processor) {
	}
	
	/**
	 * Adds all elements from one collection to collection which invoked it.
	 * @param other collection from which the elements are added, it remains unchanged
	 */
	public void addAll(Collection other) {
		
		/**
		 * Local class that extends Processor and implements method process 
		 * which is used in this method
		 * @author Alex
		 *
		 */
		class LocalProcessor extends Processor{
			
			/**
			 * Defines what action should be carried out by this processor.
			 * Adds value to the collection.
			 */
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		LocalProcessor local = new LocalProcessor();
		other.forEach(local);
	}
	
	/**
	 * Removes all elements from collection.
	 */
	public void clear() {
	}
}
