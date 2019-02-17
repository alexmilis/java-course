package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

public class FactorialTest {

	@Test
	public void test5() {
		Assert.assertEquals(120, Factorial.calculateFactorial(5));
	}
	
	@Test
	public void test15() {
		Assert.assertEquals(479001600, Factorial.calculateFactorial(12));
	}
	
	@Test
	public void test7() {
		Assert.assertEquals(5040, Factorial.calculateFactorial(7));
	}
	
	@Test
	public void test10() {
		Assert.assertEquals(3628800, Factorial.calculateFactorial(10));
	}

}
