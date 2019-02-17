package hr.fer.zemris.java.custom.collections;

/**
 * Class used to describe a stack. This class is adaptor for class ArrayIndexedCollection.
 * @author Alex
 *
 */
public class ObjectStack {

	/**
	 * Collection used to store stack items.
	 */
	private ArrayIndexedCollection collection;
	
	/**
	 * Creates new empty stack.
	 */
	public ObjectStack() {
		this.collection = new ArrayIndexedCollection();
	}	
	
	/**
	 * Checks if the stack is empty.
	 * @return true if empty
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
	 * Determines size of the stack (number of elements in stack).
	 * @return size of stack
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Puts new value on the top of the stack.
	 * @param value
	 * @throws NullPointerException if value is null
	 */
	public void push(Object value) {
		if(value == null) {
			throw new NullPointerException("Can't put value null on stack");
		}
		collection.add(value);
	}
	
	/**
	 * Removes value from the top of the stack.
	 * @return value from the top of the stack
	 * @throws EmptyStackException if stack is empty
	 */
	public Object pop() {
		if(collection.isEmpty()) {
			throw new EmptyStackException("Can't pop value from empty stack");
		}
		
		Object object = collection.get(collection.size() - 1);
		collection.remove(collection.size() - 1);
		return object;
	}
	
	/**
	 * Gets object from the top of the stack, but it doesn't remove it.
	 * @return object from the top of the stack
	 * @throws EmptyStackException if stack is empty
	 */
	public Object peek() {
		if(collection.isEmpty()) {
			throw new EmptyStackException("Can't peek value from empty stack");
		}
		
		return collection.get(collection.size() - 1);
	}
	
	/**
	 * Removes all values from the stack.
	 */
	public void clear() {
		collection.clear();
	}
}
