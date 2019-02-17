package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementation of hash map. 
 * Consists of parameterized table entries (key, value). 
 * Implements interface iterable.
 * @author Alex
 *
 * @param <K> type of key
 * @param <V> type of value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	
	/**
	 * Array in which table entries are stored under index that corresponds hashcode of each entry.
	 */
	private TableEntry<K,V>[] table;
	
	/**
	 * Number of table entries in table.
	 */
	private int size;
	
	/**
	 * Number of modifications made.
	 */
	private int modificationCount;
	
	/**
	 * Default capacity of table.
	 */
	private static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * Factor of occupancy. Once occupancy is bigger that this factor, table must be resized.
	 */
	private static final double OCCUPANCY_FACTOR = 0.75;

	
	/**
	 * Static class that represents one table entry.
	 * Structure of table entry is (key, value).
	 * @author Alex
	 *
	 * @param <K> type of key
	 * @param <V> type of value
	 */
	public static class TableEntry<K, V>{
		
		/**
		 * Key under which value is stored.
		 */
		private K key;
		
		/**
		 * Value that is stored.
		 */
		private V value;
		
		/**
		 * If there are more keys with same index, entries are stored as linked list.
		 * This points to the next entry that has same index.
		 */
		TableEntry<K, V> next;
		
		/**
		 * Constructor of table entry.
		 * @param key cannot be null.
		 * @param value can be null.
		 * @throws NullPointerException if key is null.
		 */
		public TableEntry(K key, V value) {
			this.key = Objects.requireNonNull(key);
			this.value = value;
			this.next = null;
		}
		
		/**
		 * Getter for field key.
		 * @return key of type K
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * Getter for field value.
		 * @return value of type V.
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Setter for value.
		 * @param value of type V.
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * String representation of table entry.
		 */
		@Override
		public String toString() {
			return key + "=" + value;
		}
	}
	
	
	/**
	 * Constructor of SimpleHashtable with default capacity of 16. 
	 * Delegates creation to the other constructor.
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor of SimpleHashtable. Table is initialized with smallest power of 2 that is bigger than given capacity.
	 * @param capacity wanted capacity of table.
	 * @throws IllegalArgumentException if capacity < 1.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) {
			throw new IllegalArgumentException("Invalid capacity: " + capacity);
		}
		int cap = (int) Math.pow(2, Math.ceil(Math.log(capacity) / Math.log(2)));
		this.table = (TableEntry<K, V>[]) (new TableEntry[cap]);
		this.size = 0;
		this.modificationCount = 0;
	}
	
	/**
	 * Creates and puts an entry in the table. If key is already in table, it updates the value of that entry.
	 * @param key cannot be null.
	 * @param value can be null.
	 * @throws NullPointerException if key is null.
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);
		
		boolean contains = containsKey(key);
		int index = getIndex(key);
		TableEntry<K, V> helpEntry = getTableEntry(key, index);
		
		if(helpEntry == null) {
			table[index] = new TableEntry<>(key, value);
			size++;
			modificationCount++;
			return;
		}
		
		if(contains) {
			helpEntry.setValue(value);
		} else {
			helpEntry.next = (TableEntry<K, V>) (new TableEntry<>(key, value));
			size++;
			modificationCount++;
		}	
		
		if(size >= table.length * OCCUPANCY_FACTOR) {
			resize();
			modificationCount++;
		}
	}
	
	/**
	 * Gets value that is stored in table with given key.
	 * @param key
	 * @return value of type V that is stored under key
	 * 			null if key is null or not in table.
	 */
	public V get(Object key) {
		if(key == null) {
			return null;
		}
		
		if(!containsKey(key)) {
			return null;
		}
		
		int index = getIndex(key);
		return getTableEntry(key, index).getValue();
	}
	
	/**
	 * Gets the number of entries stored in table.
	 * @return number of stored entries.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Checks if key is stored in table.
	 * @param key object
	 * @return true if key is in table, otherwise false
	 * @throws NullPointerException if key is null
	 */
	public boolean containsKey(Object key) {
		Objects.requireNonNull(key);
		int index = getIndex(key);
		TableEntry<K, V> helpEntry = getTableEntry(key, index);
		
		if(helpEntry == null) {
			return false;
		}
		
		if(helpEntry.getKey().equals(key)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks if value is stored in table.
	 * @param value
	 * @return true if value is stored in table.
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> helpEntry = null;
		for(int i = 0; i < table.length; i++) {
			helpEntry = table[i];
			while(helpEntry != null) {
				if(helpEntry.getValue().equals(value)) {
					return true;
				}
				helpEntry = helpEntry.next;
			}
		}
		return false;
	}
	
	/**
	 * Removes entry with key key from table.
	 * @param key to be removed
	 */
	public void remove(Object key) {
		if(key == null) {
			return;
		}
		
		if(!containsKey(key)) {
			return;
		}
		
		int index = getIndex(key);
		TableEntry<K, V> helpEntry = table[index];
		
		if(helpEntry.getKey().equals(key)) {
			table[index] = helpEntry.next;
			size--;
			modificationCount++;
			return;
		}

		while(!helpEntry.next.getKey().equals(key)) {
			helpEntry = helpEntry.next;
		}
		
		helpEntry.next = helpEntry.next.next;
		size--;
		modificationCount++;
	}
	
	/**
	 * Checks if table if empty.
	 * @return true if table is empty.
	 */
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * String representation of simpe hashtable.
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder().append("[");
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> helpEntry = table[i];
			while(helpEntry != null) {
				str.append(helpEntry.toString()).append(", ");
				helpEntry = helpEntry.next;
			}

		}
		
		int index = str.lastIndexOf(", ");
		if(index != -1) {
			str.deleteCharAt(index);
			str.deleteCharAt(index);
		}
		
		return str.append("]").toString();
	}
	
	/**
	 * Deletes all entries from table.
	 */
	public void clear() {
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
			size = 0;
		}
		modificationCount++;
	}
	
	//private methods
	/**
	 * Method used to resize table once occupancy passes occupancy factor.
	 * Default occupancy factor if 75%.
	 */
	@SuppressWarnings("unchecked")
	private void resize() {
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> helpEntry = table[i];
			while(helpEntry != null) {
				add(newTable, helpEntry);
				helpEntry = helpEntry.next;
			}

		}
		
		this.table = newTable;
	}
	
	/**
	 * Method that adds table entry to new table during the action of resizing the table.
	 * @param newTable new table of bigger capacity.
	 * @param entry table entry that should be added to new table.
	 */
	private void add(TableEntry<K, V>[] newTable, TableEntry<K, V> entry) {
		int index = getIndex(entry.getKey(), newTable.length);
		TableEntry<K, V> helpEntry = newTable[index];
		
		if(helpEntry == null) {
			newTable[index] = new TableEntry<>(entry.getKey(), entry.getValue());
			return;
		}
		
		while(helpEntry.next != null) {
			helpEntry = helpEntry.next;
		}
		
		helpEntry.next = new TableEntry<>(entry.getKey(), entry.getValue());
	}
	
	/**
	 * Gets table entry with given key if such exists.
	 * @param key
	 * @param index slot of table in which entry with key should be stored.
	 * @return table entry if such exists, otherwise null
	 */
	private TableEntry<K, V> getTableEntry(Object key, int index) {
		TableEntry<K, V> helpEntry = table[index];

		if(helpEntry == null) {
			return helpEntry;
		}
		
		while(helpEntry.next != null) {
			if(helpEntry.getKey().equals(key)) {
				break;
			}
			helpEntry = helpEntry.next;
		}
		
		return helpEntry;
	}
	
	/**
	 * Calculates index of slot in which entry with key should be stored.
	 * @param key
	 * @param length capacity of table
	 * @return index of slot
	 */
	private int getIndex(Object key, int length) {
		return Math.abs(key.hashCode()) % length;
	}
	
	/**
	 * Same function as previous implementation of this method, delegates work to that method.
	 * @param key
	 * @return index of slot based on given key
	 */
	private int getIndex(Object key) {
		return getIndex(key, table.length);
	}

	/**
	 * Creates new iterator for simple hashtable.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Implementation of iterator for simple hashtable.
	 * @author Alex
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>>{

		/**
		 * Index of table slot that is currently checked.
		 */
		private int currentIndex = 0;
		
		/**
		 * Current entry in iteration.
		 */
		private TableEntry<K, V> currentEntry = table[0];
		
		/**
		 * Current number of modifications, used to determine if there have been any changes made to table during iteration.
		 */
		private int currentModCount = modificationCount;
		
		/**
		 * Used to show if in this step of iteration iterator.remove() has already been invoked.
		 */
		private boolean removed = false;
		
		/**
		 * Checks if there is another entry in table.
		 * @throws ConcurrentModificationException if table has been modified during iteration.
		 * @return true if there is another entry.
		 */
		@Override
		public boolean hasNext() {
			if(currentModCount != modificationCount) {
				throw new ConcurrentModificationException("Collection has been changed during iteration.");
			}
			
			if(currentEntry != null) {
				if(currentEntry.next != null) {
					return true;
				}
			}
				
			while(currentIndex < table.length) {
				if(table[currentIndex] != null) {
					return true;
				}
				currentIndex++;
			}
			return false;
		}

		/**
		 * Gets next entry from table if such exists.
		 * @return next entry if it exists
		 * @throws ConcurrentModificationException if table has been modified during iteration.
		 * @throws NoSuchElementException if there are no more entries left in table.
		 */
		@Override
		public TableEntry<K, V> next() {
			if(currentModCount != modificationCount) {
				throw new ConcurrentModificationException("Collection has been changed during iteration.");
			}
			
			if(!hasNext()) {
				throw new NoSuchElementException("No more elements in table!");
			}
			
			removed = false;
			
			if(currentEntry != null) {
				if(currentEntry.next != null) {
					currentEntry = currentEntry.next;
					return currentEntry;
				}
			}
			
			while(currentIndex < table.length) {
				if(table[currentIndex] != null) {
					currentEntry = table[currentIndex];
					break;
				}
				currentIndex++;
			}
			currentIndex++;
			return currentEntry;
		}
		
		/**
		 * Removes current entry from table. Can be invoked only once after every step of iteration (invocation of method next()).
		 * @throws IllegalStateException if current entry has already been removed.
		 * @throws ConcurrentModificationException if table has been modified during iteration.
		 */
		public void remove() {
			if(removed) {
				throw new IllegalStateException("Already removed in this iteration");
			}
			if(currentModCount != modificationCount) {
				throw new ConcurrentModificationException("Collection has been changed during iteration.");
			}
			SimpleHashtable.this.remove(currentEntry.getKey());
			currentModCount++;
			removed = true;
		}
		
	}
	
}
