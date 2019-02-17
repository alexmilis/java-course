package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

public class UniqueNumbersTest {

	@Test
	public void testAddNode() {
		
		TreeNode actual = UniqueNumbers.addNode(null, 22);
		actual = UniqueNumbers.addNode(actual, 14);
		actual = UniqueNumbers.addNode(actual, 98);
		actual = UniqueNumbers.addNode(actual, 68);
		actual = UniqueNumbers.addNode(actual, 14);

		
		Assert.assertEquals(22, actual.value);
		Assert.assertEquals(14, actual.left.value);
		Assert.assertEquals(98, actual.right.value);
		Assert.assertEquals(68, actual.right.left.value);
	}

	@Test
	public void testTreeSize() {
		
		TreeNode treeTest = null;
		
		Assert.assertEquals(0, UniqueNumbers.treeSize(treeTest));
		
		treeTest = UniqueNumbers.addNode(treeTest, 22);
		treeTest = UniqueNumbers.addNode(treeTest, 14);
		treeTest = UniqueNumbers.addNode(treeTest, 98);
		treeTest = UniqueNumbers.addNode(treeTest, 68);
		treeTest = UniqueNumbers.addNode(treeTest, 14);
		
		Assert.assertEquals(4, UniqueNumbers.treeSize(treeTest));
	}

	@Test
	public void testContainsValue() {
		
		TreeNode treeTest = UniqueNumbers.addNode(null, 22);
		treeTest = UniqueNumbers.addNode(treeTest, 14);
		treeTest = UniqueNumbers.addNode(treeTest, 98);
		treeTest = UniqueNumbers.addNode(treeTest, 68);
		treeTest = UniqueNumbers.addNode(treeTest, 14);
		
		Assert.assertEquals(true, UniqueNumbers.containsValue(treeTest, 68));
		Assert.assertEquals(false, UniqueNumbers.containsValue(treeTest, 76));
	}

}
