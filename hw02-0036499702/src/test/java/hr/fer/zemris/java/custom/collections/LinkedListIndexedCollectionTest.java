package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class used to test functionalities of class LinkedListIndexedCollection.
 * @author Alex
 *
 */
public class LinkedListIndexedCollectionTest {
	
	/**
	 * Creates linked list collection used for tests.
	 * @return linked list collection
	 */
	private LinkedListIndexedCollection createCollection() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		for(int i = 0; i < 5; i++) {
			collection.add(i);
		}
		return collection;
	}

	@Test
	public void testSize() {
		LinkedListIndexedCollection collection = createCollection();
		Assert.assertEquals(5, collection.size());
	}

	@Test
	public void testAdd() {
		LinkedListIndexedCollection collection = createCollection();
		Assert.assertEquals(0, collection.get(0));
		Assert.assertEquals(4, collection.get(4));
		Assert.assertEquals(5, collection.size());
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddNull() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(null);
	}

	@Test
	public void testToArray() {
		LinkedListIndexedCollection collection = createCollection();
		Object[] expected = {0, 1, 2, 3, 4};
		Assert.assertArrayEquals(expected, collection.toArray());
	}

	@Test
	public void testAddAll() {
		LinkedListIndexedCollection collection = createCollection();
		LinkedListIndexedCollection collection2 = new LinkedListIndexedCollection();
		collection2.addAll(collection);
		Assert.assertEquals(5, collection2.size());
	}

	@Test
	public void testClear() {
		LinkedListIndexedCollection collection = createCollection();
		collection.clear();
		Assert.assertEquals(0, collection.size());
	}

	@Test
	public void testRemoveInt() {
		LinkedListIndexedCollection collection = createCollection();
		collection.remove(0);
		Assert.assertEquals(4, collection.size());
		Assert.assertEquals(1, collection.get(0));
		Assert.assertEquals(4, collection.get(3));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveIndexOutOfBounds() {
		LinkedListIndexedCollection collection = createCollection();
		collection.remove(8);
	}

	@Test
	public void testInsert() {
		LinkedListIndexedCollection collection = createCollection();
		collection.insert(8, 2);
		Assert.assertEquals(8, collection.get(2));
		Assert.assertEquals(6, collection.size());		
	}
	
	@Test(expected = NullPointerException.class)
	public void testInsertNull() {
		LinkedListIndexedCollection collection = createCollection();
		collection.insert(null, 2);	
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testInsertIndexOutOfBounds() {
		LinkedListIndexedCollection collection = createCollection();
		collection.insert(8, 8);	
	}

	@Test
	public void testIndexOf() {
		LinkedListIndexedCollection collection = createCollection();
		
		Assert.assertEquals(0, collection.indexOf(0));
		Assert.assertEquals(4, collection.indexOf(4));
		Assert.assertEquals(-1, collection.indexOf(null));
		Assert.assertEquals(-1, collection.indexOf(8));
	}
}
