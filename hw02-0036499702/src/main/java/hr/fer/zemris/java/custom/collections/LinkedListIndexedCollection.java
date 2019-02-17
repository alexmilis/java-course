package hr.fer.zemris.java.custom.collections;

/**
 * Class used to define a collection based on an indexed double linked list.
 * Implements inherited methods, has some specific methods as well.
 * @author Alex
 *
 */

public class LinkedListIndexedCollection extends Collection {
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	static final private int LOWEST_VALID_INDEX = 0;
	static final private int VALUE_NOT_FOUND = -1;

	/**
	 * Static class used to define elements of linked list, list nodes. 
	 * Each node has attributes previous, next and value.
	 * @author Alex
	 *
	 */
	static class ListNode{
		protected ListNode previous;
		protected ListNode next;
		protected Object value;
		
		/**
		 * Creates list node.
		 * @param previous previous list node
		 * @param next next list node
		 * @param value object
		 */
		private ListNode(ListNode previous, ListNode next, Object value){
			this.previous = previous;
			this.next = next;
			this.value = value;
		}
	}
	
	/**
	 * Creates empty linked list.
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * Creates linked list and fills it with elements from other collection.
	 * @param other elements to fill linked list
	 * @throws NullPointerException if other collection is null
	 */
	public LinkedListIndexedCollection(Collection other) {
		this();
		if(other == null) {
			throw new NullPointerException("Other collection can't be null");
		}
		
		addAll(other);
	}
	
	//inherited methods
	
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
	 * @param value object that should be added
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("Can't add value null to collection");
		}
		
		ListNode newNode = new ListNode(last, null, value);
		
		if(first == null) {
			first = newNode;
		} else {
			last.next = newNode;
		}
		
		last = newNode;
		size++;
	}
	
	/**
	 * Removes object value from collection.
	 * @param value object that should be removed
	 * @throws IndexOutOfBoundsException if index is not from [0, size - 1]
	 */
	public void remove(int index) {
		if(index < LOWEST_VALID_INDEX || index > size - 1) {
			throw new IndexOutOfBoundsException("Can't remove value at index " + index + " because size is " + size);
		}
		
		if(index == 0) {
			first = first.next;
			first.previous = null;
		} else if(index == size - 1) {
			last = last.previous;
			last.next = null;
		} else {
			ListNode helpNode = nodeByIndex(index);
			helpNode.previous.next = helpNode.next;
			helpNode.next.previous = helpNode.previous;
		}
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
		Object[] array = new Object[size];
		ListNode helpNode = first;
		
		for(int i = 0; i < size; i++) {
			array[i] = helpNode.value;
			helpNode = helpNode.next;
		}
		
		return array;
	}
	
	/**
	 * Calls method Processor.process() for every elements in collection.
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode helpNode = first;
		while(helpNode != null) {
			processor.process(helpNode.value);
			helpNode = helpNode.next;
		}
	}
	
	/**
	 * Removes all elements from collection.
	 */
	@Override
	public void clear() {
		first = null;
		last = null;
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
		if(index < LOWEST_VALID_INDEX || index > size - 1) {
			throw new IndexOutOfBoundsException("Can't get value at index " + index + " because size is " + size);
		}
		
		return nodeByIndex(index).value;
	}
	
	/**
	 * Inserts value at position in collection. 
	 * @param value that should be inserted
	 * @param position where value should be inserted
	 * @throws NullPointerException if value is null
	 * @throws ArrayIndexOutOfBoundsException if position is not from [0, size]
	 */
	void insert(Object value, int position) {
		if(position < LOWEST_VALID_INDEX || position > size) {
			throw new IndexOutOfBoundsException("Can't get value at index " + position + " because size is " + size);
		} else if(value == null) {
			throw new NullPointerException("Can't insert value null to collection");
		}
		
		if(position == 0) {
			first = new ListNode(null, first, value);
		} else if(position == size) {
			last.next = new ListNode(last, null, value);
			last = last.next;
		} else {
			ListNode helpNode = nodeByIndex(position);
			ListNode newNode = new ListNode(helpNode.previous, helpNode, value);
			helpNode.previous.next = newNode;
			helpNode.previous = newNode;
		}
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
		ListNode helpNode = first;
		for(int i = 0; i < size; i++) {
			if(helpNode.value.equals(value)) {
				return i;
			}
			helpNode = helpNode.next;
		}
		return VALUE_NOT_FOUND;
	}
	
	
	//help method
	
	/**
	 * Finds node at given index.
	 * @param index from range [0, size - 1]
	 * @return list node 
	 */
	private ListNode nodeByIndex(int index) {
		if(index < size/2) {
			ListNode helpNode = first;
			for(int i = 0; i < index; i++) {
				helpNode = helpNode.next;
			}
			return helpNode;
		} else {
			ListNode helpNode = last;
			for(int i = 0; i < size - index - 1; i++) {
				helpNode = helpNode.previous;
			}
			return helpNode;
		}
	}	
}
