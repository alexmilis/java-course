package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Class used to define a collection based on an indexed array. Implements inherited methods, 
 * has some specific methods as well
 * @author Alex
 *
 */

public class ArrayIndexedCollection extends Collection {
	 /**
	  * Array of objects that represents elements.
	  */
	private Object elements[];
	
	/**
	 * Number of objects in array.
	 */
	private int size;
	
	/**
	 * Capacity of array.
	 */
	private int capacity;
	
	
	/**
	 * Default capacity of array.
	 */
	private final static int DEFAULT_CAPACITY = 16;
	
	/**
	 * Lowest accesible index of array.
	 */
	private final static int LOWEST_INDEX = 0;
	
	/**
	 * Lowest capacity of array.
	 */
	private final static int LOWEST_CAPACITY = 1;
	
	/**
	 * Index -1 is returned iv value is not found in array.
	 */
	private final static int VALUE_NOT_FOUND = -1;
	
	/**
	 * Creates an ArrayIndexedCollection with given initial capacity.
	 * @param initialCapacity can be integer bigger than 0
	 * @throws IllegalArgumentException if initialCapacity < 0
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < LOWEST_CAPACITY) {
			throw new IllegalArgumentException("Capacity can't be " + initialCapacity);
		}

		this.capacity = initialCapacity;
		this.elements = new Object[initialCapacity];
		this.size = 0;
	}
	
	/**
	 * Creates an ArrayIndexedCollection of given initial capacity that contains elements from another collection.
	 * @param other collection from which the elements are copied
	 * @param initialCapacity can be integer bigger than 0
	 * @throws IllegalArgumentException if initialCapacity < 0
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(initialCapacity);
		addAll(other);
	}
	
	/**
	 * Creates an ArrayIndexedCollection that contains elements from another collection.
	 * @param other collection from which the elements are copied
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}
	
	/**
	 * Creates an ArrayIndexedCollection with capacity 16.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}
		
	
	//inherited methods
	
	/**
	 * Checks if value is in array.
	 * @param value
	 * @return true if value is found
	 */
	public boolean contains(Object value) {
		if(indexOf(value) == VALUE_NOT_FOUND) {
			return false;
		}
		return true;
	}
	
	/**
	 * Determines the size of the collection.
	 * @return size
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Adds object value to the collection. 
	 * If capacity is full, it reallocates a new array twice the capacity.
	 * @param value object that should be added
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		if(value.equals(null)) throw new NullPointerException("Value null can't be added to collection");
		
		sizeCheck();
		elements[size] = value;
		size++;
	}
	
	/**
	 * Removes object at given index from collection.
	 * @param index index of value that should be removed
	 * @throws IndexOutOfBoundsException if index is not from [0, size - 1]
	 */
	public void remove(int index) {
		if(index < LOWEST_INDEX || index > size - 1) {
			throw new IndexOutOfBoundsException("Can't get element at index " + index + " because elements' size is " + size);
		}
		shift(index, false);
		size--;
	}
	
	/**
	 * Removes object value from collection.
	 * @param value object that should be removed
	 * @throws IllegalArgumentException if value is not in collection
	 */
	@Override
	public boolean remove(Object value) {
		if(indexOf(value) == VALUE_NOT_FOUND) {
			throw new IllegalArgumentException("Value " + value + " is not in collection, can't remove it");
		}
		
		int sizeCheck = size();
		remove(indexOf(value));
		if(sizeCheck > size()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Converts collection to an array of objects.
	 * @return array of elements from collection
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}
	
	/**
	 * Calls method Processor.process() for every elements in collection.
	 */
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Removes all elements from collection.
	 */
	@Override
	public void clear() {
		for(int i = LOWEST_INDEX; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	
	//methods

	/**
	 * Gets object from collection on position index.
	 * @param index position of object in collection
	 * @return object
	 * @throws IndexOutOfBoundsException if index is not from [0, size - 1]
	 */
	public Object get(int index) {
		if(index < LOWEST_INDEX || index > size - 1) {
			throw new IndexOutOfBoundsException("Can't get element at index " + index + " because elements' size is " + size);
		}
		return elements[index];
	}

	/**
	 * Inserts value at position in collection. 
	 * @param value that should be inserted
	 * @param position where value should be inserted
	 * @throws NullPointerException if value is null
	 * @throws ArrayIndexOutOfBoundsException if position is not from [0, size]
	 */
	public void insert(Object value, int position) {
		if(value == null) {
			throw new NullPointerException("Can't add null value to collection");
		} else if(position < LOWEST_INDEX || position > size - 1) {
			throw new ArrayIndexOutOfBoundsException("Can't insert value at index " + position + " because size is " + size);
		}
		
		sizeCheck();	
		shift(position, true);
		elements[position] = value;
		size++;
	}
	
	/**
	 * Finds index of first occurrence of value in collection.
	 * @param value object for whose index this method searches
	 * @return index of value, if value is not found returns -1
	 */
	public int indexOf(Object value) {
		if(value == null) {
			return VALUE_NOT_FOUND;
		}
		
		for(int i = LOWEST_INDEX; i < size; i++) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		
		return VALUE_NOT_FOUND;
	}
	
	//help methods
	
	/**
	 * Checks if size of array is equal or bigger than its capacity.
	 * Reallocates the array if needed.
	 */
	private void sizeCheck() {
		if(size >= capacity) {
			capacity *= 2;
			elements = Arrays.copyOf(elements, capacity);
		}
	}
	
	/**
	 * Shifts elements in array.
	 * @param position starting index for shift
	 * @param direction true - shift to right, false - shift to left
	 */
	private void shift(int position, boolean direction) {
		if(direction) {
			for(int i = size - 1; i > position; i--) {
				elements[i] = elements[i-1];
			}
		} else {
			for(int i = position; i < size - 1; i++) {
				elements[i] = elements[i + 1];
			}
			elements[size - 1] = null;
		}
	}
}
