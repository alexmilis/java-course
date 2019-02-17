package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

public class Dictionary {
	
	private ArrayIndexedCollection array;
	
	//static??
	private class Record {
		
		private Object key;
		private Object value;
		
		public Record(Object key, Object value) {
			this.key = Objects.requireNonNull(key);
			this.value = value;
		}
		
		@Override
		public boolean equals(Object arg0) {
			if(arg0 instanceof Record)
				return ((Record) arg0).key.equals(key);
			return false;
		}
	}
	
	public Dictionary() {
		this.array = new ArrayIndexedCollection();
	}
	
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	public int size() {
		return array.size();
	}
	
	public void clear() {
		array.clear();
	}
	
	//throws null pointer ex ako key = null
	public void put(Object key, Object value) {
		Record record = new Record(key, value);
		if(array.contains(record)) {
			array.remove(array.indexOf(record));
		}
		array.add(record);
	}
	
	//throws nullpointerex, dictex
	public Object get(Object key) {
		Record record = new Record(key, null);
		if(array.contains(record)) {
			return ((Record) array.get(array.indexOf(record))).value;
		}
		throw new DictionaryException("Record with key " + key + " is not in dictionary.");
	}
	
	public boolean contains(Object key) {
		return array.contains(new Record(key, null));
	}

}
