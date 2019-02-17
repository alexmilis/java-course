package hr.fer.zemris.java.hw07.crypto;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilTest {

	@Test
	public void testHextobyteExample() {
		byte[] bytes = Util.hextobyte("01aE22");
		assertTrue(bytes[0] == 1);
		assertTrue(bytes[1] == -82);
		assertTrue(bytes[2] == 34);
	}

	@Test
	public void testBytetohexExample() {
		byte[] bytes = {1, -82, 34};
		String hex = Util.bytetohex(bytes);
		assertTrue(hex.equals("01ae22"));
	}

	
	@Test(expected = IllegalArgumentException.class)
	public void testHextobyteEx1() {
		Util.hextobyte("01aE222");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testHextobyteEx2() {
		Util.hextobyte("01aE22k");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testHextobyteEx3() {
		Util.hextobyte("01gE22");
	}
	
	@Test
	public void testHextobyteEmpty() {
		byte[] bytes = Util.hextobyte("");
		assertTrue(bytes.length == 0);
	}
	
	@Test
	public void testBytetohexEmpty() {
		String hex = Util.bytetohex(new byte[0]);
		assertTrue(hex.length() == 0);
	}
	
	@Test
	public void testHextobyte1() {
		byte[] bytes = Util.hextobyte("AAAB");
		assertTrue(bytes[0] == -86);
		assertTrue(bytes[1] == -85);
	}
	
	@Test
	public void testHextobyte2() {
		byte[] bytes = Util.hextobyte("00a0Eeff");
		assertTrue(bytes[0] == 0);
		assertTrue(bytes[1] == -96);
		assertTrue(bytes[2] == -18);
		assertTrue(bytes[3] == -1);
	}
	
	@Test
	public void testBytetohex1() {
		byte[] bytes = {-86, -85};
		String hex = Util.bytetohex(bytes);
		assertTrue(hex.equals("aaab"));
	}
	
	@Test
	public void testBytetohex2() {
		byte[] bytes = {0, -96, -18, -1};
		String hex = Util.bytetohex(bytes);
		assertTrue(hex.equals("00a0eeff"));
	}
	
}
