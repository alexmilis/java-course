package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class used to test functionalities of class ArrayIndexedCollection.
 * @author Alex
 *
 */
public class ArrayIndexedCollectionTest {
	
	/**
	 * Creates an array collection used for tests.
	 * @return array indexed collection
	 */
	private ArrayIndexedCollection createCollection() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		for(int i = 0; i < 5; i++) {
			collection.add(i);
		}
		return collection;
	}

	@Test
	public void testSize() {
		ArrayIndexedCollection collection = createCollection();
		Assert.assertEquals(5, collection.size());
	}
	
	@Test
	public void testAdd() {
		ArrayIndexedCollection collection = createCollection();
		Assert.assertEquals(5, collection.size());
		Assert.assertEquals(0, collection.get(0));
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddNull() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(null);
	}
	
	@Test
	public void testRemove() {
		ArrayIndexedCollection collection = createCollection();
		collection.remove(3);
		
		Assert.assertEquals(4, collection.size());
		Assert.assertEquals(4, collection.get(3));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveIndexOutOfBounds() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.remove(2);
	}
	
	@Test
	public void testToArray() {
		Object[] expected = {0, 1, 2, 3, 4};
		ArrayIndexedCollection collection = createCollection();
		
		Assert.assertArrayEquals(expected, collection.toArray());
		Assert.assertEquals(expected.length, collection.size());
	}
	
	@Test
	public void testAddAll() {		
		ArrayIndexedCollection collection = createCollection();
		
		ArrayIndexedCollection actual = new ArrayIndexedCollection();
		actual.addAll(collection);
		
		Assert.assertEquals(5, actual.size());
	}
	
	@Test
	public void testClear() {
		ArrayIndexedCollection collection = createCollection();
		
		collection.clear();
		Assert.assertEquals(0, collection.size());
	}
	
	@Test
	public void testGet() {
		ArrayIndexedCollection collection = createCollection();
		Assert.assertEquals(3, collection.get(3));
		Assert.assertEquals(0, collection.get(0));
		Assert.assertEquals(4, collection.get(4));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetIndexOutOfBounds() {
		ArrayIndexedCollection collection = createCollection();
		collection.get(7);
		collection.get(-1);
	}
	
	@Test
	public void testInsert() {
		ArrayIndexedCollection collection = createCollection();
		collection.insert(0, 2);
		
		Assert.assertEquals(0, collection.get(2));
		Assert.assertEquals(6, collection.size());
	}
	
	@Test(expected = NullPointerException.class)
	public void testInsertNullPointerException() {
		ArrayIndexedCollection collection = createCollection();
		collection.insert(null, 4);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testInsertIndexOutOfBoundsException() {
		ArrayIndexedCollection collection = createCollection();
		collection.insert(7, 7);
	}

	@Test
	public void TestIndexOf() {
		ArrayIndexedCollection collection = createCollection();
		
		Assert.assertEquals(0, collection.indexOf(0));
		Assert.assertEquals(4, collection.indexOf(4));
		Assert.assertEquals(-1, collection.indexOf(null));
		Assert.assertEquals(-1, collection.indexOf(8));
	}
	
}




















