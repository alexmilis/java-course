package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComparisonOperatorsTest {

	@Test
	public void testLess() {
		assertTrue(ComparisonOperators.LESS.satisfied("aa", "zjhg"));
	}
	
	@Test
	public void testLessOrEquals() {
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("aa", "zjhg"));
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("aa", "aa"));
		assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("zaa", "aa"));
	}

	@Test
	public void testGreater() {
		assertTrue(ComparisonOperators.GREATER.satisfied("zzzaa", "zjhg"));
		assertFalse(ComparisonOperators.GREATER.satisfied("aa", "zjhg"));
	}
	
	@Test
	public void testGreaterOrEquals() {
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("ZZaa", "ZZaa"));
	}
	
	@Test
	public void testEquals() {
		assertTrue(ComparisonOperators.EQUALS.satisfied("ZZaa", "ZZaa"));
		assertFalse(ComparisonOperators.EQUALS.satisfied("ZZaaa", "ZZaa"));
	}
	
	@Test
	public void testNotEquals() {
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("ZZaha", "ZZaa"));
	}
	
	@Test
	public void testLike() {
		assertTrue(ComparisonOperators.LIKE.satisfied("aabb", "*abb"));
		assertTrue(ComparisonOperators.LIKE.satisfied("aabb", "aab*"));
		assertTrue(ComparisonOperators.LIKE.satisfied("aabb", "a*b"));
		
		assertFalse(ComparisonOperators.LIKE.satisfied("aabbc", "*abb"));
		assertTrue(ComparisonOperators.LIKE.satisfied("caabb", "*abb"));
		assertTrue(ComparisonOperators.LIKE.satisfied("acaccaabb", "a*bb"));
	}
	
}
